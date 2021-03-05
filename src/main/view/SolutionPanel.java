package main.view;

import javax.swing.*;

/**
 * This class is for to display the solution on the
 * top of the frame.
 */
public class SolutionPanel {

    private JPanel holder;
    private JLabel solution;

    public SolutionPanel(String sol) {
        initHolder();
        initSolution(sol);
    }

    private void initSolution(String sol) {
        solution = new JLabel("The solution is: " + sol);
        holder.add(solution);
    }

    private void initHolder() {
        holder = new JPanel();
    }

    public JPanel getHolder() {
        return holder;
    }
}
