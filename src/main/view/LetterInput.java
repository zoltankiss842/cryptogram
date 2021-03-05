package main.view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

/**
 * This class is represents a letter, where the user
 * can guess/enter their inputs. This contains a text field
 * and a label which is the encrypted letter
 */
public class LetterInput {

    private final int WIDTH = 30;
    private final int HEIGHT = 30;

    private JFormattedTextField userGuess;  // Input for the user
    private JLabel encryptedLetter;         // Label for the encrypted letter
    private JPanel letterInput;             // Holds the text field and the label together

    private Word word;                      // Makes an aggregation between LetterInput and Word

    private String originalLetter;          // This stores the original letter, in case the user resets, it can use
                                            // this field to access the old letter.

    public LetterInput(String encryptedLetter, Word word){

        this.word = word;

        initLetterInput();
        initEncryptedLetter(encryptedLetter);
        initUserGuess();
        assembleLetterInput();
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

    private void assembleLetterInput() {
        letterInput.add(userGuess);
        letterInput.add(encryptedLetter);
    }

    /**
     * Here we set up the user input field.
     * We only accept 1 letter only ("?" means that)
     * We align center and set the focus and change behaviors here.
     */
    private void initUserGuess() {
        this.userGuess = new JFormattedTextField(createUserInputFormatter("?"));

        userGuess.setColumns(3);

        userGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
        userGuess.setHorizontalAlignment(JFormattedTextField.CENTER);

        userGuess.setFocusLostBehavior(JFormattedTextField.COMMIT);

        userGuess.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        userGuess.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        userGuess.setMaximumSize(new Dimension(WIDTH, HEIGHT));

        userGuess.getDocument().addDocumentListener(createDocumentListener());

        userGuess.addFocusListener(createNewFocusListener());
    }

    /**
     * Formatter for the user input fields
     * @param s         type of format
     * @return          formatter for user input
     */
    private MaskFormatter createUserInputFormatter(String s) {
        MaskFormatter formatter = null;
        try{
            formatter = new MaskFormatter(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatter;
    }

    /**
     * This method updates the label that is under the text field.
     * If the parameter is null, it sets back to the original letter, that it was
     * @param newLetter      new letter to be set to
     */
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

    /**
     * This updates the value in the input field.
     * Good for resetting as well.
     * @param newLetter     if null, the field will be empty, else the field value is newLetter
     */
    public void updateInputFieldValue(String newLetter){

        // This runnable is for prevent threading problems
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

    private DocumentListener createDocumentListener(){
        DocumentListener dl = new DocumentListener() {
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
        };

        return dl;
    }

    private FocusListener createNewFocusListener(){
        FocusListener fl = new FocusListener() {
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
        };

        return fl;
    }

    public String getOriginalLetter() {
        return originalLetter;
    }

    public String getUserInputFromField(){
        return userGuess.getText();
    }

    public JPanel getLetterInput() {
        return letterInput;
    }
}
