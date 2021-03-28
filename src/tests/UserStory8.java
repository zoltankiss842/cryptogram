package tests;

import main.exceptions.*;
import main.game.Game;
import main.players.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserStory8 {

    private Player player;
    private ArrayList<String> sentences;

    @Before
    public void setUp(){
        String PLAYER_NAME = "test";
        player = new Player(PLAYER_NAME);
        sentences = new ArrayList<>();
        String SOLUTION = "This is a test sentence that needs to be solved";
        sentences.add(SOLUTION);
        String SOLUTION2 = "This is another test sentence that needs to be solved";
        sentences.add(SOLUTION2);
    }

    /*
    As a player I want to store my details so I can track my game play statistics
        - Given a player has been created
        - When the player asks to exit the game
        - Then their details are saved to a file
     */
    @Test
    public void storeDetails() throws NoSentencesToGenerateFrom, InvalidGameCreation, NoSuchGameType, NoSaveGameFound, InvalidPlayerCreation, FileNotFoundException, NoGameBeingPlayed {
        Game game = new Game(player, sentences, false);

        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game.playGame();

        game.savegame();

        File file = new File("players.txt");
        File testFile = new File("test.txt");


        Assert.assertTrue(file.exists());

        FileReader reader = new FileReader(file);
        Scanner sc = new Scanner(reader);

        Assert.assertFalse(sc.nextLine().isEmpty());
        sc.close();


        file.delete();

        testFile.delete();
    }
    @After
    public void tearDown()
    {

        File test=new File("test.txt");
        test.delete();
        File players = new File("players.txt");
        players.delete();

    }

}
