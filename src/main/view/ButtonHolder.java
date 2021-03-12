package main.view;

import main.game.Game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class is for managing the buttons that the player see
 * at the bottom of the window.
 */
public class ButtonHolder {

    private JPanel holder;        // Holds the buttons together
    private JPanel nameHolder;
    private JLabel playerName;    // Label for the player's username
    private Game gameController;  // This creates a aggregation between ButtonsHolder and Game

    private JButton newGame;
    private JButton reset;

    public ButtonHolder(String name, Game gameController) {

        this.gameController = gameController;

        initHolder();
        initButtons();
        initPlayerStatistics(name);

    }

    /**
     * This is the actual panel that holds every button.
     * Here we initialize it.
     */
    private void initHolder() {
        holder = new JPanel();
//        holder.setBorder(new LineBorder(new Color(255,0,0), 5));
    }

    /**
     * This is where we initialize buttons,
     * by adding text and action listeners to them.
     */
    private void initButtons() {
        newGame = new JButton("New Game");

        // Clicking on the "New Game" button, it generates a new sentence
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.playGame();
            }
        });


        // Clicking on the "Reset" button clears every input field
        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.resetMappings();
            }
        });

        holder.add(newGame);
//        holder.add(submit);
        holder.add(reset);
    }

    /**
     * Here we create the player username display,
     * which is interactive, so the user can click on it and can check their
     * statistics.
     * @param name      the players username
     */
    private void initPlayerStatistics(String name) {
        nameHolder = new JPanel(new FlowLayout(FlowLayout.CENTER));

        nameHolder.addMouseListener(createMouseListener());

        playerName = new JLabel(name);

        nameHolder.add(playerName);

        holder.add(nameHolder);
    }

    private MouseListener createMouseListener() {
        MouseListener mouseListener = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                CurrentPlayerStatistics statistics = new CurrentPlayerStatistics(gameController.getCurrentPlayer());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                playerName.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                nameHolder.setBorder(new LineBorder(new Color(0,0,0), 1));
                nameHolder.setToolTipText("Check your statistics!");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playerName.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                nameHolder.setBorder(null);
            }
        };

        return mouseListener;
    }

    public JPanel getHolder() {
        return holder;
    }
}
