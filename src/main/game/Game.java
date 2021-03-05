package main.game;

import main.cryptogram.Cryptogram;
import main.players.Player;
import main.players.Players;
import main.view.Frame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Random;


public class Game {

    HashMap<Player, Cryptogram> playerGameMapping;
    Player currentPlayer;
    ArrayList<String> entered;
    String currentPhrase;

    private Frame gameGui;

    public Game(String userName) {
        currentPlayer = new Player(userName);
        entered = new ArrayList<>();
        currentPhrase = "";
        gameGui = new Frame(currentPlayer.getUsername(), this);
        playGame();
    }

    public Game(Player p, String cryptType){
        currentPlayer = loadPlayer(p);
        playGame();
        entered = new ArrayList<>();
        currentPhrase = "";
        gameGui = new Frame(currentPlayer.getUsername(), this);
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

        return 'c';
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
        //gameGui.displayNewGame(playerGameMapping.get(currentPlayer));

        ArrayList<String> tempSentences = new ArrayList<>();
        tempSentences.add("This is a very long sentence that will be displayed so we will see what is going to happen");
        tempSentences.add("He was so preoccupied with whether or not he could that he failed to stop to consider if he should");
        tempSentences.add("Pair your designer cowboy hat with scuba gear for a memorable occasion");
        tempSentences.add("For oil spots on the floor, nothing beats parking a motorbike in the lounge");
        tempSentences.add("He said he was not there yesterday however many people saw him there");

        Random rnd = new Random();

        String toDisplay = tempSentences.get(rnd.nextInt(5));

        Cryptogram c = new Cryptogram();
        c.setPhrase(toDisplay);
        c.setSolution(toDisplay);

        gameGui.displayNewGame(c);
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

        //String chosenphrase = ArrayList.get(rand.nextInt(ArrayList.size()));

        return false;
    }

    public void showSolution(){
        if(currentPlayer!=null){
            /*Cryptogram c = playerGameMapping.get(currentPlayer);
            show(c.phrase);*/
        }
    }
}
