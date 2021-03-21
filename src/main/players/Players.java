package main.players;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class Players {
    private static ArrayList<Player> allPlayers = new ArrayList<>(20);
    File playersFile = new File("players.txt");
    Player p;

    public void add(Player p) {
        allPlayers.add(p);
    }

    public void savePlayer() {

    }

    public static boolean findPlayer(Player p) {
        for(int i = 0; i<allPlayers.size(); i++) {
            if(p.getUsername() == allPlayers.get(i).getUsername()) {
                return true;
            }
        }return false;
    }

    public void getAllPlayersAccuracies() {
    }

    public void getAllPlayersCryptogramsPlayed() {

    }

    public void getAllPlayersCompletedCryptos() {

    }

    public void saveStats(){
        try{
            FileWriter playersWrite= new FileWriter(playersFile);
            playersWrite.write(p.getUsername() + "\n" + p.getAccuracy() + " " + p.getTotalGuesses() + " " +
                    p.getTotalCorrectGuesses() + " " + p.getNumCryptogramsPlayed() + " " + p.getNumCryptogramsCompleted()
                    + " " + p.getNumCryptogramsSuccessfullyCompleted());
            playersWrite.close();
        }catch(IOException e){
            System.out.println("File could not be saved");
            e.printStackTrace();
        }
    }




}
