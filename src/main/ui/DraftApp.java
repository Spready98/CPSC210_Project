package ui;

import model.*;

import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;

// Draft application
public class DraftApp {

    public static final int BAN_SIZE = 2;
    public static final int TEAM_SIZE = 3;
    private static final String JSON_STORE_RED = "./data/red-draft.json";
    private static final String JSON_STORE_BLUE = "./data/blue-draft.json";
    private static final String JSON_STORE_BANS = "./data/ban-list.json";
    private static final String JSON_STORE_AVAILABLE_CHAMPS = "./data/champ-list.json";
    private static final String JSON_STORE_PRELOAD_CHAMPS = "./data/preload-champ.json";
    private ChampionList blueTeam;
    private ChampionList redTeam;
    private ChampionList availableChamps;
    private ChampionList banList;
    private Scanner input;
    boolean banPhase = true;
    boolean draftPhase = true;
    private final JsonReader jsonReaderRed;
    private final JsonReader jsonReaderBlue;
    private final JsonWriter jsonWriterRed;
    private final JsonWriter jsonWriterBlue;
    private final JsonWriter jsonWriterBan;
    private final JsonReader jsonReaderBan;
    private final JsonWriter jsonWriterChamp;
    private final JsonReader jsonReaderChamp;
    private final JsonReader jsonReaderPreload;


    // EFFECTS: Constructs the draft application, and gives user option to load previous file
    public DraftApp(boolean ifGui) throws FileNotFoundException {

        input = new Scanner(System.in);
        jsonReaderRed = new JsonReader(JSON_STORE_RED);
        jsonWriterRed = new JsonWriter(JSON_STORE_RED);
        jsonReaderBlue = new JsonReader(JSON_STORE_BLUE);
        jsonWriterBlue = new JsonWriter(JSON_STORE_BLUE);
        jsonReaderBan = new JsonReader(JSON_STORE_BANS);
        jsonWriterBan = new JsonWriter(JSON_STORE_BANS);
        jsonReaderChamp = new JsonReader(JSON_STORE_AVAILABLE_CHAMPS);
        jsonWriterChamp = new JsonWriter(JSON_STORE_AVAILABLE_CHAMPS);
        jsonReaderPreload = new JsonReader(JSON_STORE_PRELOAD_CHAMPS);
        startDraft();
        champsAvailable();
        if (!ifGui) {
            loadMenu();
            runDraft();
        }
    }

    // EFFECTS: loads in a preset champion pool for both teams to draft from
    public void champsAvailable() {
        try {
            availableChamps = jsonReaderPreload.read();
            System.out.println("Pre-Loaded champion list from " + JSON_STORE_AVAILABLE_CHAMPS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_AVAILABLE_CHAMPS);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input for the Draft
    private void runDraft() {

        while (banPhase) {
            startBanPhase();
            String command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                banPhase = false;
                draftPhase = false;
            } else {
                banDraft();
                banPhase = false;
            }
        }
        while (draftPhase) {
            blueDraft();
            redDraft();
            saveAndQuitMenu();
            if (redTeam.length() >= TEAM_SIZE) {
                System.out.println("Draft complete! Blue team: " + blueTeam.printDraft()
                        + " Red team: " + redTeam.printDraft());
                draftPhase = false;
            }
        }
    }

    // EFFECTS: displays option for user to load previous draft, and handles an already full draft
    private void loadMenu() {
        System.out.println("\n Would you like to load the draft from file?");
        System.out.println("\ty -> Yes, load the saved draft");
        System.out.println("\tn -> No, start a new draft");
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("y")) {
            loadDraft();
            banPhase = false;
            if (blueTeam.length() >= TEAM_SIZE) {
                draftPhase = false;
                System.out.println();
                System.out.println("Loaded draft is already fully complete...");
                System.out.println("Bans: " + banList.printDraft());
                System.out.println("Blue Team: " + blueTeam.printDraft());
                System.out.println("Red Team: " + redTeam.printDraft());
                System.out.println("Goodbye.");
            }
        } else {
            System.out.println();
        }
    }

