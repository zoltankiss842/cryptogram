package main.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Top10Panel {

    private JFrame frame;
    private JPanel panel;

    public Top10Panel(HashMap<String,String> playerStats){
        initFrame();
        initPanel();
        initLabels(sortNamesByScore(playerStats));

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void initFrame(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        frame = new JFrame(gs[0].getDefaultConfiguration());

        frame.setTitle("Top 10 Scores");
        frame.setPreferredSize(new Dimension(500,500));
        frame.setResizable(false);
        frame.requestFocus();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            frame.setIconImage(ImageIO.read(new File("resources/assets/score.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPanel(){
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10,10,10,10));
        frame.add(panel);
    }

    private void initLabels(ArrayList<String> top10players){
        JPanel statHolder = new JPanel();

        JPanel titleHolder = new JPanel();

        JLabel title = new JLabel("Scores from people that have successfully completed cryptograms");
        titleHolder.add(title);
        statHolder.add(titleHolder);

        JPanel stats = new JPanel();
        statHolder.setBorder(new EmptyBorder(10,10,10,10));
        stats.setLayout(new GridLayout(11,0,5,5));
        stats.setBorder(new EmptyBorder(10,10,10,10));

        for(int i=0;i<10&&i<top10players.size();i++){

            JPanel temp = new JPanel();

            if(i == 0){
                JLabel first = new JLabel();
                first.setIcon(new ImageIcon("resources/assets/first.png"));
                temp.add(first);
            }

            if(i == 1){
                JLabel first = new JLabel();
                first.setIcon(new ImageIcon("resources/assets/second.png"));
                temp.add(first);
            }

            if(i == 2){
                JLabel first = new JLabel();
                first.setIcon(new ImageIcon("resources/assets/third.png"));
                temp.add(first);
            }

            JLabel name = new JLabel(i+1+". "+top10players.get(i));
            temp.add(name);

            stats.add(temp);
        }

        statHolder.add(stats);

        panel.add(statHolder,BorderLayout.CENTER);
    }

    private ArrayList<String> sortNamesByScore(HashMap<String,String> playerStats){
        ArrayList<String> top10players = new ArrayList<>();
        for(Map.Entry<String,String>entry: playerStats.entrySet()){

            if(Integer.parseInt(entry.getValue())>0) {
                top10players.add(entry.getKey() + " " + entry.getValue());
            }
        }
        if(top10players.isEmpty()) {
            System.out.println("No player scores to show");
        }

        sort(top10players);

        return top10players;
    }

    private void sort(ArrayList<String> players){

        for(int i=1;i<players.size();i++){
            String key = players.get(i);

            String[] tokens = key.split(" ");
            int intKey = Integer.parseInt(tokens[1]);

            int j = i-1;
            tokens = players.get(j).split(" ");
            int arrayItems = Integer.parseInt(tokens[1]);

            while(j>=0 && intKey > arrayItems){
                players.set(j+1,players.get(j));
                j--;
                if(j>=0) {
                    tokens = players.get(j).split(" ");
                    arrayItems = Integer.parseInt(tokens[1]);
                }
            }

            players.set(j+1,key);

        }

    }
}
