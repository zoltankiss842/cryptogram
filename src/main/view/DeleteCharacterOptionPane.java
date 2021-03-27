package main.view;

import main.cryptogram.LetterCryptogram;
import main.cryptogram.NumberCryptogram;

import javax.swing.*;

public class DeleteCharacterOptionPane {

    private final String TITLE = "Delete character";
    private final String MESSAGE = "Would you like to delete your assigned character from the mapping?";
    private final String[] OPTIONS = {"Yes, I want to delete", "No, I don't want to delete"};
    private final int OPTION_TYPE = JOptionPane.YES_NO_OPTION;
    private final int MESSAGE_TYPE = JOptionPane.PLAIN_MESSAGE;

    private int result = -1;
    private JOptionPane pane;

    public DeleteCharacterOptionPane(JFrame frame) {
        initPane(frame);
    }

    private void initPane(JFrame frame) {
        result = JOptionPane.showOptionDialog( frame, MESSAGE, TITLE, OPTION_TYPE ,MESSAGE_TYPE, null, OPTIONS, null);
    }

    public boolean getResult() {
        if(result == JOptionPane.YES_OPTION){
            return true;
        }

        return false;
    }
}
