package main.players;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class Players {
    private static ArrayList<Player> allPlayers = new ArrayList<>(20);
    File playersFile = new File("players.txt");


    public void add(Player p) {
        allPlayers.add(p);
    }

    public void savePlayer() {

    }

    public static boolean findPlayer(Player p) {
        for (int i = 0; i < allPlayers.size(); i++) {
            if (p.getUsername() == allPlayers.get(i).getUsername()) {
                return true;
            }
        }
        return false;
    }

    public void getAllPlayersAccuracies() {
    }

    public void getAllPlayersCryptogramsPlayed() {

    }

    public void getAllPlayersCompletedCryptos() {

    }

    public void saveStats() {
        try {
            Player p=null;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < allPlayers.size(); i++) {
                 p=allPlayers.get(i);

                String dummy=p.getUsername() + "\n" + p.getAccuracy() + " " + p.getTotalGuesses() + " " +
                        p.getTotalCorrectGuesses() + " " + p.getNumCryptogramsPlayed() + " " + p.getNumCryptogramsCompleted()
                        + " " + p.getNumCryptogramsSuccessfullyCompleted()+"\n";
               sb.append(dummy);
            }
            FileWriter playersWrite = new FileWriter(playersFile);
            playersWrite.write(sb.toString());
            playersWrite.close();
        } catch (IOException e) {
            System.out.println("File could not be saved");
            e.printStackTrace();
        }
    }

    public void loadStats() {
        Scanner mys;
        try {
            mys = new Scanner(playersFile);
            while(mys.hasNextLine()){
                String username = mys.nextLine();
                String[] tokens = mys.nextLine().split(" ");
                Player p = new Player(username);

                    p.setAccuracy(Double.parseDouble(tokens[0]));
                    p.setTotalGuesses(Integer.parseInt(tokens[1]));
                    p.setTotalCorrectGuesses(Integer.parseInt(tokens[2]));
                    p.setCryptogramsPlayed(Integer.parseInt(tokens[3]));
                    p.setCryptogramsCompleted(Integer.parseInt(tokens[4]));
                    p.setCryptogramsSuccessfullyCompleted(Integer.parseInt(tokens[5]));
                    add(p);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }}
}




