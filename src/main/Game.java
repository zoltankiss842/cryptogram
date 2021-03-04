package main;

import main.players.Player;
import main.players.Players;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Random;

import static java.lang.constant.ConstantDescs.NULL;

public class Game {

    HashMap<Player, Cryptogram> playerGameMapping;
    Player currentPlayer;
    ArrayList<String> entered;
    String currentPhrase;

    public Game(Player p, String cryptType){
        currentPlayer = loadPlayer(p);
        playGame();
        entered = new ArrayList<>();
        currentPhrase = "";
    }

    public Game(Player p){

    }
    public char getHint(){
        if(currentPhrase.equals("")){
            System.out.println("Empty phrase");
        }else{
            char[] myArray = currentPhrase.toCharArray();
            ArrayList<Character> phrasechars = new ArrayList<>();
            Set<Character> set = new HashSet();
            for(int i=0;i<myArray.length;i++){
                if(!set.add(myArray[i])){
                    phrasechars.add(myArray[i]);
                }
            }

            Random rand = new Random();
            System.out.println("Here the hint");
            return(phrasechars.get(rand.nextInt(set.size())));
        }
        return null;
    }

    public Player loadPlayer(Player p){
        if(Players.findPlayer(p)){
            loadGame(p.getUsername());
            return p;
        }else{
            loadGame(p.getUsername());
        }
        return p;
    }

    public void playGame(){
        //Driver.main(playerGameMapping.get(currentPlayer));
    }

    public boolean enterLetter(String l){
        if(entered.contains(l)){
            return false;
        }
        entered.add(l);
        return true;
    }

    public void undoLetter(){

    }

    public boolean viewFrequencies(){
        File f = new File("saves.txt");
        Scanner mys;
        try {
            mys = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        while(mys.hasNextLine()){
            String data = mys.nextLine();
            String[] tokens = data.split(" ");
            System.out.println(tokens[3]);
        }
        return true;
    }

    public void saveGame(String name){
        try{
            File f = new File("saves.txt");
            if(f.createNewFile()){
                System.out.println("File created: "+f.getName());
            }else{
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
        try{
            FileWriter myw = new FileWriter("saves.txt");
            myw.write(name+" "+"game");
            myw.close();
            System.out.println("Successfully saved");
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean loadGame(String name){
        File f = new File("saves.txt");
        Scanner mys;
        try {
            mys = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        while(mys.hasNextLine()){
            String data = mys.nextLine();
            String[] tokens = data.split(" ");
            if(name.equals(tokens[0])){
                return true;
            }
        }
        return false;
    }

    public boolean generateCryptogram(){
        File f = new File("phrases.txt");
        Scanner mys;
        try{
            mys = new Scanner(f);
        }catch(FileNotFoundException e){
            e.printStackTrace();
            return false;
        }
        ArrayList<String> phrases = new ArrayList<>();
        Random rand = new Random();
        while(mys.hasNextLine()){
            phrases.add(mys.nextLine());
        }
        String chosenphrase = ArrayList.get(rand.nextInt(ArrayList.size()));
    }

    public void showSolution(){
        if(currentPlayer!=NULL){
            /*Cryptogram c = playerGameMapping.get(currentPlayer);
            show(c.phrase);*/
        }
    }
}
