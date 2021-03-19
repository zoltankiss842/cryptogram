package main.view;

import main.cryptogram.LetterCryptogram;
import main.cryptogram.NumberCryptogram;

import javax.swing.*;

public class FirstOptionGamePane implements OptionPane {



    private final String TITLE = "What type of game?";
    private final String MESSAGE = "What type of game would you like to play?";
    private final String[] OPTIONS = {"Letter", "Number"};
    private final int OPTION_TYPE = JOptionPane.YES_NO_OPTION;
    private final int MESSAGE_TYPE = JOptionPane.PLAIN_MESSAGE;

    private int result = -1;
    private JOptionPane pane;

    public FirstOptionGamePane(JFrame frame) {
        initPane(frame);
    }

    private void initPane(JFrame frame) {
        result = JOptionPane.showOptionDialog( frame, MESSAGE, TITLE, OPTION_TYPE ,MESSAGE_TYPE, null, OPTIONS, null);

    }

    public String getResult() {
        if(result == JOptionPane.YES_OPTION){
            return LetterCryptogram.TYPE;
        }
        else if((result == JOptionPane.NO_OPTION)){
            return NumberCryptogram.TYPE;
        }
        else{
            return null;
        }
    }

}
