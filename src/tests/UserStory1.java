package tests;

import main.cryptogram.Cryptogram;
import main.cryptogram.LetterCryptogram;
import main.cryptogram.NumberCryptogram;
import main.game.Game;
import main.players.Player;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.*;

/* As a player I want to be able to generate a cryptogram so I can play it */
public class UserStory1 {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final String PLAYERNAME = "test";
    private final String SOLUTION = "This is a test sentence that needs to be solved";

    private Player player;
    private ArrayList<String> sentences;
    private Game game;

    @Before
    public void setUp(){
        player = new Player(PLAYERNAME);
        sentences = new ArrayList<>();
        sentences.add(SOLUTION);
    }

    /*
    Scenario: Player requests letters cryptogram
        - Given there are phrases stored
        - When the player requests a cryptogram
        - Then cryptogram based on a phrase where each plain letter from the phrase is mapped to a single cryptogram letter value
     */
    @Test
    public void letterCryptoTest() throws Exception {
        LetterCryptogram letter =new LetterCryptogram(SOLUTION);
        // Checking if the solution is the sentence that was given is the solution to the cryptogram
        Assert.assertEquals(letter.getSolution(),SOLUTION.toLowerCase());
        //Checking if the solution's letters have been mapped to letters of the alphabet and that they are unique


        HashSet<Character> set = new HashSet<>();
        HashMap<Character, Character> cryptoMapping=letter.getLetterCryptogramAlphabet();

        for (int i = 0; i < SOLUTION.length(); i++) {
            if (SOLUTION.toLowerCase().charAt(i) != ' '){
                Assert.assertNotNull(cryptoMapping.get(SOLUTION.toLowerCase().charAt(i)));

            }
        }
        //checking if mapping is unique
        for(Map.Entry<Character, Character> entry : cryptoMapping.entrySet()){
            Assert.assertTrue(set.add(cryptoMapping.get(entry.getKey())));
        }
    }

    /*
    Scenario: Player requests numbers cryptogram
        - Given there are phrases stored
        - When the player requests a cryptogram
        - Then cryptogram based on a phrase where each plain letter from the phrase is mapped to a single cryptogram number from 1-26
     */
    @Test
    public void numberCryptoTest() throws Exception {
        game = new Game(player, NumberCryptogram.TYPE, sentences,false);

        // Checking if the player-crypto mapping, stores the encrypted solution
        Assert.assertNotEquals(game.getPlayerGameMapping().get(player).getPhrase(), SOLUTION.toLowerCase());

        NumberCryptogram number = (NumberCryptogram) game.getPlayerGameMapping().get(player);

        // Checking that the NumberCryptogram phrase is the encrypted sentence
        Assert.assertNotEquals(number.getPhrase(), SOLUTION.toLowerCase());

        // Checking that the NumberCryptogram solution is the solution
        Assert.assertEquals(number.getSolution(), SOLUTION.toLowerCase());

        // Checking for each encrypted letter that is a number and there is only one instance of it
        HashSet<Character> set = new HashSet<>();
        for(int i = 1; i < 27; ++i){
            char plainLetter = number.getPlainLetter(i);
            Assert.assertTrue(plainLetter >= 'a' && plainLetter <= 'z');
            Assert.assertTrue(set.add(Character.valueOf(plainLetter)));
        }
    }

    /*
    Scenario: Player requests a cryptogram but no phrases file exists
        - Given there are no phrases stored
        - When the player requests a cryptogram
        - Then an error message is shown and the game exits
     */
    @Test(expected = Exception.class)
    public void noPhrasesTest() throws Exception{
        game = new Game(player, NumberCryptogram.TYPE, null, false);
    }

    @After
    public void tearDown(){

    }

}
