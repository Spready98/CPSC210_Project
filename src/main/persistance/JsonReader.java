package persistance;

import model.Role;
import model.Champion;
import model.ChampionList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Champion List from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Champion List from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ChampionList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Champion List from JSON object and returns it
    private ChampionList parseWorkRoom(JSONObject jsonObject) {
        ChampionList cl = new ChampionList();
        addChampions(cl, jsonObject);
        return cl;
    }

    // MODIFIES: cl
    // EFFECTS: parses champions from JSON object and adds them to workroom
    private void addChampions(ChampionList cl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Champion List");
        for (Object json : jsonArray) {
            JSONObject nextChamp = (JSONObject) json;
            addChamp(cl, nextChamp);
        }
    }

    // MODIFIES: cl
    // EFFECTS: parses Champion from JSON object and adds it to Champion List
    private void addChamp(ChampionList cl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Role role = Role.valueOf(jsonObject.getString("role"));
        Champion champ = new Champion(name, role);
        cl.addChampion(champ);
    }
}
