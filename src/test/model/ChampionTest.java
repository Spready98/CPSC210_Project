package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChampionTest {
    private Champion testChamp;

    @BeforeEach
    void runBefore() {
        testChamp = new Champion("Ahri", Role.MAGE);
    }

    @Test
    void testConstructor() {
        assertEquals("Ahri", testChamp.getName());
        assertEquals(Role.MAGE, testChamp.getRole());
    }

    @Test
    void testStatString() {
        assertTrue(testChamp.statString().contains("Ahri (Role = MAGE)"));
    }

    @Test
    void testGetRole() {
        assertEquals(Role.MAGE, testChamp.getRole());
    }

}