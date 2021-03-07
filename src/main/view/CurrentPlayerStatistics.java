package main.view;

import main.players.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * This class is for simply creating a frame which displays
 * the players statistics, getting the player data from
 * the game controller.
 */
public class CurrentPlayerStatistics {

    private JFrame frame;
    private JPanel statsHolder;
    private JLabel playerName;
    private JLabel playerAccuracy;
    private JLabel playerTotalGuesses;
    private JLabel playerTotalCorrectGuesses;
    private JLabel playerGamePlayed;
    private JLabel playerGameCompleted;

    public CurrentPlayerStatistics(Player player) {
        initFrame();
        initPanel();
        initLabels(player);

        centerFrame();
        frame.pack();
        frame.setVisible(true);
    }

    private void initFrame() {
        frame = new JFrame("Your statistics");
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setResizable(false);
        frame.requestFocus();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    }

    private void initPanel() {
        statsHolder = new JPanel();
        statsHolder.setLayout(new BorderLayout());
        statsHolder.setBorder(new EmptyBorder(10,10,10,10));

        frame.add(statsHolder);
    }

    private void initLabels(Player player) {

        JPanel dataHolder = new JPanel();
        dataHolder.setLayout(new GridLayout(0,2));

        JLabel playerNameLabel = new JLabel("Username:");
        playerName = new JLabel(player.getUsername());

        JLabel playerAccuracyLabel = new JLabel("Accuracy:");
        playerAccuracy = new JLabel(String.format("%.2f", player.getAccuracy()));

        JLabel playerTotalGuessesLabel = new JLabel("Total guesses:");
        playerTotalGuesses = new JLabel(String.valueOf(player.getTotalGuesses()));

        JLabel playerTotalCorrectGuessesLabel = new JLabel("Total correct guesses:");
        playerTotalCorrectGuesses = new JLabel(String.valueOf(player.getTotalCorrectGuesses()));

        JLabel playerGamePlayedLabel = new JLabel("Games played:");
        playerGamePlayed = new JLabel(String.valueOf(player.getNumCryptogramsPlayed()));

        JLabel playerGameCompletedLabel = new JLabel("Games completed:");
        playerGameCompleted = new JLabel(String.valueOf(player.getNumCryptogramsCompleted()));

        dataHolder.add(playerNameLabel);
        dataHolder.add(playerName);
        dataHolder.add(playerAccuracyLabel);
        dataHolder.add(playerAccuracy);
        dataHolder.add(playerTotalGuessesLabel);
        dataHolder.add(playerTotalGuesses);
        dataHolder.add(playerTotalCorrectGuessesLabel);
        dataHolder.add(playerTotalCorrectGuesses);
        dataHolder.add(playerGamePlayedLabel);
        dataHolder.add(playerGamePlayed);
        dataHolder.add(playerGameCompletedLabel);
        dataHolder.add(playerGameCompleted);

        statsHolder.add(dataHolder, BorderLayout.CENTER);
    }

    private void centerFrame(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }
}
