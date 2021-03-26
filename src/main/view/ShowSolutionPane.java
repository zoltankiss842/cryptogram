package main.view;

import main.cryptogram.LetterCryptogram;
import main.cryptogram.NumberCryptogram;
import main.game.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/**
 * This class is for displaying the solution
 * to the current cryptogram when the user
 * presses the button to see the solution
 */

public class ShowSolutionPane {
    private final String TITLE = "The solution: ";
    private final String[] OPTIONS = {"OK"};
    private final int OPTION_TYPE = JOptionPane.OK_OPTION;
    private final int MESSAGE_TYPE = JOptionPane.PLAIN_MESSAGE;

    private int result = -1;
    private JOptionPane pane;

    public ShowSolutionPane(String solution, JFrame frame) {
        initPane(solution, frame);
    }

    private void initPane(String solution, JFrame frame) {
        result = JOptionPane.showOptionDialog(frame, solution, TITLE, OPTION_TYPE, MESSAGE_TYPE, null, OPTIONS, null);

    }
}