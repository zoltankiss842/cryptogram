package main.view;

import main.game.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * This is the first UI component that is shown to
 * the user. Here the user enters their username and
 * then clicks on the "Play" button.
 */
public class NewPlayerFrame {

    private JFrame playerFrame;
    private JLabel prompt;
    private JTextField playerName;
    private String name;
    private JButton play;
    private JButton exit;

    private Game newGame;

    public NewPlayerFrame() throws IOException {
        initFrame();
        initComponents();

        playerFrame.pack();
        playerFrame.setLocationRelativeTo(null);
        playerFrame.setVisible(true);
    }

    private void initFrame() throws IOException {
        playerFrame = new JFrame("New Player");
        playerFrame.setIconImage(ImageIO.read(new File("resources/assets/add-group.png")));
        playerFrame.setPreferredSize(new Dimension(500,100));
        playerFrame.setMinimumSize(new Dimension(500,100));
        playerFrame.setLayout(new BorderLayout());
        playerFrame.setResizable(false);
        playerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        JPanel panel = new JPanel();

        panel.setBorder(new EmptyBorder(15,1,1,1));

        prompt = new JLabel("Please enter your name: ");
        prompt.setVerticalAlignment(JLabel.CENTER);

        playerName = new JTextField();
        playerName.setPreferredSize(new Dimension(100,20));

        play = new JButton("Play");
        play.setVerticalAlignment(JButton.CENTER);

        // Here we check if the entered name is empty, if not we show the game frame
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = playerName.getText();

                if(name.isEmpty() || name.isBlank() || name == null){
                    JOptionPane.showMessageDialog(playerFrame, "Name cannot be empty", "Name error", JOptionPane.ERROR_MESSAGE);
                }
                else if(name.length() > 30){
                    JOptionPane.showMessageDialog(playerFrame, "Name too long", "Name error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    playerFrame.setVisible(false);

                    try {
                        initNewGame(name);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }


            }
        });

        exit = new JButton("Exit");
        exit.setVerticalAlignment(JButton.CENTER);

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

    private void initNewGame(String name) throws Exception {
        newGame = new Game(name);
    }

}
