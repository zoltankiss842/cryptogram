package main.view;

import javax.swing.*;

public class GameCompletedMessagePane {

    public GameCompletedMessagePane(JFrame frame, boolean success) {
        initPane(frame, success);
    }

    private void initPane(JFrame frame, boolean success) {
        String TITLE = "Game Completed";
        int MESSAGE_TYPE = JOptionPane.PLAIN_MESSAGE;
        if(success){
            String SUCCESS_MESSAGE = "Congratulations! You have successfully completed the cryptogram!";
            JOptionPane.showMessageDialog(frame, SUCCESS_MESSAGE, TITLE, MESSAGE_TYPE);
        }
        else{
            String FAIL_MESSAGE = "Unfortunately you failed this cryptogram. Better luck next time!";
            JOptionPane.showMessageDialog(frame, FAIL_MESSAGE, TITLE, MESSAGE_TYPE);
        }
    }

}
