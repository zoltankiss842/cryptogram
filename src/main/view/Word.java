package main.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Word {

    private JPanel word;
    private ArrayList<LetterInput> letters;

    public Word(){
        letters = new ArrayList<>();
        initWord();
    }

    private void initWord() {
        word = new JPanel();
        word.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addToPanel();
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
}
