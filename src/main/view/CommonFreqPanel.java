package main.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommonFreqPanel {
    private JFrame frame;
    private JPanel panel;


    private BufferedImage image;

    public CommonFreqPanel() {
        initFrame();
        initPanel();
        try {
            BufferedImage myPicture = ImageIO.read(new File("resources/assets/freq_new.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setToolTipText("https://en.wikipedia.org/wiki/Letter_frequency");
            frame.setPreferredSize(new Dimension((new ImageIcon(myPicture).getIconWidth()+50),(new ImageIcon(myPicture).getIconHeight()+50)));

            frame.pack();
            frame.setIconImage(ImageIO.read(new File("resources/assets/wikipedia.png")));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            panel.add(picLabel);
        } catch (Exception e) {

        }


    }

    private void initFrame() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        frame = new JFrame(gs[0].getDefaultConfiguration());

        frame.setTitle("Frequencies");

        frame.setResizable(false);

        frame.requestFocus();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        centerFrame();
    }

    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        frame.add(panel);
    }


    private void centerFrame() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }


}