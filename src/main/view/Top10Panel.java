package main.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Top10Panel {

    private JFrame frame;
    private JPanel panel;

    public Top10Panel(HashMap<String,String> playerstats){
        initFrame();
        initPanel();
        initLabels(sortnamesbyscore(playerstats),playerstats);

        centerFrame();
        frame.pack();
        frame.setVisible(true);
    }

    private void initFrame(){
        frame = new JFrame("Top 10 Scores");
        frame.setPreferredSize(new Dimension(500,500));
        frame.setResizable(false);
        frame.requestFocus();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initPanel(){
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10,10,10,10));
        frame.add(panel);
    }

    private void initLabels(ArrayList<String> top10players, HashMap<String,String> playerstats){
        JPanel statholder = new JPanel();
        statholder.setLayout(new GridLayout(11,2));

        JLabel title = new JLabel("Scores from people that have successfully completed cyptograms");
        statholder.add(title);

        for(int i=0;i<10&&i<top10players.size();i++){
            JLabel name = new JLabel(top10players.get(i));
            JLabel stat = new JLabel(playerstats.get(top10players.get(i)));
            statholder.add(name);
            statholder.add(stat);
        }

        panel.add(statholder,BorderLayout.CENTER);
    }

    private void centerFrame(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2,dim.height/2-frame.getSize().height/2);
    }

    private ArrayList<String> sortnamesbyscore(HashMap<String,String> playerstats){
        ArrayList<String> top10players = new ArrayList<>();
        for(Map.Entry<String,String>entry:playerstats.entrySet()){
            String stats = entry.getValue();
            String[] tokenstats = stats.split(" ");
            top10players.add(entry.getKey()+" "+tokenstats[5]);
        }
        top10players = sort(top10players);
        return top10players;
    }

    private ArrayList<String> sort(ArrayList<String> players){
        System.out.println("\nStarting loop");
        for(int k=0;k<players.size();k++){
            System.out.println(k+" "+players.get(k));
        }
        for(int i=1;i<players.size();i++){
            String key = players.get(i);

            String[] toks = key.split(" ");
            int intkey = Integer.valueOf(toks[1]);
            System.out.println(Integer.valueOf(toks[1]));

            int j = i-1;
            toks = players.get(j).split(" ");
            int arrayitem = Integer.valueOf(toks[1]);
            System.out.println(Integer.valueOf(toks[1]));
            while(j>=0 && intkey > arrayitem){
                players.set(j+1,players.get(j));
                j--;
                if(j>=0) {
                    toks = players.get(j).split(" ");
                    arrayitem = Integer.valueOf(toks[1]);
                }
            }
        players.set(j+1,key);
            for(int k=0;k<players.size();k++){
                System.out.println(players.get(k));
            }
            System.out.println();
        }
        return players;
    }
}
