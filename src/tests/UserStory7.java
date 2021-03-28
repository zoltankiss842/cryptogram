package tests;

import main.cryptogram.Cryptogram;
import main.cryptogram.LetterCryptogram;
import main.exceptions.*;
import main.game.Game;
import main.players.Player;
import main.view.CommonFreqPanel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserStory7 {

    private final String PLAYER_NAME = "test";
    private final String SOLUTION = "This is a test sentence that needs to be solved";

    private Player player;
    private ArrayList<String> sentences;
    private Game game;

    @Before
    public void setUp(){
        player = new Player(PLAYER_NAME);
        sentences = new ArrayList<>();
        sentences.add(SOLUTION);
    }

    /* Scenario player views the frequencies of letters in the cryptogram
        - Given a cryptogram is being played
        - When the player asks to view the frequencies
        - Then the proportion of letter frequencies in the cryptogram is shown as well as the common proportions of letter frequencies in the English language
        */
    @Test
    public void frequenciesTest() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, FileNotFoundException, NoGameBeingPlayed {
        game = new Game(player, sentences, false);

        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game.playGame();
        LetterCryptogram letter = new LetterCryptogram(SOLUTION);

        // test if common english letter frequencies panel exists
        Assert.assertFalse(game.viewFrequencies().isEmpty());

        // test if frequencies are calculated for one letter
        String sentence = letter.getSolution();
        char lookingForChar = 't';
        int countFreq = 0;

        for (int i = 0; i < sentence.length(); i++) {
            if (sentence.charAt(i) == lookingForChar) {
                countFreq++;
            }
        }
        Assert.assertEquals(7, countFreq);
    }

    // here we compare the viewFrequencies method and the calculated frequency

}
