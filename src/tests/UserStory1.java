package tests;

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

/* As a player I want to be able to generate a cryptogram so I can play it */
public class UserStory1 {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final String SOLUTION = "This is a test sentence that needs to be solved";

    private Player player;
    private ArrayList<String> sentences;
    private Game game;

    @Before
    public void setUp(){
        String PLAYERNAME = "test";
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
    public void letterCryptoTest() {
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
        game = new Game(player, sentences,false);



        HashSet<Character> set = new HashSet<>();
        NumberCryptogram number = new NumberCryptogram(SOLUTION);
        Assert.assertEquals(number.getSolution(),SOLUTION.toLowerCase());
        //Checking if the solution's letters have been mapped to letters of the alphabet and that they are unique
        HashMap<Integer, Character> cryptoMapping=number.getNumberCryptogramAlphabet();
        for(Map.Entry<Integer, Character> entry : cryptoMapping.entrySet()){
            Assert.assertTrue(set.add(cryptoMapping.get(entry.getKey())));
        }

        // Checking for each encrypted letter that is a number and there is only one instance of it


    }

    /*
    Scenario: Player requests a cryptogram but no phrases file exists
        - Given there are no phrases stored
        - When the player requests a cryptogram
        - Then an error message is shown and the game exits
     */
    @Test(expected = Exception.class)
    public void noPhrasesTest() throws Exception{
        game = new Game(player, null, false);
    }

    @After
    public void tearDown(){

    }

}
