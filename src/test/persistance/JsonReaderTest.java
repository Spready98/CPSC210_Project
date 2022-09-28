package persistance;

import model.Champion;
import model.ChampionList;
import model.Role;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ChampionList cl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            ChampionList cl = reader.read();
            assertEquals(0, cl.length());
            assertFalse(cl.removeChamp("jhin"));
            assertEquals("", cl.printTeamStats());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            ChampionList cl = reader.read();
            List<Champion> champs = cl.getChampionList();
            assertEquals(2, champs.size());
            checkThingy("jhin", Role.MARKSMAN, champs.get(0));
            checkThingy("ahri", Role.MAGE, champs.get(1));
            assertTrue(cl.removeChamp("jhin"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}