package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FrequenciesPanel {

    private JScrollPane holder;
    private JPanel frequenciesHolder;

    public FrequenciesPanel(String letterFrequencies) {
        initFrequencies(letterFrequencies);
        initHolder(frequenciesHolder);
    }

    private void initHolder(JPanel frequenciesHolder) {
        holder = new JScrollPane(frequenciesHolder);
        holder.setPreferredSize(new Dimension(150, Frame.FRAME_HEIGHT));
        holder.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        holder.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        holder.getVerticalScrollBar().setUnitIncrement(16);
    }

    private void initFrequencies(String letterFrequencies) {
        if(letterFrequencies.length() <= 0){
            return;
        }
        String[] tokenised = letterFrequencies.split(",");
        int rows = tokenised.length+1;

        frequenciesHolder = new JPanel();
        frequenciesHolder.setLayout(new GridLayout(rows, 1));
        frequenciesHolder.setBackground(Frame.GUNMETAL);

        JPanel temp = new JPanel();
        temp.setBackground(Frame.GUNMETAL);

        JPanel letterHolder = new JPanel();
        String LETTERTOOLTIP = "Letter: Non-encrypted letter used in current game";
        letterHolder.setToolTipText(LETTERTOOLTIP);
        letterHolder.setBackground(Frame.GUNMETAL);
        JLabel letter = new JLabel("L");
        letter.setForeground(Color.WHITE);
        letterHolder.add(letter);

        JPanel quantityHolder = new JPanel();
        quantityHolder.setBackground(Frame.GUNMETAL);
        String QUANTITYTOOLTIP = "Quantity: Number of letters in current game";
        quantityHolder.setToolTipText(QUANTITYTOOLTIP);
        JLabel quantity = new JLabel("QTY");
        quantity.setForeground(Color.WHITE);
        quantityHolder.add(quantity);

        JPanel ratioHolder = new JPanel();
        ratioHolder.setBackground(Frame.GUNMETAL);
        String RATIOTOOLTIP = "Ratio: Ratio of usage of the letter in current game";
        ratioHolder.setToolTipText(RATIOTOOLTIP);
        JLabel ratio = new JLabel("R");
        ratio.setForeground(Color.WHITE);
        ratioHolder.add(ratio);

        temp.add(letterHolder);
        temp.add(quantityHolder);
        temp.add(ratioHolder);

        frequenciesHolder.add(temp);


        for(String string : tokenised){
            String[] secTokenise = string.split(" ");

            JPanel borderHolder = new JPanel();
            borderHolder.setBackground(Frame.GUNMETAL);

            JPanel letterPanel = new JPanel();
            letterPanel.setBackground(Frame.GUNMETAL);
            JLabel letterLabel = new JLabel(secTokenise[0]);
            letterLabel.setHorizontalAlignment(JLabel.CENTER);
            letterLabel.setForeground(Color.WHITE);
            letterPanel.add(letterLabel);

            JPanel quantityPanel = new JPanel();
            quantityPanel.setBackground(Frame.GUNMETAL);
            JLabel quantityLabel = new JLabel(secTokenise[1]);
            quantityLabel.setHorizontalAlignment(JLabel.CENTER);
            quantityLabel.setForeground(Color.WHITE);
            quantityPanel.add(quantityLabel);

            JPanel ratioPanel = new JPanel();
            ratioPanel.setBackground(Frame.GUNMETAL);
            JLabel ratioLabel = new JLabel(secTokenise[2]);
            ratioLabel.setHorizontalAlignment(JLabel.CENTER);
            ratioLabel.setForeground(Color.WHITE);
            ratioPanel.add(ratioLabel);

            borderHolder.addMouseListener(createMouseListener(borderHolder, letterPanel, quantityPanel, ratioPanel));

            borderHolder.add(letterPanel);
            borderHolder.add(quantityPanel);
            borderHolder.add(ratioPanel);

            frequenciesHolder.add(borderHolder);
        }

    }

    private MouseListener createMouseListener(JPanel holder, JPanel letter, JPanel quantity, JPanel ratio) {

        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                holder.setBackground(Frame.QUEENBLUE);
                letter.setBackground(Frame.QUEENBLUE);
                quantity.setBackground(Frame.QUEENBLUE);
                ratio.setBackground(Frame.QUEENBLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                holder.setBackground(null);
                letter.setBackground(null);
                quantity.setBackground(null);
                ratio.setBackground(null);
            }
        };
    }

    public JScrollPane getHolder() {
        return holder;
    }
}
