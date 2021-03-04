package main.view;

import javax.swing.*;
import java.awt.*;

public class Frame {

    public final int FRAME_WIDTH = 600;
    public final int FRAME_HEIGHT = 400;

    public final String FRAME_TITLE = "Cryptogrammer";

    private JFrame frame;
    private WordHolder wordHolder;
    private ButtonHolder buttonHolder;

    public Frame(){
        wordHolder = new WordHolder();
        buttonHolder = new ButtonHolder();

        frame = new JFrame(FRAME_TITLE);

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adding the two main component to the frame
        frame.add(wordHolder.getHolder(), BorderLayout.CENTER);
        frame.add(buttonHolder.getHolder(), BorderLayout.PAGE_END);

        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);

    }

}
