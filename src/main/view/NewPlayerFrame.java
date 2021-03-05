package main.view;

import main.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewPlayerFrame {

    private JFrame playerFrame;
    private JLabel prompt;
    private JTextField playerName;
    private String name;
    private JButton play;
    private JButton exit;

    private Game newGame;

    public NewPlayerFrame() {
        initFrame();
        initComponents();

        playerFrame.pack();
        playerFrame.setVisible(true);
    }

    private void initFrame() {
        playerFrame = new JFrame("New Player");
        playerFrame.setPreferredSize(new Dimension(500,100));
        playerFrame.setMinimumSize(new Dimension(500,100));
        playerFrame.setLayout(new BorderLayout());
        playerFrame.setResizable(false);
        playerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerFrame.setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        prompt = new JLabel("Please enter your name: ");

        playerName = new JTextField();
        playerName.setPreferredSize(new Dimension(100,20));

        play = new JButton("Play");

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = playerName.getText();

                if(name.isEmpty() || name.isBlank() || name == null){
                    JOptionPane.showMessageDialog(playerFrame, "Name cannot be empty", "Name error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    playerFrame.setVisible(false);

                    initNewGame(name);
                }


            }
        });

        exit = new JButton("Exit");

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(prompt);
        panel.add(playerName);
        panel.add(play);
        panel.add(exit);

        playerFrame.add(panel, BorderLayout.CENTER);
    }

    private void initNewGame(String name) {
        newGame = new Game(name);
    }



}
