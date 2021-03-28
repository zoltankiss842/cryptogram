package main.view;

import main.cryptogram.LetterCryptogram;
import main.cryptogram.NumberCryptogram;

import javax.swing.*;

public class NewGameTypeOptionPane implements OptionPane {

    private final String[] OPTIONS = {"Letter", "Number", "Cancel"};

    private int result = -1;

    public NewGameTypeOptionPane(JFrame frame) {
        initPane(frame);
    }

    private void initPane(JFrame frame) {
        String TITLE = "What type of game?";
        String MESSAGE = "What type of game would you like to play?";
        int OPTION_TYPE = JOptionPane.YES_NO_CANCEL_OPTION;
        int MESSAGE_TYPE = JOptionPane.PLAIN_MESSAGE;
        result = JOptionPane.showOptionDialog( frame, MESSAGE, TITLE, OPTION_TYPE, MESSAGE_TYPE, null, OPTIONS, null);

    }

    public String getResult() {
        if(result == JOptionPane.YES_OPTION){
            return LetterCryptogram.TYPE;
        }
        else if((result == JOptionPane.NO_OPTION)){
            return NumberCryptogram.TYPE;
        }
        else{
            return "";
        }
    }

}
