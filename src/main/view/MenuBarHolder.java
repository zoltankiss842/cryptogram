package main.view;


import main.game.Game;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class MenuBarHolder {
    private JMenu scoreboard;
    private JMenu exit;
    private Game gameController;  // This creates a aggregation between MenuBarHolder and Game
    private JFrame gameFrame;

    private JPanel holder;        // Holds the menu items together

    public MenuBarHolder(Game gameController, JFrame gameFrame) {
        this.gameController = gameController;
        this.gameFrame = gameFrame;


        makeMenuBar();
        initHolder();
    }

    private void initHolder() {
        holder = new JPanel();
    }

    private void makeMenuBar() {
        final JMenuBar menuBar = new JMenuBar();

        menuBar.add(this.scoreboard = new JMenu("Scoreboard"));
        scoreboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

            }
        });

        menuBar.add(this.exit = new JMenu("Exit"));
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gameController.savegame();
                jExitClicked(evt);
            }
    });
        gameFrame.setJMenuBar(menuBar);

    }

    // mouse listener for exit
    private void jExitClicked(java.awt.event.MouseEvent evt) {
        if(evt.getButton() == MouseEvent.BUTTON1)
            System.exit(0);
    }

    public JPanel getHolder() {
        return holder;
    }
}

