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
import java.util.List;

public class UserStory13 {

    private final String SOLUTION = "This is a test sentence that needs to be solved";
    private final String SOLUTION2 = "This is another test sentence that needs to be solved";
    private final String SOLUTION3 = "This is the last sentence that needs to be solved";

    private Player player;
    private ArrayList<String> sentences;
    private List<String> scoreboardTop10;
    private Game game;

    @Before
    public void setUp() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation {
        String PLAYER_NAME = "test";
        player = new Player(PLAYER_NAME);
        sentences = new ArrayList<>();
        scoreboardTop10 = new ArrayList<>();
        sentences.add(SOLUTION);
        sentences.add(SOLUTION2);
        sentences.add(SOLUTION3);

        game = new Game(player, sentences, false);
    }

    /* Scenario: player wants to see the top 10 players ordered by proportion of
    successfully completed cryptograms
        - Given at least one player has successfully completed a cryptogram
        - When a player selects to see the top 10 players by number of successfully completed cryptograms
        - Then the top 10 players are shown, ordered by highest proportion of successfully completed cryptogram
    */

    @Test
    public void top10Tests() throws Exception {
        game = new Game(player, sentences, false);

        Assert.assertEquals(0, player.getNumCryptogramsPlayed());

        LetterCryptogram letter = new LetterCryptogram(SOLUTION);
        Assert.assertEquals(letter.getSolution(),SOLUTION.toLowerCase());
        player.incrementCryptogramsSuccessfullyCompleted();
        player.incrementCryptogramsPlayed();
        Assert.assertEquals(1, player.getNumCryptogramsSuccessfullyCompleted());
        Assert.assertEquals(1, player.getNumCryptogramsPlayed());

        LetterCryptogram letter2 =new LetterCryptogram(SOLUTION2);
        Assert.assertNotSame(letter2.getSolution(), SOLUTION3.toLowerCase());
        player.incrementCryptogramsPlayed();
        Assert.assertEquals(1, player.getNumCryptogramsSuccessfullyCompleted());
        Assert.assertEquals(2, player.getNumCryptogramsPlayed());

        LetterCryptogram letter3 = new LetterCryptogram(SOLUTION3);
        Assert.assertNotSame(letter3.getSolution(), SOLUTION3.toLowerCase());
        player.incrementCryptogramsSuccessfullyCompleted();
        player.incrementCryptogramsPlayed();
        Assert.assertEquals(2, player.getNumCryptogramsSuccessfullyCompleted());
        Assert.assertEquals(3, player.getNumCryptogramsPlayed());

        scoreboardTop10.add(player.getUsername() + " " + player.getNumCryptogramsSuccessfullyCompleted());

        Assert.assertFalse(scoreboardTop10.isEmpty());

        Assert.assertEquals("test 2", scoreboardTop10.get(0));
    }

    /* Scenario: no player stats have been stored
        - Given no player stats have been stored
        - When the player selects to see the top 10 players
        - Then an error message is shown
     */

    @Test
    public void top10Empty() throws Exception {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game = new Game(player, sentences,false);
        game.playGame();

        LetterCryptogram letter = new LetterCryptogram(SOLUTION3);
        Assert.assertNotEquals(letter.getSolution(), SOLUTION.toLowerCase());
        player.incrementCryptogramsPlayed();

        Assert.assertTrue(scoreboardTop10.isEmpty());
    }

    @After
    public void tearDown(){

        File players = new File("players.txt");
        Assert.assertTrue(players.delete());

    }
}

