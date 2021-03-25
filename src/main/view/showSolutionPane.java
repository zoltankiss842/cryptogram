package main.view;

import main.game.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/**
 * This class is for displaying the solution
 * to the current cryptogram when the user
 * presses the button to see the solution
 */

public class showSolutionPane {
    private JFrame frame;
    private JPanel holder;
    private JLabel solution;

    public showSolutionPane() {
        initFrame();
        initPanel();

        centerFrame();
        frame.pack();
        frame.setVisible(true);
    }

    private void initFrame() {
        frame = new JFrame("Cryptogram Solution");
        frame.setPreferredSize(new Dimension(300, 300));
        frame.setResizable(false);
        frame.requestFocus();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initPanel() {
        holder = new JPanel();
        holder.setLayout(new BorderLayout());
        holder.setBorder(new EmptyBorder(10, 10, 10, 10));

        frame.add(holder);
    }

    private void initLabels() {

        JPanel dataHolder = new JPanel();
        dataHolder.setLayout(new GridLayout(0, 2));

        JLabel cryptogramSolutionLabel = new JLabel("Solution:");
    }

    private void centerFrame(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }
}
