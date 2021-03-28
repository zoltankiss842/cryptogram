package main.view;

import javax.swing.*;

public class DeleteCharacterOptionPane {

    private final String[] OPTIONS = {"Yes, I want to delete", "No, I don't want to delete"};

    private int result = -1;

    public DeleteCharacterOptionPane(JFrame frame) {
        initPane(frame);
    }

    private void initPane(JFrame frame) {
        String TITLE = "Delete character";
        int OPTION_TYPE = JOptionPane.YES_NO_OPTION;
        String MESSAGE = "Would you like to delete your assigned character from the mapping?";
        int MESSAGE_TYPE = JOptionPane.PLAIN_MESSAGE;
        result = JOptionPane.showOptionDialog( frame, MESSAGE, TITLE, OPTION_TYPE, MESSAGE_TYPE, null, OPTIONS, null);
    }

    public boolean getResult() {
        return result == JOptionPane.YES_OPTION;
    }
}
