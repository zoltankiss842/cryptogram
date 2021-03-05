package main.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class is a visual representation of a word.
 * A word contains LetterInputs.
 */
public class Word {

    private JPanel word;
    private ArrayList<LetterInput> letters;
    private WordHolder wordHolder;

    public Word(String word, WordHolder wordHolder){
        this.wordHolder = wordHolder;
        initWord();
        createLetters(word);
        addToPanel();
    }

    private void initWord() {
        word = new JPanel();
        word.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        word.setBorder(new EmptyBorder(10,10,10,10));
    }

    private void createLetters(String word) {
        letters = new ArrayList<>();
        for(char c : word.toCharArray()){
            LetterInput newLetter = new LetterInput(String.valueOf(c), this);
            letters.add(newLetter);
        }
    }

    private void addToPanel() {
        for(LetterInput letterInput : letters){
            word.add(letterInput.getLetterInput());
        }
    }

    public ArrayList<LetterInput> getLetters() {
        return letters;
    }

    public JPanel getWord() {
        return word;
    }

    /**
     * This class returns a string of the combined
     * inputs from the textfields. Used for checking
     * the users attempt.
     * @return      a String of the inputs
     */
    public String getTextFromInputs(){
        StringBuilder stringBuilder = new StringBuilder();
        for(LetterInput letterInput : letters){
            stringBuilder.append(letterInput.getUserInputFromField());
        }

        return stringBuilder.toString();
    }

    /**
     * This method updates both the letter label and the input fields value.
     * After the user enters a letter in one of the text fields, it goes through every word
     * and letter, and if the letter is the same as where the user inputted, then it substitutes it.
     * @param original          the original letter that was at there place
     * @param inputLetter       the new letter the will replace both the label and input field
     */
    public void updateLetterLabel(String original, String inputLetter){
        for(Word words : wordHolder.getWords()){
            for(LetterInput input : words.getLetters()){
                if(original.equals(input.getOriginalLetter())){
                    input.updateLetterLabel(inputLetter);
                    input.updateInputFieldValue(inputLetter);
                }
            }
        }
    }
}
