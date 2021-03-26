package main.view;

import javax.swing.*;
import java.awt.*;

public class FrequenciesPanel {
    private JPanel holder;
    private JLabel frequencies;

    public FrequenciesPanel(String letterFrequencies) {
        initHolder();
        initFrequencies(letterFrequencies);
    }

    private void initHolder() {
        holder = new JPanel();
        holder.setLayout(new WrapLayout());
    }

    private void initFrequencies(String letterFrequencies) {
        String[] tokenised = letterFrequencies.split(",");

        for(String string : tokenised){
            JPanel tempPanel = new JPanel();
            JLabel tempLabel = new JLabel(string);

            tempPanel.add(tempLabel);

            holder.add(tempPanel);
        }

        if(tokenised.length < 11){
            holder.setPreferredSize(new Dimension(Frame.FRAME_WIDTH, 30));
        }
        else if(tokenised.length >= 11 && tokenised.length < 21){
            holder.setPreferredSize(new Dimension(Frame.FRAME_WIDTH, 60));
        }
        else if(tokenised.length >= 21 && tokenised.length < 31){
            holder.setPreferredSize(new Dimension(Frame.FRAME_WIDTH, 120));
        }
        else{
            holder.setPreferredSize(new Dimension(Frame.FRAME_WIDTH, 150));
        }

    }

    public JPanel getHolder() {
        return holder;
    }
}
