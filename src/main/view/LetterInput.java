package main.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

public class LetterInput {

    private final int WIDTH = 30;
    private final int HEIGHT = 30;

    private JFormattedTextField userGuess;
    private JLabel encryptedLetter;
    private JPanel letterInput;

    private Word word;

    private String originalLetter;

    public LetterInput(String encryptedLetter, Word word){

        this.word = word;

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
        userGuess.setFocusLostBehavior(JFormattedTextField.COMMIT);
        userGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
        userGuess.setHorizontalAlignment(JFormattedTextField.CENTER);
        userGuess.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        userGuess.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        userGuess.setMaximumSize(new Dimension(WIDTH, HEIGHT));

        userGuess.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = userGuess.getText();
                if(!text.isEmpty() && !text.isBlank()){
                    updateLetterLabel(text);
                }
                else{
                    updateLetterLabel(originalLetter);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLetterLabel(originalLetter);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String text = userGuess.getText();
                if(!text.isEmpty() && !text.isBlank()){
                    updateLetterLabel(text);
                }
                else{
                    updateLetterLabel(originalLetter);
                }

            }
        });

        userGuess.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = userGuess.getText();
                if(!text.isEmpty() && !text.isBlank()){
                    updateLetterLabel(text);
                    word.updateLetterLabel(originalLetter, text);
                }
                else{
                    updateLetterLabel(originalLetter);
                    word.updateLetterLabel(originalLetter, null);
                }
            }
        });
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
        originalLetter = letter;
        encryptedLetter.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public String getUserInputFromField(){
        return userGuess.getText();
    }

    public JPanel getLetterInput() {
        return letterInput;
    }

    public void updateLetterLabel(String newLetter){

        if(newLetter != null && !newLetter.isBlank() && !newLetter.isEmpty()){
            encryptedLetter.setText(newLetter);
        }
        else{
            encryptedLetter.setText(originalLetter);
        }

        encryptedLetter.revalidate();
        letterInput.revalidate();
    }

    public void updateInputFieldValue(String newLetter){

        Runnable modify = new Runnable() {
            @Override
            public void run() {
                userGuess.setText(newLetter);
                userGuess.setValue(newLetter);
                userGuess.revalidate();
                letterInput.revalidate();
            }
        };

        SwingUtilities.invokeLater(modify);


    }

    public String getOriginalLetter() {
        return originalLetter;
    }
}
