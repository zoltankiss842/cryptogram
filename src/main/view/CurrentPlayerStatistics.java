package main.view;

import main.players.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This class is for simply creating a frame which displays
 * the players statistics, getting the player data from
 * the game controller.
 */
public class CurrentPlayerStatistics {

    private JFrame frame;
    private JPanel statsHolder;

    public CurrentPlayerStatistics(Player player) {
        initFrame();
        initPanel();
        initLabels(player);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initFrame() {
        frame = new JFrame("Your statistics");
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setResizable(false);
        frame.requestFocus();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            frame.setIconImage(ImageIO.read(new File("resources/assets/graph.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPanel() {
        statsHolder = new JPanel();
        statsHolder.setLayout(new BorderLayout());
        statsHolder.setBorder(new EmptyBorder(10,10,10,10));

        frame.add(statsHolder);
    }

    private void initLabels(Player player) {

        JPanel dataHolder = new JPanel();
        dataHolder.setLayout(new GridLayout(7,2));

        JLabel playerNameLabel = new JLabel("<html>Username:</html>");
        playerNameLabel.setHorizontalAlignment(JLabel.LEFT);
        JLabel playerName = new JLabel(player.getUsername());
        playerName.setHorizontalAlignment(JLabel.RIGHT);

        JLabel playerAccuracyLabel = new JLabel("<html>Accuracy:</html>");
        playerAccuracyLabel.setHorizontalAlignment(JLabel.LEFT);
        JLabel playerAccuracy = new JLabel(String.format("%.2f", player.getAccuracy()));
        playerAccuracy.setHorizontalAlignment(JLabel.RIGHT);

        JLabel playerTotalGuessesLabel = new JLabel("<html>Total guesses:</html>");
        playerTotalGuessesLabel.setHorizontalAlignment(JLabel.LEFT);
        JLabel playerTotalGuesses = new JLabel(String.valueOf(player.getTotalGuesses()));
        playerTotalGuesses.setHorizontalAlignment(JLabel.RIGHT);

        JLabel playerTotalCorrectGuessesLabel = new JLabel("<html>Total correct guesses:</html>");
        playerTotalCorrectGuessesLabel.setHorizontalAlignment(JLabel.LEFT);
        JLabel playerTotalCorrectGuesses = new JLabel(String.valueOf(player.getTotalCorrectGuesses()));
        playerTotalCorrectGuesses.setHorizontalAlignment(JLabel.RIGHT);

        JLabel playerGamePlayedLabel = new JLabel("<html>Games played:</html>");
        playerGamePlayedLabel.setHorizontalAlignment(JLabel.LEFT);
        JLabel playerGamePlayed = new JLabel(String.valueOf(player.getNumCryptogramsPlayed()));
        playerGamePlayed.setHorizontalAlignment(JLabel.RIGHT);

        JLabel playerGameCompletedLabel = new JLabel("<html>Games completed:</html>");
        playerGameCompletedLabel.setHorizontalAlignment(JLabel.LEFT);
        JLabel playerGameCompleted = new JLabel(String.valueOf(player.getNumCryptogramsCompleted()));
        playerGameCompleted.setHorizontalAlignment(JLabel.RIGHT);

        JLabel playerGameSuccessfullyCompletedLabel = new JLabel("<html>Games correctly completed:</html>");
        playerGameSuccessfullyCompletedLabel.setHorizontalAlignment(JLabel.LEFT);
        JLabel playerGameSuccessfullyCompleted = new JLabel(String.valueOf(player.getNumCryptogramsSuccessfullyCompleted()));
        playerGameSuccessfullyCompleted.setHorizontalAlignment(JLabel.RIGHT);

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
        dataHolder.add(playerGameSuccessfullyCompletedLabel);
        dataHolder.add(playerGameSuccessfullyCompleted);

        statsHolder.add(dataHolder, BorderLayout.CENTER);
    }
}
