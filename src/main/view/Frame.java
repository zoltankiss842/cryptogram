package main.view;

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
    private Player player;

    public Frame(String name){
        frame = new JFrame(FRAME_TITLE);

        player = new Player(name);

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        wordHolder = new WordHolder(frame);
        buttonHolder = new ButtonHolder(player.getUsername());
        solutionPanel = new SolutionPanel("This is a very long sentence that will be displayed so we will see what is going to happen");

        wordHolder.displayNewSentence("This is a very long sentence that will be displayed so we will see what is going to happen");

        // Adding the two main component to the frame
        frame.add(solutionPanel.getHolder(), BorderLayout.PAGE_START);
        frame.add(wordHolder.getHolder(), BorderLayout.CENTER);
        frame.add(buttonHolder.getHolder(), BorderLayout.PAGE_END);

        frame.pack();
        frame.setVisible(true);

    }

}
