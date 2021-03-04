package main.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ButtonHolder {

    private JPanel holder;

    public ButtonHolder() {
        this.holder = new JPanel();
        holder.setBorder(new LineBorder(new Color(255,0,0), 5));
    }

    public JPanel getHolder() {
        return holder;
    }
}
