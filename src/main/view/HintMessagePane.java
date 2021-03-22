package main.view;

import javax.swing.*;

public class HintMessagePane {

    private final String TITLE = "Hint";
    private final String MESSAGE = "Here is your hint: ";
    private final int MESSAGE_TYPE = JOptionPane.PLAIN_MESSAGE;

    private String hintMessage;

    public HintMessagePane(JFrame frame, Object encrypted, Character solution) {
        this.hintMessage = encrypted + "->" + solution;
        initPane(frame);
    }

    private void initPane(JFrame frame) {
        JOptionPane.showMessageDialog(frame, MESSAGE + hintMessage, TITLE, MESSAGE_TYPE);

    }


}