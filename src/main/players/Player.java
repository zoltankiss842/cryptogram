package main.players;

import java.util.*;

public class Player {
    private String username;
    private int accuracy;
    private int totalGuesses;
    private int totalCorrectGuesses;
    private int cryptogramsPlayed;
    private int cryptogramsCompleted;

    public Player(String playername) {
        username = playername;
        accuracy = 0;
        totalGuesses = 0;
        totalCorrectGuesses = 0;
        cryptogramsPlayed = 0;
        cryptogramsCompleted = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int updateAccuracy() {
        return accuracy;
    }

    public void incrementTotalGuesses() {
        totalGuesses++;
    }

    public void incrementTotalCorrectGuesses() {
        totalCorrectGuesses++;
    }

    public void incrementCryptogramsCompleted() {
        cryptogramsCompleted++;
    }

    public void incrementCryptogramsPlayed() {
        cryptogramsPlayed++;
    }

    public int getNumCryptogramsCompleted() {
        return cryptogramsCompleted;
    }

    public int getNumCryptogramsPlayed() {
        return cryptogramsPlayed;
    }

    public int getTotalGuesses() {
        return totalGuesses;
    }

    public int getTotalCorrectGuesses() {
        return totalCorrectGuesses;
    }
}