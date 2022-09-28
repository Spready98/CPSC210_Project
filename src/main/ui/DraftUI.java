package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import persistance.JsonReader;
import persistance.JsonWriter;

import model.Champion;
import model.ChampionList;


public class DraftUI extends JPanel implements ListSelectionListener, Icon {

    private JList listJM;
    private JList listJB;
    private final DefaultListModel listModel;
    private final DefaultListModel listBlue;
    private final DefaultListModel listRed;

    private int banCount = 0;
    private static final String banString = "Ban";
    private final DraftApp draftApp = new DraftApp(true);
    private ChampionList redTeam;
    private ChampionList blueTeam;
    private ChampionList champPool;

    private JLabel iconLabel;
    private JButton saveButton;
    private JButton loadButton;
    private JButton banButton;
    private JButton draftButtonB;
    private JButton draftButtonR;
    private JPanel buttonPane;
    private JSplitPane splitPane;
    private JSplitPane splitPaneMain;
    private final Dimension minSize = new Dimension(100, 50);

    private static final String JSON_STORE_RED = "./data/red-list.json";
    private static final String JSON_STORE_BLUE = "./data/blue-list.json";
    private static final String JSON_STORE_AVAILABLE_CHAMPS = "./data/champ-pool-list.json";
    private JsonReader jsonReaderRed;
    private JsonReader jsonReaderBlue;
    private JsonWriter jsonWriterRed;
    private JsonWriter jsonWriterBlue;
    private JsonWriter jsonWriterChamp;
    private JsonReader jsonReaderChamp;

    // DRAFT GUI Application
    public DraftUI() throws FileNotFoundException {
        super(new BorderLayout());

        initializeDraft();

        createImg();
        createSaveButton();
        createLoadButton();
        createDraftButtonBlue();
        createBanButton();
        createDraftButtonRed();
        createButtonPane();

        listModel = new DefaultListModel();
        listBlue = new DefaultListModel();
        listRed = new DefaultListModel();
        loadChampPool();
        JScrollPane listScrollPane = new JScrollPane(createListModel(listModel));
        JScrollPane listBluePane = new JScrollPane(createListBlue(listBlue));
        JScrollPane listRedPane = new JScrollPane(createListRed(listRed));
        createSplitPane(listScrollPane, listBluePane);
        createMainPane(listRedPane);

        add(iconLabel, BorderLayout.PAGE_START);
        add(splitPaneMain, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    public void windowClosing() {
        System.out.println("WindowClosingDemo.windowClosing");
        System.exit(0);
    }

    // EFFECTS: creates the button to save lists to JSON
    private void createSaveButton() {
        String saveString = "Save Draft";
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());
    }
    
    // EFFECTS: creates the button to load saved lists
    private void createLoadButton() {
        String loadString = "Load Draft";
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());
    }

    // EFFECTS: Convert DefaultListModel to JList and return it for Red Team
    private JList createListRed(DefaultListModel listR) {

        JList listJR = new JList(listR);
        listJR.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listJR.setSelectedIndex(0);
        listJR.addListSelectionListener(this);
        listJR.setVisibleRowCount(20);
        return listJR;
    }

