package main.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ButtonHolder {

    private JPanel holder;
    private JLabel playerName;

    public ButtonHolder(String name) {
        this.holder = new JPanel();
//        holder.setBorder(new LineBorder(new Color(255,0,0), 5));

        playerName = new JLabel(name);

        holder.add(playerName);
        holder.add(new JButton("New Game"));
        holder.add(new JButton("Submit"));
        holder.add(new JButton("Reset"));
    }

    public JPanel getHolder() {
        return holder;
    }
}
