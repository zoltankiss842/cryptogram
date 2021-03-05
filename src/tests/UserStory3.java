package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/* As a player I want to be able to undo a letter so I can play the cryptogram */
public class UserStory3 {

    @Before
    public void setUp(){

    }

    /*
    Scenario: player wants to undo a mapped letter
        - Given a cryptogram has been generated and is being played
        - When a player selects a letter to remove from their mapping
        - Then the letter is removed from the player mapping
     */
    @Test
    public void undoMapped(){

    }

    /*
    Scenario: player selects a letter in the cryptogram which they have not mapped
        - Given a cryptogram has been generated and is being played
        - When a player identifies a letter which has not been mapped to remove the mapping
        - Then an error message is displayed to the player indicating the letter has not been mapped
    */
    @Test
    public void undoNotMapped(){

    }

    @After
    public void tearDown(){

    }
}
