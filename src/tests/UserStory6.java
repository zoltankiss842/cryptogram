package tests;

import main.cryptogram.LetterCryptogram;
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

    private Player player;
    private ArrayList<String> sentences;

    @Before
    public void setUp(){
        String PLAYER_NAME = "test";
        player = new Player(PLAYER_NAME);
        sentences = new ArrayList<>();
        sentences.add(SOLUTION);
    }

    /* Scenario: player shows the solution
        - Given a cryptogram is being played and hasn’t been completed by the player
        - When the player selects to show the solution
        - Then the correct mapping is applied and the solution displayed to the player
     */

    @Test
    public void showSolution() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, NoGameBeingPlayed {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        Game game = new Game(player, sentences, false);
        game.playGame();

        // showSolution() gets the solution from the getSolution() method
        // so we first check is the solution is empty
        LetterCryptogram letter = new LetterCryptogram(SOLUTION);
        String solution = letter.getSolution();
        Assert.assertFalse(solution.isEmpty());

        // if solution from getSolution() is not empty
        // then the showSolution() should not be empty either
        String showSol = game.showSolution();
        Assert.assertNotNull(showSol);

       /* Character[] inputKeys = game.getInputFromUserLetter().keySet().toArray(new Character[0]);

        for(Character character : inputKeys){
            Character value = game.getInputFromUserLetter().get(character);
            Assert.assertNotNull(value);
        }
    }*/
}
}
