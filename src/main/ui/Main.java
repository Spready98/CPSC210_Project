package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new DraftApp(false);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run Draft: File not found");
        }
    }
}
