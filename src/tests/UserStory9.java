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
public class UserStory9 {

    private final String PLAYER_NAME = "test";
    private final String SOLUTION = "This is a test sentence that needs to be solved";
    private final String SOLUTION2 = "This is another test sentence that needs to be solved";

    private Player player;
    private ArrayList<String> sentences;
    private Game game;

    @Before
    public void setUp(){
        player = new Player(PLAYER_NAME);
        sentences = new ArrayList<>();
        sentences.add(SOLUTION);
        sentences.add(SOLUTION2);
    }

    /*
    Scenario – cryptogram successfully completed
    - Given a cryptogram is being played
    - When the player enters the last letter which results in the complete correct mapping
    - Then the number of cryptograms successfully completed is incremented and a success message is presented to the player
     */
    @Test
    public void successfullyCompletedCrypto() throws Exception {
        game = new Game(player, sentences, false);

        LetterCryptogram letter = new LetterCryptogram(SOLUTION);
        // Checking if the solution is the sentence that was given is the solution to the cryptogram
        Assert.assertEquals(letter.getSolution(),SOLUTION.toLowerCase());
        player.incrementCryptogramsSuccessfullyCompleted();
        Assert.assertTrue(player.getNumCryptogramsSuccessfullyCompleted() == 1);

        LetterCryptogram letter2 =new LetterCryptogram(SOLUTION2);
        // Checking if the solution is the sentence that was given is the solution to the cryptogram
        Assert.assertEquals(letter2.getSolution(),SOLUTION2.toLowerCase());
        player.incrementCryptogramsSuccessfullyCompleted();
        Assert.assertTrue(player.getNumCryptogramsSuccessfullyCompleted() == 2);
    }

    /*
    Scenario – cryptogram unsuccessfully completed
        - Given a cryptogram is being played
        - When the player enters the last letter which results in an incorrect mapping
        - Then a message is shown to the player indicating they are incorrect, and the game play is resumed
     */

    @Test
    public void unsuccessfullyCompletedCrypto() throws Exception {
        game = new Game(player, sentences, false);

        LetterCryptogram letter = new LetterCryptogram(SOLUTION);
        // Checking if the solution is incorrect
        Assert.assertTrue(letter.getSolution() != SOLUTION2.toLowerCase());
        Assert.assertTrue(player.getNumCryptogramsSuccessfullyCompleted() == 0);

    }

    @After
    public void tearDown(){

    }
}
