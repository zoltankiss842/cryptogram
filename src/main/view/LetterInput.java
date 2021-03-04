package main.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class LetterInput {

    private final int WIDTH = 30;
    private final int HEIGHT = 30;

    private JFormattedTextField userGuess;
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
        this.userGuess = new JFormattedTextField(createUserInputFormatter("?"));
        userGuess.setColumns(3);
        userGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
        userGuess.setHorizontalAlignment(JFormattedTextField.CENTER);
        userGuess.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        userGuess.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        userGuess.setMaximumSize(new Dimension(WIDTH, HEIGHT));
    }

    private MaskFormatter createUserInputFormatter(String s) {
        MaskFormatter formatter = null;
        try{
            formatter = new MaskFormatter(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatter;
    }

    private void initLetterInput() {
        this.letterInput = new JPanel();
        letterInput.setLayout(new BoxLayout(letterInput, BoxLayout.Y_AXIS));
//        letterInput.setBorder(new LineBorder(new Color(0,0,0),1));
    }

    private void initEncryptedLetter(String letter) {
        this.encryptedLetter = new JLabel(letter);
        encryptedLetter.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public String getUserInputFromField(){
        return userGuess.getText();
    }

    public JPanel getLetterInput() {
        return letterInput;
    }
}
