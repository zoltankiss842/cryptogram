package main.view;

import javax.swing.*;

public class OverWriteOptionPane {

    private final String overwriteValues;
    private int result = -1;

    public OverWriteOptionPane(JFrame frame, String from, String to) {
        this.overwriteValues = from + " -> " + to;
        initPane(frame);
    }

    private void initPane(JFrame frame) {
        String TITLE = "Overwrite letter";
        String MESSAGE = "Would you like to overwrite all instances for: ";
        int OPTION_TYPE = JOptionPane.YES_NO_OPTION;
        int MESSAGE_TYPE = JOptionPane.QUESTION_MESSAGE;
        result = JOptionPane.showConfirmDialog(frame, MESSAGE + overwriteValues, TITLE, OPTION_TYPE, MESSAGE_TYPE);
    }

    public boolean getResult() {
        return result == JOptionPane.YES_OPTION;
    }
}
