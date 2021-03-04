package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

public class NewPlayerFrame {

    private JFrame playerFrame;
    private Frame gameFrame;
    private JLabel prompt;
    private JTextField playerName;
    private String name;
    private JButton play;
    private JButton exit;

    public NewPlayerFrame() {
        initFrame();
        initComponents();
    }

    private void initGameFrame(String name) {
        gameFrame = new Frame(name);
    }

    private void initFrame() {
        playerFrame = new JFrame("New Player");
        playerFrame.setPreferredSize(new Dimension(800,100));
        playerFrame.setMinimumSize(new Dimension(800,100));
        playerFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        playerFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        playerFrame.setLocationRelativeTo(null);

        playerFrame.setVisible(true);
    }

    private void initComponents() {
        prompt = new JLabel("Please enter your name: ");

        playerName = new JTextField();
        playerName.setPreferredSize(new Dimension(100,20));

        play = new JButton("Play");

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = playerName.getText();

                playerFrame.setVisible(false);

                initGameFrame(name);
            }
        });

        exit = new JButton("Exit");

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        playerFrame.add(prompt);
        playerFrame.add(playerName);
        playerFrame.add(play);
        playerFrame.add(exit);
    }



}
