package main.view;

import javax.swing.*;
import java.awt.*;

public class LetterInput {

    private final int WIDTH = 30;
    private final int HEIGHT = 40;

    private JTextField userGuess;
    private JLabel encryptedLetter;
    private JPanel letterInput;

    public LetterInput(String encryptedLetter){
        initLetterInput();
        initEncryptedLetter(encryptedLetter);
        initUserGuess();
        assembleLetterInput();
    }

    private void assembleLetterInput() {
        letterInput.add(userGuess);
        letterInput.add(encryptedLetter);
    }

    private void initUserGuess() {
        this.userGuess = new JTextField();
        userGuess.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        userGuess.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        userGuess.setMaximumSize(new Dimension(WIDTH, HEIGHT));
    }

    private void initLetterInput() {
        this.letterInput = new JPanel();
        letterInput.setLayout(new BoxLayout(letterInput, BoxLayout.Y_AXIS));
    }

    private void initEncryptedLetter(String letter) {
        this.encryptedLetter = new JLabel(letter, JLabel.CENTER);
    }

    public JPanel getLetterInput() {
        return letterInput;
    }
}
