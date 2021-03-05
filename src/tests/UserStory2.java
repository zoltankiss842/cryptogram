package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/* As a player I want to be able to enter a letter so I can solve the cryptogram */
public class UserStory2 {

    @Before
    public void setUp(){

    }


    /*
    Scenario: player enters a letter
        - Given a cryptogram has been generated and is being played
        - When the player identifies a value to replace with a letter
        - Then the letter is mapped to that value and is filled in for all instances in the cryptogram,
          the player’s statistics (numGuesses, numCorrectGuesses) are updated
     */
    @Test
    public void enterLetter(){

    }

    /*
    Scenario: player selects a cryptogram value which has already been mapped
        - Given a cryptogram has been generated and is being played
        - When the player selects a cryptogram value which they have already mapped
        - Then the player is asked if they want to overwrite the mapping, if they do
          it’s overwritten and stats updated, if not the original mapping remains
    */
    @Test
    public void cryptoAlreadyMapped(){

    }

    /*
    Scenario: player selects a plain letter which they have already mapped
        - Given a cryptogram has been generated and is being played
        - When the player selects a cryptogram value to map to a plain letter they have already used
        - Then an error message is shown to the player and they are asked to try again
    */
    @Test
    public void plainAlreadyMapped(){

    }

    /*
    Scenario: player enters the last value to be mapped and successfully completes the cryptogram
        - Given a cryptogram has been generated and is being played
        - When the player enters the last value to be mapped and their mapping is correct
        - Then a success message is displayed, their stats are updated and the game is finished
    */
    @Test
    public void enterLastValueCorrect(){

    }

    /*
    Scenario: player enters the last value to be mapped and unsuccessfully completes the cryptogram
        - Given a cryptogram has been generated and is being played
        - When the player enters the last value to be mapped and their mapping is incorrect
        - Then a fail message is displayed, the player stats are updated, and the game continues
    */
    @Test
    public void enterLastValueInCorrect(){

    }

    /*
    Scenario: player enters a cryptogram value which is not used in the cryptogram
        - Given a cryptogram has been generated and is being played
        - When the player enters a cryptogram value to map which is not used in the cryptogram
        - Then an error message is shown to the user
    */
    @Test
    public void enterNotUsed(){

    }

    @After
    public void tearDown(){

    }


}
