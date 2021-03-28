package main.view;

import javax.swing.*;

public class PlainLetterInUseMessagePane {

    public PlainLetterInUseMessagePane(JFrame frame, String message) {
        initPane(frame, message);
    }

    private void initPane(JFrame frame, String message) {
        String TITLE = "Plain letter already in use";
        int MESSAGE_TYPE = JOptionPane.ERROR_MESSAGE;
        JOptionPane.showMessageDialog(frame, message, TITLE, MESSAGE_TYPE);
    }

}
