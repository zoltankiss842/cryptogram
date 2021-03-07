package main.view;

import javax.swing.*;

public class PlainLetterInUseMessagePane {

    private final String TITLE = "Plain letter already in use";
    private final int MESSAGE_TYPE = JOptionPane.ERROR_MESSAGE;

    public PlainLetterInUseMessagePane(JFrame frame, String message) {
        initPane(frame, message);
    }

    private void initPane(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, TITLE, MESSAGE_TYPE);
    }

}
