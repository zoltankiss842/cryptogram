package main.view;

import main.cryptogram.Cryptogram;
import main.game.Game;
import main.players.Player;

import javax.swing.*;
import java.awt.*;

public class Frame {

    public static final int FRAME_WIDTH = 852;
    public static final int FRAME_HEIGHT = 480;

    public final String FRAME_TITLE = "Cryptogrammer";

    private JFrame frame;
    private SolutionPanel solutionPanel;
    private WordHolder wordHolder;
    private ButtonHolder buttonHolder;

    private Game gameController;

    public Frame(String name, Game gameController){

        this.gameController = gameController;

        frame = new JFrame(FRAME_TITLE);

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        buttonHolder = new ButtonHolder(name, gameController);

        // Adding the two main component to the frame
        frame.add(buttonHolder.getHolder(), BorderLayout.PAGE_END);

        frame.pack();
        frame.setVisible(true);

    }

    public void displayNewGame(Cryptogram cryptogram){

        try{
            frame.remove(solutionPanel.getHolder());
            frame.remove(wordHolder.getHolder());
        }
        catch (NullPointerException e){
            System.err.print("Components were already null, cannot remove them from Frame.");
        }

        wordHolder = null;
        solutionPanel = null;

        wordHolder = new WordHolder(frame);
        wordHolder.displayNewSentence(cryptogram.getPhrase());

        solutionPanel = new SolutionPanel(cryptogram.getSolution());

        frame.add(solutionPanel.getHolder(), BorderLayout.PAGE_START);
        frame.add(wordHolder.getHolder(), BorderLayout.CENTER);

        frame.pack();
    }

}
