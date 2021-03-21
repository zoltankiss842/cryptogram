package tests;

import main.exceptions.*;
import main.game.Game;
import main.players.Player;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class UserStory12 {

    private final String PLAYER_NAME = "test";
    private final String SOLUTION = "This is a test sentence that needs to be solved";
    private final String SOLUTION2 = "This is another test sentence that needs to be solved";

    private Player player;
    private ArrayList<String> sentences;
    private Game game;

    @Before
    public void setUp(){
        player = new Player(PLAYER_NAME);

        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game.playGame();

        game.savegame();       sentences = new ArrayList<>();
        sentences.add(SOLUTION);
        sentences.add(SOLUTION2);
    }

    /*
    Scenario – player details loaded
        - Given a player has previously played at least one cryptogram game
        - When the player identifies themselves
        - Then the player’s details are loaded
     */
    @Test
    public void playerDetailsLoaded() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation {
//        game = new Game(player, sentences, false);
//
//        InputStream sysInBackup = System.in; // backup System.in to restore it later
//        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
//        System.setIn(in);
//
//        game.playGame();
//
//        game.savegame();

    }
}
