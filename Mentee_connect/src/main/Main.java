package main;

import javax.swing.SwingUtilities;
import ui.IntroUI;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new IntroUI();
        });
    }
}