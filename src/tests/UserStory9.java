package tests;

import main.cryptogram.LetterCryptogram;
import main.exceptions.*;
import main.players.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class UserStory9 {

    private final String SOLUTION = "This is a test sentence that needs to be solved";
    private final String SOLUTION2 = "This is another test sentence that needs to be solved";

    private Player player;

    @Before
    public void setUp() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation {
        String PLAYER_NAME = "test";
        player = new Player(PLAYER_NAME);
        ArrayList<String> sentences = new ArrayList<>();
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
    public void successfullyCompletedCrypto() {

        LetterCryptogram letter = new LetterCryptogram(SOLUTION);
        // Checking if the solution is the sentence that was given is the solution to the cryptogram
        Assert.assertEquals(letter.getSolution(),SOLUTION.toLowerCase());
        player.incrementCryptogramsSuccessfullyCompleted();
        Assert.assertEquals(1, player.getNumCryptogramsSuccessfullyCompleted());

        LetterCryptogram letter2 =new LetterCryptogram(SOLUTION2);
        // Checking if the solution is the sentence that was given is the solution to the cryptogram
        Assert.assertEquals(letter2.getSolution(),SOLUTION2.toLowerCase());
        player.incrementCryptogramsSuccessfullyCompleted();
        Assert.assertEquals(2, player.getNumCryptogramsSuccessfullyCompleted());


        File test=new File("test.txt");
        test.delete();
        File players = new File("players.txt");
        players.delete();
    }

    /*
    Scenario – cryptogram unsuccessfully completed
        - Given a cryptogram is being played
        - When the player enters the last letter which results in an incorrect mapping
        - Then a message is shown to the player indicating they are incorrect, and the game play is resumed
     */

    @Test
    public void unsuccessfullyCompletedCrypto() {

        LetterCryptogram letter = new LetterCryptogram(SOLUTION);
        // Checking if the solution is incorrect
        Assert.assertNotSame(letter.getSolution(), SOLUTION2.toLowerCase());
        Assert.assertEquals(0, player.getNumCryptogramsSuccessfullyCompleted());

        File test=new File("test.txt");
        test.delete();
        File players = new File("players.txt");
        players.delete();
    }

    @After
    public void tearDown(){

    }
}
