package main.view;

import javax.swing.*;
import java.awt.*;

public class Frame {

    private JFrame frame;

    public Frame(){
        frame = new JFrame("Cryptogrammer");

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello World!");

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        frame.add(label, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
