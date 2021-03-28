package main.view;

import javax.swing.*;


/**
 * This class is for displaying the solution
 * to the current cryptogram when the user
 * presses the button to see the solution
 */
public class ShowSolutionPane {
    private final String[] OPTIONS = {"OK"};

    public ShowSolutionPane(String solution, JFrame frame) {
        initPane(solution, frame);
    }

    private void initPane(String solution, JFrame frame) {
        String TITLE = "The solution: ";
        int OPTION_TYPE = JOptionPane.OK_OPTION;
        int MESSAGE_TYPE = JOptionPane.PLAIN_MESSAGE;
        JOptionPane.showOptionDialog(frame, solution, TITLE, OPTION_TYPE, MESSAGE_TYPE, null, OPTIONS, null);

    }
}