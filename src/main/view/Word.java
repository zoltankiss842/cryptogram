package main.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Word {

    private JPanel word;
    private ArrayList<LetterInput> letters;

    public Word(String word){
        initWord();
        createLetters(word);
        addToPanel();
    }

    private void createLetters(String word) {
        letters = new ArrayList<>();
        for(char c : word.toCharArray()){
            LetterInput newLetter = new LetterInput(String.valueOf(c));
            letters.add(newLetter);
        }
    }

    private void initWord() {
        word = new JPanel();
        word.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        word.setBorder(new EmptyBorder(10,10,10,10));
    }

    private void addToPanel() {
        for(LetterInput letterInput : letters){
            word.add(letterInput.getLetterInput());
        }
    }

    public ArrayList<LetterInput> getLetters() {
        return letters;
    }

    public void setLetters(ArrayList<LetterInput> letters) {
        this.letters = letters;
    }

    public void addLetter(LetterInput letterInput){
        this.letters.add(letterInput);
    }

    public JPanel getWord() {
        return word;
    }

    public String getTextFromInputs(){
        StringBuilder stringBuilder = new StringBuilder();
        for(LetterInput letterInput : letters){
            stringBuilder.append(letterInput.getUserInputFromField());
        }

        return stringBuilder.toString();
    }
}
