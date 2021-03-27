package tests;

import main.exceptions.*;
import main.game.Game;
import main.players.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class UserStory6 {

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

    @Test
    public void showSolution() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, NoGameBeingPlayed {
        game = new Game(player, sentences,false);

        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game.playGame();

        String solution = game.showSolution();

        Assert.assertFalse(solution.isEmpty());

        Character[] inputKeys = game.getInputFromUserLetter().keySet().toArray(new Character[0]);

        for(Character character : inputKeys){
            Character value = game.getInputFromUserLetter().get(character);
            Assert.assertTrue(value != null);
        }
    }
}
