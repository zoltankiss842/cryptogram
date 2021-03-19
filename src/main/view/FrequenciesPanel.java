package main.view;

import javax.swing.*;
import java.util.HashMap;

public class FrequenciesPanel {
    private JPanel holder;
    private JLabel frequencies;
    private JLabel letterFreq;

    public FrequenciesPanel(String letterFreq) {
        initHolder();
        initFrequencies(letterFreq);
    }

    private void initFrequencies(String letterFr) {
       frequencies = new JLabel("<html>Frequencies<html>" + letterFr);
       frequencies.setText("<html>" + letterFr.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");

       holder.add(frequencies);
    }

    private void initHolder() {
        holder = new JPanel();
    }

    public JPanel getHolder() {
        return holder;
    }
}