    // EFFECTS: combines two split panes to create a nested split pane
    private void createMainPane(JScrollPane listRed) {

        listRed.setMinimumSize(minSize);
        splitPaneMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listRed,splitPane);
        splitPaneMain.setOneTouchExpandable(true);
        splitPaneMain.setDividerLocation(220);

    }
    
    // EFFECTS: Convert DefaultListModel to JList and return it for Blue Team
    private JList createListBlue(DefaultListModel listB) {

        listJB = new JList(listB);
        listJB.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listJB.setSelectedIndex(0);
        listJB.addListSelectionListener(this);
        listJB.setVisibleRowCount(20);
        return listJB;
    }
    
    // EFFECTS: Create a split pane from two scroll panes
    private void createSplitPane(JScrollPane listScroll, JScrollPane listBlue) {
        listScroll.setMinimumSize(minSize);
        listBlue.setMinimumSize(minSize);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScroll, listBlue);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);
    }


    // EFFECTS: Create a panel that uses BoxLayout.
    private void createButtonPane() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(draftButtonR);
        buttonPane.add(Box.createHorizontalStrut(25));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(loadButton);
        buttonPane.add(Box.createHorizontalStrut(15));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(banButton);
        buttonPane.add(Box.createHorizontalStrut(15));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(saveButton);
        buttonPane.add(Box.createHorizontalStrut(15));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(25));
        buttonPane.add(draftButtonB);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    // EFFECTS: Create the button to add/draft a champion from list to a team Blue
    private void createDraftButtonRed() {
        String draftStringR = "Draft (Red)";
        draftButtonR = new JButton(draftStringR);
        DraftListener draftListener = new DraftListener(draftButtonR);
        draftButtonR.setActionCommand(draftStringR);
        draftButtonR.addActionListener(draftListener);
    }

    // EFFECTS: Create the button to add/draft a champion from list to a team Blue
    private void createDraftButtonBlue() {
        String draftStringB = "Draft (Blue)";
        draftButtonB = new JButton(draftStringB);
        DraftListener draftListener = new DraftListener(draftButtonB);
        draftButtonB.setActionCommand(draftStringB);
        draftButtonB.addActionListener(draftListener);
    }

    // EFFECTS: Convert DefaultListModel to JList and return it for Champion Pool
    private JList createListModel(DefaultListModel listM) {

        listJM = new JList(listM);
        listJM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listJM.setSelectedIndex(0);
        listJM.addListSelectionListener(this);
        listJM.setVisibleRowCount(20);
        return listJM;
    }

    // EFFECTS: Create the button to ban/remove a champion from list
    private void createBanButton() {
        banButton = new JButton(banString);
        banButton.setActionCommand(banString);
        banButton.addActionListener(new BanListener());

    }
    
    // EFFECTS: Transfers champion list from Json to the lists used in the panels
    public void loadChampPool() {
        champPool = draftApp.getChampPool();
        for (int i = 0; i < champPool.length(); i++) {
            Champion c = champPool.getChamp(i);
            listModel.addElement(c.getName());
        }
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {

    }

    @Override
    public int getIconWidth() {
        return 0;
    }

    @Override
    public int getIconHeight() {
        return 0;
    }

    //This listener is for the load button.
    class SaveListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            try {
                jsonWriterRed.open();
                System.out.println(redTeam);
                jsonWriterRed.write(redTeam);
                jsonWriterRed.close();
                jsonWriterBlue.open();
                jsonWriterBlue.write(blueTeam);
                jsonWriterBlue.close();
                jsonWriterChamp.open();
                jsonWriterChamp.write(champPool);
                jsonWriterChamp.close();
                System.out.println("Saved draft to " + JSON_STORE_RED + ", " + JSON_STORE_BLUE + ", "
                        + ", and " + JSON_STORE_AVAILABLE_CHAMPS);
            } catch (FileNotFoundException ex) {
                System.out.println("Unable to write to file: " + JSON_STORE_RED + JSON_STORE_BLUE);
            }


        }

    }

    //This listener is for the load button.
    class LoadListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            try {
                redTeam = jsonReaderRed.read();
                System.out.println("Loaded red draft from " + JSON_STORE_RED);
                blueTeam = jsonReaderBlue.read();
                System.out.println("Loaded blue draft from " + JSON_STORE_BLUE);
                champPool = jsonReaderChamp.read();
                System.out.println("Loaded available champion list from " + JSON_STORE_AVAILABLE_CHAMPS);
                convertChampionsToList();
            } catch (IOException ex) {

                System.out.println("Unable to read from file: " + JSON_STORE_RED + JSON_STORE_BLUE);
            }
        }

        private void convertChampionsToList() {

            listModel.removeAllElements();

            for (int i = 0; i < champPool.length(); i++) {
                Champion c = champPool.getChamp(i);
                listModel.addElement(c.getName());

            }

            for (int i = 0; i < redTeam.length(); i++) {
                Champion c = redTeam.getChamp(i);
                listRed.addElement(c.getName());
            }

            for (int i = 0; i < blueTeam.length(); i++) {
                Champion c = blueTeam.getChamp(i);
                listBlue.addElement(c.getName());
            }

        }
    }

    //This listener is for the ban button.
    class BanListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            int index = listJM.getSelectedIndex();
            String removed = (String) listModel.getElementAt(index);
            champPool.removeChamp(removed);
            listModel.remove(index);
            banCount++;

            int size = listModel.getSize();

            if (size == 0 || banCount >= draftApp.BAN_SIZE) { //Nobody's left or finished banning, disable firing.
                banButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                listJM.setSelectedIndex(index);
                listJM.ensureIndexIsVisible(index);
            }
        }
    }

    //This listener is for the draft button.
    class DraftListener implements ActionListener {

        private final JButton button;

        public DraftListener(JButton button) {
            this.button = button;
        }


        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {

            int index = listJM.getSelectedIndex(); //get selected index

            if (button == draftButtonB && listBlue.size() < draftApp.TEAM_SIZE) {

                String removed = (String) listModel.remove(index);
                Champion champToAdd = champPool.findChampion(removed);
                blueTeam.addChampion(champToAdd);
                listBlue.addElement(removed);
                champPool.removeChamp(removed);


            } else if (button == draftButtonR && listRed.size() < draftApp.TEAM_SIZE) {

                String removed = (String) listModel.remove(index);
                System.out.println(removed); // returns "jhin (MARKSMAN)" but findChampion needs just "jhin"
                Champion champToAdd = champPool.findChampion(removed); // champToAdd is null?
                redTeam.addChampion(champToAdd);
                listRed.addElement(removed);
                champPool.removeChamp(removed);

            }

            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            //Select the new item and make it visible.
            listJB.setSelectedIndex(index);
            listJB.ensureIndexIsVisible(index);
        }

    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (listJM.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                banButton.setEnabled(false);

            } else if (banCount < draftApp.BAN_SIZE) {
                //Selection, enable the fire button.
                banButton.setEnabled(true);
            }
        }
    }
    
    // EFFECTS: initializes all the json and ChampionList fields
    private void initializeDraft() {

        jsonReaderRed = new JsonReader(JSON_STORE_RED);
        jsonWriterRed = new JsonWriter(JSON_STORE_RED);
        jsonReaderBlue = new JsonReader(JSON_STORE_BLUE);
        jsonWriterBlue = new JsonWriter(JSON_STORE_BLUE);
        jsonReaderChamp = new JsonReader(JSON_STORE_AVAILABLE_CHAMPS);
        jsonWriterChamp = new JsonWriter(JSON_STORE_AVAILABLE_CHAMPS);
        redTeam = new ChampionList();
        blueTeam = new ChampionList();
    }
    
    // EFFECTS: creates an ImageIcon from given path url
    protected ImageIcon createImageIcon() {
        java.net.URL imgURL = getClass().getResource("image/summonerheader.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL, "The Summoner's Cup");
        } else {
            System.err.println("Couldn't find file: " + "image/summonerheader.png");
            return null;
        }
    }
    
    // EFFECTS: instantiates the image to be added to the JPanel
    private void createImg() {
        ImageIcon icon = createImageIcon();
        iconLabel = new JLabel(icon);
    }

}


