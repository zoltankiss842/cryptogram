package main.view;

import javax.swing.*;
import java.util.HashMap;

public class FrequenciesPanel {
    private JPanel holder;
    private JLabel frequencies;

    public FrequenciesPanel(String letterFreq) {
        initHolder();
        initFrequencies(letterFreq);
    }

    private void initFrequencies(String letterFreq) {
       frequencies = new JLabel(letterFreq);
       holder.add(frequencies);
    }

    private void initHolder() {
        holder = new JPanel();
    }

    public JPanel getHolder() {
        return holder;
    }
}