    // EFFECTS: displays a menu option for user to save and quit current draft file
    private void saveAndQuitMenu() {
        System.out.println("\n Would you like to save and quit the draft?");
        System.out.println("\ty -> Yes, save and quit");
        System.out.println("\tn -> No, continue draft");
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("y")) {
            saveDraft();
            System.out.println("Goodbye!");
            draftPhase = false;
        } else {
            System.out.println();
        }
    }

    // EFFECTS: display starting ban phase menu
    private void startBanPhase() {
        System.out.println("\n Welcome to the Ban Phase of the Draft, select from:");
        System.out.println("\tb -> start ban phase");
        System.out.println("\tq -> quit draft");
    }

    // EFFECTS: initializes the banning phase of the draft and takes user input
    private void banDraft() {

        for (int banCount = 1; banCount <= BAN_SIZE; banCount++) {

            System.out.println("\n Ban Phase: " + banCount + "/" + BAN_SIZE);
            System.out.println("\t Which unique champion would you like to ban?");
            String banInput = input.next();
            banInput = banInput.toLowerCase();
            processBan(banInput);
        }
    }


    // EFFECTS: initializes redTeam's drafting phase and takes user input
    private void redDraft() {
        displayDraft("Red Team");

        System.out.println("Red team, which champion would you like to draft?");
        System.out.println("Champions that are banned: " + banList.printDraft());
        String redInput = input.next();
        redInput = redInput.toLowerCase();
        processRedDraft(redInput);
    }

    // EFFECTS: initializes blueTeam's drafting phase and takes user input
    private void blueDraft() {

        displayDraft("Blue Team");

        System.out.println("Blue team, which champion would you like to draft?");
        System.out.println("Champions that are banned: " + banList.printDraft());
        String blueInput = input.next();
        blueInput = blueInput.toLowerCase();
        processBlueDraft(blueInput);

    }

    // EFFECTS: processes redTeam's draft choice, lets user know the champion of choice's stats, and
    //          removes the champion from availableChamps list
    private void processRedDraft(String command) {
        Champion sel = availableChamps.findChampion(command);
        String champStats = sel.statString();
        System.out.println("You have drafted " + champStats);
        availableChamps.removeChamp(command);
        redTeam.addChampion(sel);
    }

    // EFFECTS: processes the Champion onto the banlist and removes it from availability
    private void processBan(String command) {

        Champion banned = availableChamps.findChampion(command);
        availableChamps.removeChamp(command);

        banList.addChampion(banned);

    }

    // EFFECTS: processes blueTeam's draft choice, lets user know the champion of choice's stats, and
    //          removes the champion from availableChamps list
    private void processBlueDraft(String command) {
        Champion sel = availableChamps.findChampion(command);
        String champStats = sel.statString();
        System.out.println("You have drafted " + champStats);

        availableChamps.removeChamp(command);
        blueTeam.addChampion(sel);
    }


    // EFFECT: displays prompt for red or blue team to draft a champion
    private void displayDraft(String team) {
        System.out.println("\n" + team + ", before drafting a champion, would you like to...");
        System.out.println("\tp -> print available champions to draft");
        System.out.println("\t     press any other key to continue the draft");
        String command = input.next();
        command = command.toLowerCase();

        if (command.equals("p")) {
            System.out.println("Champion pool remaining: " + availableChamps.printDraft());
        }
    }

    // EFFECT: initializes the whole draft
    private void startDraft() {
        blueTeam = new ChampionList();
        redTeam = new ChampionList();
        banList = new ChampionList();
        input = new Scanner(System.in);
        availableChamps = new ChampionList();

    }

    // MODIFIES: this
    // EFFECTS: loads Champion list from file
    private void loadDraft() {
        try {
            redTeam = jsonReaderRed.read();
            System.out.println("Loaded red draft from " + JSON_STORE_RED);
            blueTeam = jsonReaderBlue.read();
            System.out.println("Loaded blue draft from " + JSON_STORE_BLUE);
            banList = jsonReaderBan.read();
            System.out.println("Loaded ban list from " + JSON_STORE_BANS);
            availableChamps = jsonReaderChamp.read();
            System.out.println("Loaded available champion list from " + JSON_STORE_AVAILABLE_CHAMPS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_RED + JSON_STORE_BLUE);
        }
    }

    // EFFECTS: saves the draft to file
    private void saveDraft() {
        try {
            jsonWriterRed.open();
            jsonWriterRed.write(redTeam);
            jsonWriterRed.close();
            jsonWriterBlue.open();
            jsonWriterBlue.write(blueTeam);
            jsonWriterBlue.close();
            jsonWriterBan.open();
            jsonWriterBan.write(banList);
            jsonWriterBan.close();
            jsonWriterChamp.open();
            jsonWriterChamp.write(availableChamps);
            jsonWriterChamp.close();
            System.out.println("Saved draft to " + JSON_STORE_RED + ", " + JSON_STORE_BLUE + ", "
                    + JSON_STORE_BANS + ", and " + JSON_STORE_AVAILABLE_CHAMPS);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_RED + JSON_STORE_BLUE);
        }
    }

    public ChampionList getChampPool() {
        return availableChamps;
    }

}
