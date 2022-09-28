package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class ChampionListTest {
    private ChampionList testList;
    private Champion testChamp;

    @BeforeEach
    void runBefore() {
        testList = new ChampionList();
        testChamp = new Champion("ahri", Role.MAGE);
    }

    @Test
    public void testAddChampionFull() {

        for (int count = 1; count <= testList.MAX_SIZE; count++) {
            testList.addChampion(testChamp);
        }

        assertEquals(testList.MAX_SIZE, testList.length());
        testList.addChampion(testChamp);
        assertEquals(testList.MAX_SIZE, testList.length());
    }

    @Test
    public void testAddChampion() {

        testList.addChampion(testChamp);
        assertEquals(1, testList.length());
        assertEquals(testChamp, testList.getNextChamp());
    }

    @Test
    public void testAddMultipleChampions() {

        Champion testChampTwo = new Champion("jhin", Role.MARKSMAN);

        testList.addChampion(testChamp);
        testList.addChampion(testChampTwo);
        assertEquals(2, testList.length());
        assertEquals(testChamp, testList.getNextChamp());
        assertEquals(testChampTwo, testList.getNextChamp());
    }

    @Test
    public void testAddChampionAgainButFull() {

        for (int count = 1; count < testList.MAX_SIZE; count++) {
            testList.addChampion(testChamp);
        }

        Champion testChampTwo = new Champion("jhin", Role.MARKSMAN);
        assertEquals(testList.MAX_SIZE - 1, testList.length());
        testList.addChampion(testChampTwo);
        assertEquals(testList.MAX_SIZE, testList.length());
        testList.addChampion(testChampTwo);

    }

    @Test
    public void testPrintDraft() {
        Champion testChampTwo = new Champion("jhin", Role.MARKSMAN);
        testList.addChampion(testChamp);
        testList.addChampion(testChampTwo);

        assertEquals(" 1. ahri 2. jhin ", testList.printDraft());
    }

    @Test
    public void testPrintTeamStats() {
        Champion testChampTwo = new Champion("jhin", Role.MARKSMAN);
        testList.addChampion(testChamp);
        testList.addChampion(testChampTwo);

        assertEquals("ahri (Role = MAGE)" + " " +
                "jhin (Role = MARKSMAN) ", testList.printTeamStats());
    }

    @Test
    public void testGetChampionList() {
        Champion testChampTwo = new Champion("jhin", Role.MARKSMAN);
        Champion fakeChamp = new Champion("zoe", Role.MAGE);
        testList.addChampion(testChamp);
        testList.addChampion(testChampTwo);

        LinkedList<Champion> champList = testList.getChampionList();

        assertEquals(2, champList.size());
        assertTrue(champList.contains(testChamp));
        assertTrue(champList.contains(testChampTwo));
        assertFalse(champList.contains(fakeChamp));

    }


    @Test
    public void testFindChampion() {
        Champion testChampTwo = new Champion("jhin", Role.MARKSMAN);
        testList.addChampion(testChamp);
        testList.addChampion(testChampTwo);

        Champion foundChamp = testList.findChampion("jhin");
        assertEquals(testChampTwo, foundChamp);
    }

    @Test
    public void testCantFindChampion() {
        Champion testChampTwo = new Champion("jhin", Role.MARKSMAN);
        testList.addChampion(testChamp);
        testList.addChampion(testChampTwo);

        Champion foundChamp = testList.findChampion("yasuo");
        assertEquals(null, foundChamp);
    }

    @Test
    public void testEmptyPrintDraft() {
        assertEquals(" ", testList.printDraft());
    }

    @Test
    public void testRemoveChamp() {
        testList.addAvailableChampion(testChamp);
        assertFalse(testList.removeChamp("jhin"));
        assertEquals(1, testList.length());
        assertTrue(testList.removeChamp("ahri"));
        assertEquals(0, testList.length());


    }

}
