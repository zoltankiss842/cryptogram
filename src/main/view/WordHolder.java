package main.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class WordHolder {

    private ArrayList<Word> words;
    private JPanel holder;
    private JScrollPane holderScrollPane;

    private JFrame frame;

    public WordHolder(JFrame frame) {
        this.words = new ArrayList<>();
        this.frame = frame;

        initHolder();
        initScrollPane();


    }

    private void initHolder() {
        this.holder = new JPanel();
//        holder.setBorder(new LineBorder(new Color(0,255,0), 5));
        holder.setLayout(new WrapLayout());
    }

    private void initScrollPane() {
        this.holderScrollPane = new JScrollPane(holder);
        holderScrollPane.setHorizontalScrollBar(null);
        holderScrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }

    public void displayNewSentence(String encrypted){

        if(words != null){
            words.clear();
        }

        String[] tokens = encrypted.split(" ");
        for(int i = 0; i < tokens.length; ++i){
            Word newWord = new Word(tokens[i]);
            words.add(newWord);
        }

        addToPanel();
    }

    public void addToPanel(){
        for(Word word: words){
            holder.add(word.getWord());
        }

        holder.add(new JLabel());
    }

    public boolean checkAnswer(String solution){
        int i = 0;
        String[] tokens = solution.split(" ");
        for(Word word : words){
            if(!word.getTextFromInputs().equals(tokens[i])){
                return false;
            }

            i++;
        }

        return true;
    }

    public JScrollPane getHolder() {
        return holderScrollPane;
    }
}
