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

public class UserStory14 {

    private final String SOLUTION = "This is a test sentence that needs to be solved";

    private Player player;
    private ArrayList<String> sentences;
    private Game game;

    @Before
    public void setUp() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation {
        String PLAYER_NAME = "test";
        player = new Player(PLAYER_NAME);
        sentences = new ArrayList<>();
        sentences.add(SOLUTION);

        game = new Game(player, sentences, false);


    }

    /* Scenario
        - Given a cryptogram is being played and there are still cryptogram values to be mapped
        - When a player asks for a hint
        - Then the letter for which the corresponding cryptogram value which has not been mapped is added to the mapping and displayed to the user
    */

    @Test
    public void hintsForNullValues() throws Exception {
        game = new Game(player, sentences, false);

        Assert.assertEquals(0, player.getNumCryptogramsPlayed());

        new LetterCryptogram(SOLUTION);



    }

    /* Scenario: the letter identified has already been mapped by the user
        - Given a cryptogram is being played, there are still cryptogram values to be mapped and the player has asked for a hint
        - When the hint identifies a letter which the user has already used
        - Then the player mapping is removed and the correct mapping is entered and a message displayed to the player
     */

    @Test
    public void hintsForWrongMapping() throws Exception {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game = new Game(player, sentences,false);
        game.playGame();


    }

    @After
    public void tearDown(){

        File players = new File("players.txt");
       players.delete();

    }
}

