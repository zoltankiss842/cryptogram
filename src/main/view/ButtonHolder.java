package main.view;

import main.game.Game;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonHolder {

    private JPanel holder;
    private JLabel playerName;

    private JButton newGame;
    private JButton submit;
    private JButton reset;

    private Game gameController;

    public ButtonHolder(String name, Game gameController) {
        this.holder = new JPanel();
        this.gameController = gameController;
//        holder.setBorder(new LineBorder(new Color(255,0,0), 5));

        playerName = new JLabel(name);

        initButtons();

        holder.add(playerName);

    }

    private void initButtons() {
        newGame = new JButton("New Game");

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.playGame();
            }
        });

        submit = new JButton("Submit");

        reset = new JButton("Reset");

        holder.add(newGame);
        holder.add(submit);
        holder.add(reset);
    }

    public JPanel getHolder() {
        return holder;
    }
}
