package main;

import main.Cryptogram;
import main.players.Player;
import main.players.Players;

import java.util.HashMap;

public class Game {

    HashMap<Player, Cryptogram> playerGameMapping;
    Player currentPlayer;

    public Game(Player p, String cryptType){
        currentPlayer = loadPlayer(p);
        playGame();
    }

    public Game(Player p){

    }
    public static void getHint(){
        System.out.println("Here the hint");
    }

    public Player loadPlayer(Player p){
        if(Players.findPlayer(p)){

            return p;
        }
        return p;
    }

    public void playGame(){
        //Driver.main(playerGameMapping.get(currentPlayer));
    }

    public void enterLetter(){

    }

    public void undoLetter(){

    }

    public void viewFrequencies(){

    }

    public void voidsaveGame(){

    }

    public void voidloadGame(){

    }

    public void generateCryptogram(){

    }

    public void showSolution(){

    }
}
