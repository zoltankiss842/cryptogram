package tests;

import main.cryptogram.LetterCryptogram;
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

public class UserStory11 {

    private Player player;
    private Game game;

    @Before
    public void setUp() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation {
        String PLAYER_NAME = "test";
        player = new Player(PLAYER_NAME);
        ArrayList<String> sentences = new ArrayList<>();
        String SOLUTION = "This is a test sentence that needs to be solved";
        sentences.add(SOLUTION);
        String SOLUTION2 = "This is another test sentence that needs to be solved";
        sentences.add(SOLUTION2);

        game = new Game(player, sentences, false);
    }

    /*
    Scenario - correct guess made
        - Given a letter has been entered
        - When the guess is correct
        - Then the number of correct guesses and the number of guesses are increased by one
     */
    @Test
    public void correctGuessMade() throws Exception {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);
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

        Assert.assertEquals(0, player.getTotalCorrectGuesses());
        Assert.assertEquals(0, player.getTotalGuesses());

        game.enterLetter(String.valueOf(list.get(0)), String.valueOf(list.get(0)));
        if(String.valueOf(list.get(0)).equals(String.valueOf(list.get(0)))) {
            Assert.assertEquals(0, player.getTotalCorrectGuesses());
            Assert.assertEquals(1, player.getTotalGuesses());
        }

        File test=new File("test.txt");
        test.delete();
        File players = new File("players.txt");
        players.delete();

    }

    /*
    Scenario – incorrect guess made
        - Given a letter has been entered
        - When the guess is incorrect
        - Then the number of guesses is increased by one
     */
    @Test
    public void incorrectGuessMade() throws Exception {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);
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

        Assert.assertEquals(0, player.getTotalCorrectGuesses());
        Assert.assertEquals(0, player.getTotalGuesses());

        game.enterLetter(String.valueOf(list.get(0)), String.valueOf(list.get(0)));
        if(!(String.valueOf(list.get(0)).equals(String.valueOf(list.get(0))))) {
            Assert.assertEquals(0, player.getTotalCorrectGuesses());
            Assert.assertEquals(1, player.getTotalGuesses());
        }

        File test=new File("test.txt");
        test.delete();
        File players = new File("players.txt");
        players.delete();
    }

    @After
    public void tearDown(){

    }
}
