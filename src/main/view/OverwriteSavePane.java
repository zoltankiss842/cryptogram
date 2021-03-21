package main.view;

import javax.swing.*;

class OverWriteSavePane {

    private final String TITLE = "Overwrite saved file";
    private final String MESSAGE = "Would you like to overwrite your saved file: ";
    private final int OPTION_TYPE = JOptionPane.YES_NO_OPTION;
    private final int MESSAGE_TYPE = JOptionPane.QUESTION_MESSAGE;

    private String overwriteValues;
    private int result = -1;
    private JOptionPane pane;

    public OverWriteSavePane(JFrame frame) {
        initPane(frame);
    }

    private void initPane(JFrame frame) {
        result = JOptionPane.showConfirmDialog(frame, MESSAGE, TITLE, OPTION_TYPE ,MESSAGE_TYPE);
    }

    public boolean getResult() {
        if(result == JOptionPane.YES_OPTION){
            return true;
        }
        else{
            return false;
        }
    }
}