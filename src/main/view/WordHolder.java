package main.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class represents a list of words.
 */
public class WordHolder {


    private ArrayList<Word> words;
    private JPanel holder;
    private JScrollPane holderScrollPane;
    private Frame gameFrame;

    private JFrame frame;

    public WordHolder(JFrame frame, Frame gameFrame) {
        this.words = new ArrayList<>();
        this.frame = frame;
        this.gameFrame = gameFrame;

        initHolder();
        initScrollPane();
    }

    private void initHolder() {
        this.holder = new JPanel();
        holder.setLayout(new WrapLayout());
        holder.setBackground(Frame.QUEENBLUE);
    }

    private void initScrollPane() {
        this.holderScrollPane = new JScrollPane(holder);
        holderScrollPane.setHorizontalScrollBar(null);
        holderScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        holderScrollPane.setBackground(Frame.QUEENBLUE);
    }

    /**
     * This is where we convert the String sentence into a visible
     * user interface.
     * @param encrypted     the encrypted sentence to be converted
     */
    public void displayNewSentence(String encrypted){

        // We reset the words
        if(words != null){
            words.clear();
        }

        String[] tokens = encrypted.split(" ");
        for(int i = 0; i < tokens.length; ++i){
            Word newWord = new Word(tokens[i], this);
            words.add(newWord);
        }

        addToPanel();
    }

    /**
     * Adding the words to the panel, making it available to show
     */
    private void addToPanel(){
        for(Word word: words){
            holder.add(word.getWord());
        }

        holder.add(new JLabel());
    }

    /**
     * Here we check the user input is the same as the parameter solution
     * @param solution          the solution it should be matched to
     * @return                  whether the input matches the solution
     */
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

    public void displayEmptyPanel(){
        // We reset the words
        if(words != null){
            words.clear();
        }

        holder.setLayout(new BorderLayout());

        JLabel defaultMessage = new JLabel("Start a new game or load an existing one to play!");
        defaultMessage.setForeground(Color.WHITE);
        defaultMessage.setHorizontalAlignment(JLabel.CENTER);
        defaultMessage.setVerticalAlignment(JLabel.CENTER);

        holder.add(defaultMessage, BorderLayout.CENTER);
    }

    public JScrollPane getHolder() {
        return holderScrollPane;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public Frame getFrame() {
        return gameFrame;
    }
}
