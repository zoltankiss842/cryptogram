package main.view;


import main.game.Game;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class MenuBarHolder {
    private final Game gameController;  // This creates a aggregation between MenuBarHolder and Game
    private final JFrame gameFrame;

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

        JMenu scoreboard;
        menuBar.add(scoreboard = new JMenu("Scoreboard"));
        scoreboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                gameController.showstats();
            }

        });

        JMenu commonFrequencies;
        menuBar.add(commonFrequencies = new JMenu("Common frequencies"));
        commonFrequencies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                gameController.showFrequencies();
            }
        });


        JMenu exit;
        menuBar.add(exit = new JMenu("Exit"));
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
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

