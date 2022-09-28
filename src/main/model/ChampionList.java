package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.LinkedList;
import java.util.Objects;

// Represents a list of Champions that have been drafted
public class ChampionList implements Writable {
    public static final int MAX_SIZE = 20;

    private final LinkedList<Champion> championList;


    // EFFECTS: Constructs a list of Champions that is empty
    public ChampionList() {
        championList = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: if corresponding team is not full
    //          - add Champion to end of the teams list
    //          - return true
    //          else return false
    public void addChampion(Champion c) {

        if (championList.size() < MAX_SIZE) {
            championList.add(c);

            Event champAdded = new Event("Added Champion: " + c.getName() + " to team or ban list");
            EventLog.getInstance().logEvent(champAdded);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds Champion to team without list size restrictions
    public void addAvailableChampion(Champion c) {
        championList.add(c);
        Event champAdded = new Event("Added Champion: " + c.getName() + " to team or ban list");
        EventLog.getInstance().logEvent(champAdded);
    }

    // EFFECTS: returns a string representing the current Champions drafted/banned, in order
    public String printDraft() {
        String champs = " ";
        int pos = 1;
        System.out.println(championList);
        for (Champion c : championList) {

            champs = champs.concat(pos + ". " + c.getName() + " ");
            pos++;
        }
        return champs;
    }

    // EFFECTS: returns a string representing each team members stats
    public String printTeamStats() {
        String champs = "";

        for (Champion c : championList) {
            champs = champs.concat(c.statString() + " ");
        }
        return champs;
    }

    // EFFECTS: finds champion with name s, and returns the champion
    //          otherwise return null
    public Champion findChampion(String s) {

        for (Champion c : championList) {
            if (Objects.equals(c.getName(), s)) {
                return c;
            }
        }
        return null;
    }

    public int length() {
        return championList.size();
    }

    public LinkedList<Champion> getChampionList() {
        return championList;
    }

    public Champion getChamp(int i) {
        return championList.get(i);
    }

    public Champion getNextChamp() {
        return championList.removeFirst();
    }

    // REQUIRES: s == a champions name
    // MODIFIES: this
    // EFFECTS: removes champion from list whose name matches s
    public boolean removeChamp(String s) {

        for (Champion c : championList) {
            if (Objects.equals(c.getName(), s)) {
                Event champRemoved = new Event("Removed Champion: " + c.getName() + " from Champion pool");
                EventLog.getInstance().logEvent(champRemoved);
                championList.remove(c);
                return true;
            }
        }
        return false;
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Champion List", championsToJson());
        return json;
    }

    // EFFECTS: returns things in this Champion List as a JSON array
    private JSONArray championsToJson() {
        JSONArray jsonArray = new JSONArray();
        System.out.println(championList);

        for (Champion c : championList) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }


}
