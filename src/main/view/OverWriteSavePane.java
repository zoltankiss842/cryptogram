package main.view;

import javax.swing.*;

public class OverWriteSavePane {

    private int result = -1;

    public OverWriteSavePane(JFrame frame) {
        initPane(frame);
    }

    private void initPane(JFrame frame) {
        String TITLE = "Overwrite saved file";
        String MESSAGE = "Would you like to overwrite your saved file? ";
        int OPTION_TYPE = JOptionPane.YES_NO_OPTION;
        int MESSAGE_TYPE = JOptionPane.QUESTION_MESSAGE;
        result = JOptionPane.showConfirmDialog(frame, MESSAGE, TITLE, OPTION_TYPE, MESSAGE_TYPE);
    }

    public boolean getResult() {
        return result == JOptionPane.YES_OPTION;
    }
}