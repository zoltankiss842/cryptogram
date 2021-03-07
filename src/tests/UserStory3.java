package tests;

import main.cryptogram.LetterCryptogram;
import main.cryptogram.NumberCryptogram;
import main.game.Game;
import main.players.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/* As a player I want to be able to undo a letter so I can play the cryptogram */
public class UserStory3 {

    private final String SOLUTION = "This is a test sentence that needs to be solved";
    private final String PLAYER_NAME = "test";

    private Game game;
    private Player player;
    private ArrayList<String> sentences;

    @Before
    public void setUp(){
        player = new Player(PLAYER_NAME);
        sentences = new ArrayList<>();
        sentences.add(SOLUTION);
    }

    /*
    Scenario: player wants to undo a mapped letter
        - Given a cryptogram has been generated and is being played
        - When a player selects a letter to remove from their mapping
        - Then the letter is removed from the player mapping
     */
    @Test
    public void undoMappedLetter() throws Exception {
        game = new Game(player, LetterCryptogram.TYPE, sentences, false);

        LetterCryptogram letter = (LetterCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual letters from the encrypted phrase
        HashSet<Character> set = new HashSet<>();
        for(char c : letter.getPhrase().toCharArray()){
            if(c != ' ' && c != '!') {
                set.add(c);
            }
        }

        ArrayList<Character> list = new ArrayList<>();

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            list.add((Character) iterator.next());
        }

        game.enterLetter(String.valueOf(list.get(0)), String.valueOf(list.get(0)));
        game.undoLetter(String.valueOf(list.get(0)));

        // Checking if the total guesses updated
        Assert.assertTrue(player.getTotalGuesses() == 1);
    }

    /*
    Scenario: player wants to undo a mapped letter
        - Given a cryptogram has been generated and is being played
        - When a player selects a letter to remove from their mapping
        - Then the letter is removed from the player mapping
     */
    @Test
    public void undoMappedNumber() throws Exception {
        game = new Game(player, NumberCryptogram.TYPE, sentences, false);

        NumberCryptogram number = (NumberCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual numbers from the encrypted phrase
        HashSet<Integer> set = new HashSet<>();
        for(Object c : number.getSolutionInIntegerFormat().toArray()){
            if((Integer) c != 0) {
                set.add((Integer)c);
            }
        }

        ArrayList<Integer> list = new ArrayList<>();

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            list.add((Integer) iterator.next());
        }

        game.enterLetter(String.valueOf(list.get(0)), "k");
        game.undoLetter(String.valueOf(list.get(0)));

        // Checking if the total guesses updated
        Assert.assertTrue(player.getTotalGuesses() == 1);
    }

    /*
    Scenario: player selects a letter in the cryptogram which they have not mapped
        - Given a cryptogram has been generated and is being played
        - When a player identifies a letter which has not been mapped to remove the mapping
        - Then an error message is displayed to the player indicating the letter has not been mapped
    */
    @Test(expected = Exception.class)
    public void undoNotMappedLetter() throws Exception {
        game = new Game(player, LetterCryptogram.TYPE, sentences, false);

        LetterCryptogram letter = (LetterCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual letters from the encrypted phrase
        HashSet<Character> set = new HashSet<>();
        for(char c : letter.getPhrase().toCharArray()){
            if(c != ' ' && c != '!') {
                set.add(c);
            }
        }

        ArrayList<Character> list = new ArrayList<>();

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            list.add((Character) iterator.next());
        }

        game.enterLetter(String.valueOf(0), "b");
        game.undoLetter("x");

        // Checking if the total guesses updated
        Assert.assertTrue(player.getTotalGuesses() == 1);
    }

    /*
    Scenario: player selects a letter in the cryptogram which they have not mapped
        - Given a cryptogram has been generated and is being played
        - When a player identifies a letter which has not been mapped to remove the mapping
        - Then an error message is displayed to the player indicating the letter has not been mapped
    */
    @Test(expected = Exception.class)
    public void undoNotMappedNumber() throws Exception {
        game = new Game(player, NumberCryptogram.TYPE, sentences, false);

        NumberCryptogram number = (NumberCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual numbers from the encrypted phrase
        HashSet<Integer> set = new HashSet<>();
        for(Object c : number.getSolutionInIntegerFormat().toArray()){
            set.add((Integer) c);
        }

        ArrayList<Integer> list = new ArrayList<>();

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            list.add((Integer) iterator.next());
        }

        int i;
        for(i = 1; i < 27; i++){
            if(!list.contains(i)){
                break;
            }
        }

        game.enterLetter(String.valueOf(list.get(0)), "k");
        game.undoLetter(String.valueOf(i));
    }

    @After
    public void tearDown(){

    }
}
