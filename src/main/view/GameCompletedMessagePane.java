package main.view;

import javax.swing.*;

public class GameCompletedMessagePane {

    private final String TITLE = "Game Completed";
    private final String SUCCESS_MESSAGE = "Congratulations! You have successfully completed the cryptogram!";
    private final String FAIL_MESSAGE = "Unfortunately you failed this cryptogram. Better luck next time!";
    private final int MESSAGE_TYPE = JOptionPane.PLAIN_MESSAGE;

    public GameCompletedMessagePane(JFrame frame, boolean success) {
        initPane(frame, success);
    }

    private void initPane(JFrame frame, boolean success) {
        if(success){
            JOptionPane.showMessageDialog(frame, SUCCESS_MESSAGE, TITLE, MESSAGE_TYPE);
        }
        else{
            JOptionPane.showMessageDialog(frame, FAIL_MESSAGE, TITLE, MESSAGE_TYPE);
        }
    }

}
