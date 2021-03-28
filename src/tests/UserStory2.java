package tests;

import main.cryptogram.LetterCryptogram;
import main.cryptogram.NumberCryptogram;
import main.game.Game;
import main.players.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

/* As a player I want to be able to enter a letter so I can solve the cryptogram */
public class UserStory2 {

    private Game game;
    private Player player;
    private ArrayList<String> sentences;

    @Before
    public void setUp(){
        String PLAYER_NAME = "test";
        player = new Player(PLAYER_NAME);
        sentences = new ArrayList<>();
        String SOLUTION = "This is a test sentence that needs to be solved";
        sentences.add(SOLUTION);
    }


    /*
    Scenario: player enters a letter
        - Given a cryptogram has been generated and is being played
        - When the player identifies a value to replace with a letter
        - Then the letter is mapped to that value and is filled in for all instances in the cryptogram,
          the player’s statistics (numGuesses, numCorrectGuesses) are updated
     */
    @Test
    public void enterLetterLetterCryptogram() throws Exception{
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game = new Game(player, sentences, false);
        game.playGame();

        LetterCryptogram letter = (LetterCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual letters from the encrypted phrase
        HashSet<Character> set = new HashSet<>();
        for(char c : letter.getPhrase().toCharArray()){
            if(c != ' ' && c != '!') {
                set.add(c);
            }
        }

        ArrayList<Character> list = new ArrayList<>(set);

        String originalPhrase = letter.getPhrase();

        // One random letter entry by user
        game.enterLetter(String.valueOf(list.get(0)), String.valueOf(list.get(0)));

        // Checking if all instances has been replaced
        Assert.assertEquals(letter.getPhrase(), originalPhrase.replace((list.get(0)), list.get(0)));
        originalPhrase = letter.getPhrase();

        Assert.assertEquals(1, player.getTotalGuesses());

        // One valid entry by user
        char validEntry = letter.getPlainLetter(list.get(2));
        game.enterLetter(String.valueOf(list.get(1)), String.valueOf(validEntry));

        // Checking if all instances has been replaced
        Assert.assertEquals(letter.getPhrase(), originalPhrase.replace((list.get(1)), validEntry));

        // Checking if the total guesses updated
        Assert.assertEquals(2, player.getTotalGuesses());

    }

    /*
Scenario: player enters a letter
    - Given a cryptogram has been generated and is being played
    - When the player identifies a value to replace with a letter
    - Then the letter is mapped to that value and is filled in for all instances in the cryptogram,
      the player’s statistics (numGuesses, numCorrectGuesses) are updated
 */
    @Test
    public void enterLetterNumberCryptogram() throws Exception{
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("N".getBytes());
        System.setIn(in);

        game = new Game(player, sentences, false);
        game.playGame();

        NumberCryptogram number = (NumberCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual numbers from the encrypted phrase
        HashSet<Integer> set = new HashSet<>();
        for(Object c : number.getSolutionInIntegerFormat().toArray()){
            if((Integer) c != 0){
                set.add((Integer) c);
            }
        }

        ArrayList<Integer> list = new ArrayList<>(set);

        number.getSolutionInIntegerFormat().toArray(new Integer[0]);

        // One random letter entry by user
        game.enterLetter(String.valueOf(list.get(0)), String.valueOf(list.get(0)));

        // One valid entry by user
        char validEntry = number.getPlainLetter(list.get(1));
        game.enterLetter(String.valueOf(list.get(1)), String.valueOf(validEntry));

        // Checking if the total guesses updated
        Assert.assertEquals(2, player.getTotalGuesses());
        Assert.assertEquals(1, player.getTotalCorrectGuesses());

    }

    /*
    Scenario: player selects a cryptogram value which has already been mapped
        - Given a cryptogram has been generated and is being played
        - When the player selects a cryptogram value which they have already mapped
        - Then the player is asked if they want to overwrite the mapping, if they do
          it’s overwritten and stats updated, if not the original mapping remains
    */
    @Test
    public void cryptoAlreadyMappedLetterYesAnswer() throws Exception {
        InputStream sysInBackup; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);


        game = new Game(player, sentences, false);
        game.playGame();

        LetterCryptogram letter = (LetterCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual letters from the encrypted phrase
        HashSet<Character> set = new HashSet<>();
        for(char c : letter.getPhrase().toCharArray()){
            if(c != ' ' && c != '!') {
                set.add(c);
            }
        }

        ArrayList<Character> list = new ArrayList<>(set);

        String originalPhrase = letter.getPhrase();

        // One random letter entry by user
        game.enterLetter(String.valueOf(list.get(0)), String.valueOf(list.get(0)));

        // Checking if all instances has been replaced
        Assert.assertEquals(letter.getPhrase(), originalPhrase.replace((list.get(0)), list.get(0)));
        originalPhrase = letter.getPhrase();

        Assert.assertEquals(1, player.getTotalGuesses());

        // One valid entry by user
        char validEntry = letter.getPlainLetter(list.get(0));
         sysInBackup = System.in; // backup System.in to restore it later
         in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game.enterLetter(String.valueOf(list.get(0)), String.valueOf(validEntry));

        // Checking if all instances has been replaced
        Assert.assertEquals(letter.getPhrase(), originalPhrase.replace((list.get(0)), validEntry));

        // Checking if the total guesses updated
        Assert.assertEquals(2, player.getTotalGuesses());

        System.setIn(sysInBackup);
    }

    /*
    Scenario: player selects a cryptogram value which has already been mapped
        - Given a cryptogram has been generated and is being played
        - When the player selects a cryptogram value which they have already mapped
        - Then the player is asked if they want to overwrite the mapping, if they do
          it’s overwritten and stats updated, if not the original mapping remains
    */
    @Test
    public void cryptoAlreadyMappedLetterNoAnswer() throws Exception {
        InputStream sysInBackup; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game = new Game(player, sentences, false);
        game.playGame();

        LetterCryptogram letter = (LetterCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual letters from the encrypted phrase
        HashSet<Character> set = new HashSet<>();
        for(char c : letter.getPhrase().toCharArray()){
            if(c != ' ' && c != '!') {
                set.add(c);
            }
        }

        ArrayList<Character> list = new ArrayList<>(set);

        String originalPhrase = letter.getPhrase();

        // One random letter entry by user
        game.enterLetter(String.valueOf(list.get(0)), String.valueOf(list.get(0)));

        // Checking if all instances has been replaced
        Assert.assertEquals(letter.getPhrase(), originalPhrase.replace((list.get(0)), list.get(0)));
        char originalInput = list.get(0);

        Assert.assertEquals(1, player.getTotalGuesses());

        // One valid entry by user
        char validEntry = letter.getPlainLetter(list.get(1));
         sysInBackup = System.in; // backup System.in to restore it later
         in = new ByteArrayInputStream("N".getBytes());
        System.setIn(in);

        game.enterLetter(String.valueOf(list.get(0)), String.valueOf(validEntry));

        Assert.assertEquals(game.getInputFromUserLetter().get(list.get(0)).charValue(), originalInput);

        // Checking if the total guesses updated


        System.setIn(sysInBackup);
    }

    /*
    Scenario: player selects a plain letter which they have already mapped
        - Given a cryptogram has been generated and is being played
        - When the player selects a cryptogram value to map to a plain letter they have already used
        - Then an error message is shown to the player and they are asked to try again
    */
    @Test(expected = Exception.class)
    public void plainAlreadyMappedLetter() throws Exception {
        game = new Game(player, sentences, false);

        LetterCryptogram letter = (LetterCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual letters from the encrypted phrase
        HashSet<Character> set = new HashSet<>();
        for(char c : letter.getPhrase().toCharArray()){
            if(c != ' ' && c != '!') {
                set.add(c);
            }
        }

        ArrayList<Character> list = new ArrayList<>(set);

        String originalPhrase = letter.getPhrase();

        // One random letter entry by user
        game.enterLetter(String.valueOf(list.get(0)), String.valueOf(list.get(0)));

        // Checking if all instances has been replaced
        Assert.assertEquals(letter.getPhrase(), originalPhrase.replace((list.get(0)), list.get(0)));

        Assert.assertEquals(1, player.getTotalGuesses());

        game.enterLetter(String.valueOf(list.get(1)), String.valueOf(list.get(0)));

        // Checking if the total guesses updated
        Assert.assertEquals(1, player.getTotalGuesses());
        Assert.assertEquals(0, player.getTotalCorrectGuesses());

    }

    /*
Scenario: player selects a plain letter which they have already mapped
    - Given a cryptogram has been generated and is being played
    - When the player selects a cryptogram value to map to a plain letter they have already used
    - Then an error message is shown to the player and they are asked to try again
*/
    @Test(expected = Exception.class)
    public void plainAlreadyMappedNumber() throws Exception {
        game = new Game(player, sentences, false);

        NumberCryptogram number = (NumberCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual numbers from the encrypted phrase
        HashSet<Integer> set = new HashSet<>();
        for(Object c : number.getSolutionInIntegerFormat().toArray()){
            if((Integer) c != 0){
                set.add((Integer) c);
            }
        }

        ArrayList<Integer> list = new ArrayList<>(set);

        // One random letter entry by user
        game.enterLetter(String.valueOf(list.get(0)), String.valueOf(list.get(0)));

        game.enterLetter(String.valueOf(list.get(1)), String.valueOf(list.get(0)));

        // Checking if the total guesses updated
        Assert.assertEquals(1, player.getTotalGuesses());
        Assert.assertEquals(0, player.getTotalCorrectGuesses());

    }

    /*
    Scenario: player enters the last value to be mapped and successfully completes the cryptogram
        - Given a cryptogram has been generated and is being played
        - When the player enters the last value to be mapped and their mapping is correct
        - Then a success message is displayed, their stats are updated and the game is finished
    */
    @Test
    public void enterLastValueCorrectLetter() throws Exception {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game = new Game(player, sentences, false);
        game.playGame();

        LetterCryptogram letter = (LetterCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual letters from the encrypted phrase
        HashSet<Character> set = new HashSet<>();
        for(char c : letter.getPhrase().toCharArray()){
            if(c != ' ' && c != '!') {
                set.add(c);
            }
        }

        ArrayList<Character> list = new ArrayList<>(set);

        // One valid entry by user

        int i;
        for(i = 0; i < list.size()-1; ++i){
            char validEntry = letter.getPlainLetter(list.get(i));
            game.enterLetter(String.valueOf(list.get(i)), String.valueOf(validEntry));
        }

        // The last entry which is correct
        char validEntry = letter.getPlainLetter(list.get(i));
        game.enterLetter(String.valueOf(list.get(i)), String.valueOf(validEntry));

        // Checking if the total guesses updated
        Assert.assertEquals(player.getTotalGuesses(), i + 1);
        Assert.assertEquals(player.getTotalCorrectGuesses(), i + 1);
        Assert.assertNull(game.getPlayerGameMapping().get(player));

    }

    /*
Scenario: player enters the last value to be mapped and successfully completes the cryptogram
    - Given a cryptogram has been generated and is being played
    - When the player enters the last value to be mapped and their mapping is correct
    - Then a success message is displayed, their stats are updated and the game is finished
*/
    @Test
    public void enterLastValueCorrectNumber() throws Exception {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("N".getBytes());
        System.setIn(in);

        game = new Game(player, sentences, false);
        game.playGame();

        NumberCryptogram number = (NumberCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual numbers from the encrypted phrase
        HashSet<Integer> set = new HashSet<>();
        for(Object c : number.getSolutionInIntegerFormat().toArray()){
            if((Integer) c != 0){
                set.add((Integer) c);
            }
        }

        ArrayList<Integer> list = new ArrayList<>(set);

        int i;
        for(i = 0; i < list.size()-1; ++i){
            char validEntry = number.getPlainLetter(list.get(i));
            game.enterLetter(String.valueOf(list.get(i)), String.valueOf(validEntry));
        }

        // The last entry which is correct
        char validEntry = number.getPlainLetter(list.get(i));
        game.enterLetter(String.valueOf(list.get(i)), String.valueOf(validEntry));

        // Checking if the total guesses updated
        Assert.assertEquals(player.getTotalGuesses(), i + 1);
        Assert.assertEquals(player.getTotalCorrectGuesses(), i + 1);
    }

    /*
    Scenario: player enters the last value to be mapped and unsuccessfully completes the cryptogram
        - Given a cryptogram has been generated and is being played
        - When the player enters the last value to be mapped and their mapping is incorrect
        - Then a fail message is displayed, the player stats are updated, and the game continues
    */
    @Test
    public void enterLastValueInCorrectLetter() throws Exception {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("Y".getBytes());
        System.setIn(in);

        game = new Game(player, sentences, false);
        game.playGame();

        LetterCryptogram letter = (LetterCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual letters from the encrypted phrase
        HashSet<Character> set = new HashSet<>();
        for(char c : letter.getPhrase().toCharArray()){
            if(c != ' ' && c != '!') {
                set.add(c);
            }
        }

        ArrayList<Character> list = new ArrayList<>(set);

        int i;
        for(i = 0; i < list.size()-1; ++i){
            char validEntry = letter.getPlainLetter(list.get(i));
            game.enterLetter(String.valueOf(list.get(i)), String.valueOf(validEntry));
        }

        // The last entry which is incorrect
        game.enterLetter(String.valueOf(list.get(i)), "#");

        // Checking if the total guesses updated
        Assert.assertEquals(player.getTotalGuesses(), i + 1);
        Assert.assertEquals(player.getTotalCorrectGuesses(), i);
        Assert.assertNull(game.getPlayerGameMapping().get(player));
    }

    /*
Scenario: player enters the last value to be mapped and unsuccessfully completes the cryptogram
    - Given a cryptogram has been generated and is being played
    - When the player enters the last value to be mapped and their mapping is incorrect
    - Then a fail message is displayed, the player stats are updated, and the game continues
*/
    @Test
    public void enterLastValueInCorrectNumber() throws Exception {
        // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("N".getBytes());
        System.setIn(in);
        game = new Game(player, sentences, false);
        game.playGame();

        NumberCryptogram number = (NumberCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual numbers from the encrypted phrase
        HashSet<Integer> set = new HashSet<>();
        for(Object c : number.getSolutionInIntegerFormat().toArray()){
            if((Integer) c != 0){
                set.add((Integer) c);
            }
        }

        ArrayList<Integer> list = new ArrayList<>(set);

        int i;
        for(i = 0; i < list.size()-1; ++i){
            char validEntry = number.getPlainLetter(list.get(i));
            game.enterLetter(String.valueOf(list.get(i)), String.valueOf(validEntry));
        }

        // The last entry which is correct
        game.enterLetter(String.valueOf(list.get(i)), "0");

        // Checking if the total guesses updated
        Assert.assertEquals(player.getTotalGuesses(), i + 1);
        Assert.assertEquals(player.getTotalCorrectGuesses(), i);
    }

    /*
    Scenario: player enters a cryptogram value which is not used in the cryptogram
        - Given a cryptogram has been generated and is being played
        - When the player enters a cryptogram value to map which is not used in the cryptogram
        - Then an error message is shown to the user
    */
    @Test(expected = Exception.class)
    public void enterNotUsedLetter() throws Exception {
        game = new Game(player, sentences, false);

        LetterCryptogram letter = (LetterCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual letters from the encrypted phrase
        HashSet<Character> set = new HashSet<>();
        for(char c : letter.getPhrase().toCharArray()){
            if(c != ' ' && c != '!') {
                set.add(c);
            }
        }

        ArrayList<Character> list = new ArrayList<>(set);

        // Need to find a number that is not in use
        char i;
        for(i = 'a'; i <= 'z'; i++){
            if(!list.contains(i)){
                break;
            }
        }

        game.enterLetter(String.valueOf(i), "b");

        // Checking if the total guesses updated
        Assert.assertEquals(1, player.getTotalGuesses());
        Assert.assertEquals(0, player.getTotalCorrectGuesses());
    }

    /*
Scenario: player enters a cryptogram value which is not used in the cryptogram
    - Given a cryptogram has been generated and is being played
    - When the player enters a cryptogram value to map which is not used in the cryptogram
    - Then an error message is shown to the user
*/
    @Test(expected = Exception.class)
    public void enterNotUsedNumber() throws Exception {
        game = new Game(player, sentences, false);

        NumberCryptogram number = (NumberCryptogram) game.getPlayerGameMapping().get(player);

        // Collecting individual numbers from the encrypted phrase
        HashSet<Integer> set = new HashSet<>();
        for(Object c : number.getSolutionInIntegerFormat().toArray()){
            if((Integer) c != 0){
                set.add((Integer) c);
            }
        }

        ArrayList<Integer> list = new ArrayList<>(set);

        // Need to find a number that is not in use
        int i;
        for(i = 1; i < 27; i++){
            if(!list.contains(i)){
                break;
            }
        }

        // The last entry which is correct
        game.enterLetter(String.valueOf(i), "h");

        // Checking if the total guesses updated
        Assert.assertEquals(1, player.getTotalGuesses());
        Assert.assertEquals(0, player.getTotalCorrectGuesses());
    }

    @After
    public void tearDown(){

    }


}
