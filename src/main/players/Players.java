package main.players;

import main.exceptions.InvalidGameCreation;
import main.exceptions.InvalidPlayerCreation;
import main.exceptions.MissingNameInFile;
import main.exceptions.MissingStatsInFile;

import java.io.*;
import java.util.*;

public class Players {
    private  ArrayList<Player> allPlayers ;
    File playersFile = new File("players.txt");

    public Players() {
        allPlayers=new ArrayList<>();

        try {
            playersFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(Player p) {
        allPlayers.add(p);
    }

    public void savePlayer() {

    }

    public  boolean findPlayer(Player p) {
        for (int i = 0; i < allPlayers.size(); i++) {
            if (p.getUsername().equals(allPlayers.get(i).getUsername())) {
                return true;
            }
        }
        return false;
    }
    public Player replacePlayer(String username) {
        for (int i = 0; i < allPlayers.size(); i++) {
            if (username.equals(allPlayers.get(i).getUsername())) {
                return allPlayers.get(i) ;
            }
        }
        return null;
    }

    public void getAllPlayersAccuracies() {
    }

    public void getAllPlayersCryptogramsPlayed() {

    }

    public void getAllPlayersCompletedCryptos() {

    }

    public void printallnames(){ //test methods to see all the names
        for(int i=0;i<allPlayers.size();i++){
            System.out.println(allPlayers.get(i).getUsername());
        }
    }

    public HashMap<String,String> readStats(){
        HashMap<String,String> hash = new HashMap<>();
        try{
            FileReader f = new FileReader(playersFile);
            Scanner scan = new Scanner(f);
            while(scan.hasNext()){
                String name = scan.nextLine();
                if(name.isBlank() || name.isEmpty() || replacePlayer(name)==null){ //was going to write a method to check if a certain name is in the list of players but this has the same effect
                    scan.close();
                    throw new MissingNameInFile("Name is missing in file!");
                }
                String stats = scan.nextLine();
                if(stats.isBlank() || stats.isEmpty()){
                    scan.close();
                    throw new MissingStatsInFile("Stats are missing in file!");
                }
                hash.put(name,stats);
                //System.out.println("name test: "+name+"\n"+stats);
            }
        }catch(FileNotFoundException e){
            System.out.println("Something went wrong while reading player stats, no: "+playersFile+" file");
        }catch(MissingNameInFile e){
            System.out.println("Missing name in file");
        }catch(MissingStatsInFile e){
            System.out.println("Missing stats in file");
        }
        return hash;
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
        Scanner mys = null;
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
            mys.close();
        } catch (FileNotFoundException e) {
            if(mys != null){
                mys.close();
            }
            e.printStackTrace();
        }}
}




