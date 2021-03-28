package tests;

import main.exceptions.*;
import main.game.Game;
import main.players.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class UserStory4 {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private Player player;
    private ArrayList<String> sentences;
    private Game game;

    @Before
    public void setUp(){
        String PLAYERNAME = "test";
        player = new Player(PLAYERNAME);
        sentences = new ArrayList<>();

    }
    @Test
    public void testSaveLetter() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, NoGameBeingPlayed {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("N".getBytes());
        System.setIn(in);

        game = new Game(player, sentences,false);
        game.playGame();
        game.savegame();


        File test=new File("test.txt");
        Assert.assertTrue(test.exists());

        test.delete();
        File players = new File("players.txt");
        players.delete();

    }


    @Test
    public void testSaveNumber() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, NoGameBeingPlayed {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game = new Game(player, sentences,false);
        game.playGame();
        game.savegame();


        File test=new File("test.txt");
        Assert.assertTrue(test.exists());

        test.delete();
        File players = new File("players.txt");
        players.delete();
    }
}
