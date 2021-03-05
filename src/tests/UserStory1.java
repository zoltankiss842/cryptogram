package tests;

import main.cryptogram.Cryptogram;
import main.cryptogram.LetterCryptogram;
import main.cryptogram.NumberCryptogram;
import main.game.Game;
import main.players.Player;
import org.junit.*;

/* As a player I want to be able to generate a cryptogram so I can play it */
public class UserStory1 {

    private final String PLAYERNAME = "test";

    private Player player;
    private LetterCryptogram letterCryptogram;
    private NumberCryptogram numberCryptogram;
    private Game game;

    @Before
    public void setUp(){
        player = new Player(PLAYERNAME);
        letterCryptogram = new LetterCryptogram();
        numberCryptogram = new NumberCryptogram();
        game = new Game(player);
    }

    /*
    Scenario: Player requests letters cryptogram
        - Given there are phrases stored
        - When the player requests a cryptogram
        - Then cryptogram based on a phrase where each plain letter from the phrase is mapped to a single cryptogram letter value
     */
    @Test
    public void letterCryptoTest(){

    }

    /*
    Scenario: Player requests numbers cryptogram
        - Given there are phrases stored
        - When the player requests a cryptogram
        - Then cryptogram based on a phrase where each plain letter from the phrase is mapped to a single cryptogram number from 1-26
     */
    @Test
    public void numberCryptoTest(){

    }

    /*
    Scenario: Player requests a cryptogram but no phrases file exists
        - Given there are no phrases stored
        - When the player requests a cryptogram
        - Then an error message is shown and the game exits
     */
    @Test
    public void noPhrasesTest(){

    }

    @After
    public void tearDown(){

    }

}
