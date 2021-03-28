package tests;

import main.cryptogram.LetterCryptogram;
import main.cryptogram.NumberCryptogram;
import main.exceptions.*;
import main.game.Game;
import main.players.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/* As a player I want to be able to undo a letter so I can play the cryptogram */
public class UserStory3 {

    private Game game;
    private Player player;
    private ArrayList<String> sentences;

    @Before
    public void setUp() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation {
        String PLAYER_NAME = "test";
        player = new Player(PLAYER_NAME);
        sentences = new ArrayList<>();
        String SOLUTION = "This is a test sentence that needs to be solved";
        sentences.add(SOLUTION);

        game = new Game(player, sentences, false);
    }

    /*
    Scenario: player wants to undo a mapped letter
        - Given a cryptogram has been generated and is being played
        - When a player selects a letter to remove from their mapping
        - Then the letter is removed from the player mapping
     */
    @Test
    public void undoMappedLetter() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, NoGameBeingPlayed, PlainLetterAlreadyInUse, NoSuchCryptogramLetter {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);
        game = new Game(player, sentences, false);
        game.playGame();

        LetterCryptogram letter = (LetterCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual letters from the encrypted phrase
        HashSet<Character> set = new HashSet<>();
        for(char c : letter.getPhrase().toCharArray()){
            if(c != ' ' && c != '!') {
                set.add(c);
            }
        }

        ArrayList<Character> list = new ArrayList<>(set);

        game.enterLetter(String.valueOf(list.get(0)), String.valueOf(list.get(0)));
        game.undoLetter(String.valueOf(list.get(0)));

        // Checking if the total guesses updated
        Assert.assertEquals(1, player.getTotalGuesses());
    }

    /*
    Scenario: player wants to undo a mapped letter
        - Given a cryptogram has been generated and is being played
        - When a player selects a letter to remove from their mapping
        - Then the letter is removed from the player mapping
     */
    @Test
    public void undoMappedNumber() throws Exception {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("N".getBytes());
        System.setIn(in);
        game = new Game(player, sentences, false);

        game.playGame();

        NumberCryptogram number = (NumberCryptogram) game.getPlayerGameMapping().get(player);


        // Collecting individual numbers from the encrypted phrase
        HashSet<Integer> set = new HashSet<>();
        for(Object c : number.getSolutionInIntegerFormat().toArray()){
            if((Integer) c != 0) {
                set.add((Integer)c);
            }
        }

        ArrayList<Integer> list = new ArrayList<>(set);

        game.enterLetter(String.valueOf(list.get(0)), "k");
        game.undoLetter(String.valueOf(list.get(0)));

        // Checking if the total guesses updated
        Assert.assertEquals(1, player.getTotalGuesses());
    }

    /*
    Scenario: player selects a letter in the cryptogram which they have not mapped
        - Given a cryptogram has been generated and is being played
        - When a player identifies a letter which has not been mapped to remove the mapping
        - Then an error message is displayed to the player indicating the letter has not been mapped
    */
    @Test(expected = Exception.class)
    public void undoNotMappedLetter() throws Exception {
        game = new Game(player, sentences, false);

        LetterCryptogram letter = (LetterCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual letters from the encrypted phrase
        HashSet<Character> set = new HashSet<>();
        for(char c : letter.getPhrase().toCharArray()){
            if(c != ' ' && c != '!') {
                set.add(c);
            }
        }

        game.enterLetter(String.valueOf(0), "b");
        game.undoLetter("x");

        // Checking if the total guesses updated
        Assert.assertEquals(1, player.getTotalGuesses());
    }

    /*
    Scenario: player selects a letter in the cryptogram which they have not mapped
        - Given a cryptogram has been generated and is being played
        - When a player identifies a letter which has not been mapped to remove the mapping
        - Then an error message is displayed to the player indicating the letter has not been mapped
    */
    @Test(expected = Exception.class)
    public void undoNotMappedNumber() throws Exception {
        game = new Game(player, sentences, false);

        NumberCryptogram number = (NumberCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual numbers from the encrypted phrase
        HashSet<Integer> set = new HashSet<>();
        for(Object c : number.getSolutionInIntegerFormat().toArray()){
            set.add((Integer) c);
        }

        ArrayList<Integer> list = new ArrayList<>(set);

        int i;
        for(i = 1; i < 27; i++){
            if(!list.contains(i)){
                break;
            }
        }

        game.enterLetter(String.valueOf(list.get(0)), "k");
        game.undoLetter(String.valueOf(i));

        File test=new File("test.txt");
        test.delete();
        File players = new File("players.txt");
        players.delete();
    }

    @After
    public void tearDown(){

    }
}
