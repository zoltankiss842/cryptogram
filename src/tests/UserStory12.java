package tests;

import main.exceptions.*;
import main.game.Game;
import main.players.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        sentences = new ArrayList<>();
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
    public void playerDetailsLoaded() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, NoGameBeingPlayed, PlainLetterAlreadyInUse, NoSuchCryptogramLetter {
        game = new Game(player, sentences, false);

        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes()); // LetterCrypto
        System.setIn(in);

        game.playGame();

        Character key = 'a';
        for(Map.Entry<Character, Character> entry : game.getInputFromUserLetter().entrySet()){
            key = entry.getKey();
            break;
        }

        Character value = (Character) game.getPlayerGameMapping().get(game.getCurrentPlayer()).getCryptogramAlphabet().get(key);

        sysInBackup = System.in; // backup System.in to restore it later
        in = new ByteArrayInputStream("Y".getBytes()); // LetterCrypto
        System.setIn(in);
        game.enterLetter(String.valueOf(key), String.valueOf(value));

        game.savegame();


        Assert.assertTrue(game.loadGame(player.getUsername()));

        File test =new File("test.txt");
        Assert.assertTrue(test.delete());
        File players = new File("players.txt");
        Assert.assertTrue(players.delete());
    }

    /*
    Scenario- error loading player details
        - Given a player has previously played at least one cryptogram game
        - When a player identifies themselves and there is a problem with their detail file
        - Then an error message is shown
     */
    @Test()
    public void errorLoadingPlayerDetails() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, NoGameBeingPlayed, PlainLetterAlreadyInUse, NoSuchCryptogramLetter {
        game = new Game(player, sentences, false);

        try
        {
            FileWriter playersWrite = new FileWriter("players.txt");
            playersWrite.write("test\n");
            playersWrite.write("very coprrupt file\n");
            playersWrite.write("very coprrupt file\n");
            playersWrite.write("very coprrupt file\n");
            playersWrite.write("very coprrupt file\n");
            playersWrite.close();
        }
        catch(Exception e)
        {

        }

        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes()); // LetterCrypto
        System.setIn(in);

        game.playGame();

        Character key = 'a';
        for(Map.Entry<Character, Character> entry : game.getInputFromUserLetter().entrySet()){
            key = entry.getKey();
            break;
        }

        Character value = (Character) game.getPlayerGameMapping().get(game.getCurrentPlayer()).getCryptogramAlphabet().get(key);

        sysInBackup = System.in; // backup System.in to restore it later
        in = new ByteArrayInputStream("Y".getBytes()); // LetterCrypto
        System.setIn(in);
        game.enterLetter(String.valueOf(key), String.valueOf(value));

        game.savegame();


        File test =new File("test.txt");
        Assert.assertTrue(test.delete());
        File players = new File("players.txt");
        Assert.assertTrue(players.delete());
    }

    /*
    Scenario- error loading player details
        - Given a player has previously played at least one cryptogram game
        - When a player identifies themselves and there is a problem with their detail file
        - Then an error message is shown
     */
    @Test(expected = NoSaveGameFound.class)
    public void notExistingPlayerDetails() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, NoGameBeingPlayed, PlainLetterAlreadyInUse, NoSuchCryptogramLetter {
        game = new Game(player, sentences, false);

        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes()); // LetterCrypto
        System.setIn(in);

        game.playGame();

        Character key = 'a';
        for(Map.Entry<Character, Character> entry : game.getInputFromUserLetter().entrySet()){
            key = entry.getKey();
            break;
        }

        Character value = (Character) game.getPlayerGameMapping().get(game.getCurrentPlayer()).getCryptogramAlphabet().get(key);

        sysInBackup = System.in; // backup System.in to restore it later
        in = new ByteArrayInputStream("Y".getBytes()); // LetterCrypto
        System.setIn(in);
        game.enterLetter(String.valueOf(key), String.valueOf(value));

        game.savegame();

        File test =new File("test.txt");
        Assert.assertTrue(test.delete());
        File players = new File("players.txt");
        Assert.assertTrue(players.delete());

        game.loadGame("Bob");
    }
}
