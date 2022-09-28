package persistance;


import model.Champion;
import model.ChampionList;
import model.Role;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            ChampionList cl = new ChampionList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ChampionList cl = new ChampionList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            cl = reader.read();
            assertEquals(0, cl.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ChampionList cl = new ChampionList();
            cl.addChampion(new Champion("ahri", Role.MAGE));
            cl.addChampion(new Champion("jhin", Role.MARKSMAN));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            cl = reader.read();
            List<Champion> champions = cl.getChampionList();
            assertEquals(2, champions.size());
            checkThingy("ahri", Role.MAGE, champions.get(0));
            checkThingy("jhin", Role.MARKSMAN, champions.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}