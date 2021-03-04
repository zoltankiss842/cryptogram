package main.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class WordHolder {

    private ArrayList<Word> words;
    private JPanel holder;
    private JScrollPane holderScrollPane;

    public WordHolder() {
        initHolder();
        initScrollPane();

        this.words = new ArrayList<>();
    }

    private void initScrollPane() {
        this.holderScrollPane = new JScrollPane();
        holderScrollPane.setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));

        addToPanel();
    }

    private void initHolder() {
        this.holder = new JPanel();
        holder.setBorder(new LineBorder(new Color(0,255,0), 5));
    }

    public void addToPanel(){
        for(Word word: words){
            holderScrollPane.add(word.getWord());
        }
    }

    public void displayNewSentence(String encrypted){
        String[] tokens = encrypted.split(" ");
        for(int i = 0; i < tokens.length; ++i){

        }
    }

    public JPanel getHolder() {
        return holder;
    }
}
