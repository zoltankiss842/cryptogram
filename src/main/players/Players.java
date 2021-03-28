package main.players;

import main.exceptions.MissingNameInFile;
import main.exceptions.MissingStatsInFile;

import java.io.*;
import java.util.*;

/**
 * This class represents the players that have saved statistics in this
 * program.
 */
public class Players {

    private final ArrayList<Player> allPlayers ;

    File playersFile = new File("players.txt");

    public Players() {
        allPlayers=new ArrayList<>();

        try {
            if(playersFile.createNewFile()){
                System.out.print("Successful file creation for players.txt");
            }
            else{
                System.out.print("Failed file creation for players.txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(Player p) {
        allPlayers.add(p);
    }

    public  boolean findPlayer(Player p) {
        for (Player allPlayer : allPlayers) {
            if (p.getUsername().equals(allPlayer.getUsername())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns actual player from the username
     * @param username  username
     * @return      found player or null
     */
    public Player replacePlayer(String username) {
        for (Player allPlayer : allPlayers) {
            if (username.equals(allPlayer.getUsername())) {
                return allPlayer;
            }
        }
        return null;
    }

    /**
     * This method reads in the players and their stats from players.txt
     * @return      map containing the statistics
     */
    public HashMap<String,String> readStats(){
        HashMap<String,String> hash = new HashMap<>();
        try{
            FileReader f = new FileReader(playersFile);
            Scanner scan = new Scanner(f);
            while(scan.hasNext()){
                String name = scan.nextLine();
                if(name.isBlank() || name.isEmpty() || replacePlayer(name)==null){ // Was going to write a method to check if a certain name is in the list of players but this has the same effect
                    scan.close();
                    throw new MissingNameInFile("Name is missing in file!");
                }
                String stats = scan.nextLine();
                if(stats.isBlank() || stats.isEmpty()){
                    scan.close();
                    throw new MissingStatsInFile("Stats are missing in file!");
                }
                hash.put(name,stats);
            }
        }catch(FileNotFoundException e){
            System.out.println("Something went wrong while reading player stats, no: "+playersFile+" file");
        }catch(MissingNameInFile e){
            System.out.println("Missing name in file");
        }catch(MissingStatsInFile e){
            System.out.println("Missing stats in file");
        }


        hash = new HashMap<>();

        for (Player player : allPlayers) {
            if (player.getNumCryptogramsCompleted()>0) {
                hash.put(player.getUsername(),String.valueOf(player.getNumCryptogramsCompleted()));
            }

        }
        return hash;
    }

    /**
     * Saving stats for players in players.txt
     */
    public void saveStats() {
        try {
            Player p;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < allPlayers.size(); i++) {
                p = allPlayers.get(i);

                String dummy
                        = p.getUsername() + "\n"
                        + p.getAccuracy() + " "
                        + p.getTotalGuesses() + " "
                        + p.getTotalCorrectGuesses() + " "
                        + p.getNumCryptogramsPlayed() + " "
                        + p.getNumCryptogramsCompleted() + " "
                        + p.getNumCryptogramsSuccessfullyCompleted() + "\n";

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

    /**
     * This method loads the stats in the allPlayers arraylist, from players.txt
     */
    public void loadStats() {
        Scanner mys;
        try {
            mys = new Scanner(playersFile);
            while (mys.hasNextLine()) {
                String username = mys.nextLine();
                if (username.isBlank()) {
                    return;

                }

                String[] tokens = mys.nextLine().split(" ");
                if (tokens.length!=6) {
                    return;

                }
                Player p = new Player(username);

                p.setAccuracy(Double.parseDouble(tokens[0]));
                p.setTotalGuesses(Integer.parseInt(tokens[1]));
                p.setTotalCorrectGuesses(Integer.parseInt(tokens[2]));
                p.setCryptogramsPlayed(Integer.parseInt(tokens[3]));
                p.setCryptogramsCompleted(Integer.parseInt(tokens[4]));
                p.setCryptogramsSuccessfullyCompleted(Integer.parseInt(tokens[5]));
                add(p);

            }
            mys.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}




