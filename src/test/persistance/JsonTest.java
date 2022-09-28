package persistance;

import model.Role;
import model.Champion;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkThingy(String name, Role category, Champion champ) {
        assertEquals(name, champ.getName());
        assertEquals(category, champ.getRole());
    }
}
