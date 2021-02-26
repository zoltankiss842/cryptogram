package players;

import java.util.*;

public class Player {
    private String username;
    private int accuracy;
    private int totalGuesses;
    private int cryptogramsPlayed;
    private int cryptogramsCompleted;

    public Player(String playername) {
        username = playername;
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

}