package main.view;

import main.cryptogram.LetterCryptogram;
import main.exceptions.PlainLetterAlreadyInUse;
import main.game.Game;

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

    private JFormattedTextField userGuess;  // Input for the user
    private JLabel encryptedLetter;         // Label for the encrypted letter
    private JPanel letterInput;             // Holds the text field and the label together

    private final Word word;                      // Makes an aggregation between LetterInput and Word

    private String originalLetter;          // This stores the original letter, in case the user resets, it can use
                                            // this field to access the old letter.
    private String previousUserInput;

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
        letterInput.setBackground(Frame.QUEENBLUE);
    }

    public void initEncryptedLetter(String letter) {
        this.encryptedLetter = new JLabel(letter);
        encryptedLetter.setForeground(Color.WHITE);
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
        this.userGuess = new JFormattedTextField(createUserInputFormatter());

        userGuess.setColumns(3);

        userGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
        userGuess.setHorizontalAlignment(JFormattedTextField.CENTER);

        userGuess.setFocusLostBehavior(JFormattedTextField.COMMIT);

        int WIDTH = 30;
        int HEIGHT = 30;
        userGuess.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        userGuess.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        userGuess.setMaximumSize(new Dimension(WIDTH, HEIGHT));

        previousUserInput = userGuess.getText();

        userGuess.getDocument().addDocumentListener(createDocumentListener());

        userGuess.addFocusListener(createNewFocusListener());
    }

    /**
     * Formatter for the user input fields
     * @return          formatter for user input
     */
    private MaskFormatter createUserInputFormatter() {
        MaskFormatter formatter = null;
        try{
            formatter = new MaskFormatter("?");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatter;
    }

    /**
     * This updates the value in the input field.
     * Good for resetting as well.
     * @param newLetter     if null, the field will be empty, else the field value is newLetter
     */
    public void updateInputFieldValue(String newLetter){

        // This runnable is for prevent threading problems
        Runnable modify = () -> {
            previousUserInput = newLetter;
            userGuess.setText(newLetter);
            userGuess.setValue(newLetter);
            userGuess.revalidate();
            letterInput.revalidate();
        };

        SwingUtilities.invokeLater(modify);

    }

    public void disableField(){

        // This runnable is for prevent threading problems
        Runnable modify = () -> {
            userGuess.setEnabled(false);
            userGuess.revalidate();
            letterInput.revalidate();
        };

        SwingUtilities.invokeLater(modify);

    }

    private DocumentListener createDocumentListener(){

        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {}

            @Override
            public void removeUpdate(DocumentEvent e) {}

            @Override
            public void changedUpdate(DocumentEvent e) {}
        };
    }

    private FocusListener createNewFocusListener(){

        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(!e.isTemporary()){
                    String text = userGuess.getText();
                    Game game = getGameController();
                    if(!text.isEmpty() && !text.isBlank()){
                        try {
                            game.enterLetter(originalLetter, text);
                            if(game.isOverwrite()){
                                word.updateLetterLabel(originalLetter, text);
                                game.setOverwrite(false);
                                previousUserInput = text;
                            }
                            else{
                                word.updateLetterLabel(originalLetter, previousUserInput);
                            }

                            if(game.isFinished()){
                                word.updateLetterLabel(originalLetter, text);
                            }

                        } catch (Exception exception) {
                            word.updateLetterLabel(originalLetter, previousUserInput);
                            if(exception instanceof PlainLetterAlreadyInUse){
                                new PlainLetterInUseMessagePane(
                                        word.getWordHolder().getFrame().getFrame(),
                                        exception.getMessage());
                            }
                            System.err.println(exception.getMessage());
                        }


                    }
                    else{
                        try {
                            if(previousUserInput != null && !previousUserInput.isEmpty() && !previousUserInput.isBlank()){
                                DeleteCharacterOptionPane pane = new DeleteCharacterOptionPane(game.getGameGui().getFrame());
                                if(pane.getResult()){
                                    game.undoLetter(originalLetter);
                                    word.updateLetterLabel(originalLetter, null);
                                    previousUserInput = text;
                                }
                                else{
                                    userGuess.setValue(previousUserInput);
                                    userGuess.setText(previousUserInput);
                                }
                            }
                            else{
                                game.undoLetter(originalLetter);
                                word.updateLetterLabel(originalLetter, null);
                                previousUserInput = text;
                            }
                        } catch (Exception exception) {
                            System.err.println(exception.getMessage());
                        }
                    }


                    if(game.getPlayerGameMapping().get(game.getCurrentPlayer()) instanceof LetterCryptogram){
                        if(game.getInputFromUserLetter() != null){
                            System.out.println(game.getInputFromUserLetter().toString());
                        }
                    }
                    else{
                        if(game.getInputFromUserNumber() != null){
                            System.out.println(game.getInputFromUserNumber().toString());
                        }
                    }
                }
            }
        };
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

    private Game getGameController(){
        return this.word.getWordHolder().getFrame().getGameController();
    }

}
