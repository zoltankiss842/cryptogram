package main.players;

/**
 * This class represents a player entity.
 */
public class Player {
    private final String username;
    private double accuracy;
    private int totalGuesses;
    private int totalCorrectGuesses;
    private int cryptogramsPlayed;
    private int cryptogramsCompleted;
    private int cryptogramsSuccessfullyCompleted;

    public Player(String playerName) {
        username = playerName;
        accuracy = 0;
        totalGuesses = 0;
        totalCorrectGuesses = 0;
        cryptogramsPlayed = 0;
        cryptogramsCompleted = 0;
        cryptogramsSuccessfullyCompleted = 0;
    }

    public String getUsername() {
        return username;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getNumCryptogramsSuccessfullyCompleted() {
        return cryptogramsSuccessfullyCompleted;
    }

    public int getNumCryptogramsCompleted() {
        return cryptogramsCompleted;
    }

    public int getNumCryptogramsPlayed() {
        return cryptogramsPlayed;
    }

    public void setTotalGuesses(int totalGuesses) {
        this.totalGuesses = totalGuesses;
    }

    public void setCryptogramsPlayed(int cryptogramsPlayed) {
        this.cryptogramsPlayed = cryptogramsPlayed;
    }

    public void setCryptogramsCompleted(int cryptogramsCompleted) {
        this.cryptogramsCompleted = cryptogramsCompleted;
    }

    public void setCryptogramsSuccessfullyCompleted(int cryptogramsSuccessfullyCompleted) {
        this.cryptogramsSuccessfullyCompleted = cryptogramsSuccessfullyCompleted;
    }

    public void setTotalCorrectGuesses(int totalCorrectGuesses) {
        this.totalCorrectGuesses = totalCorrectGuesses;
    }

    public int getTotalGuesses() {
        return totalGuesses;
    }

    public int getTotalCorrectGuesses() {
        return totalCorrectGuesses;
    }

    public void incrementTotalGuesses() {
        totalGuesses++;
        updateAccuracy();
    }

    public void incrementTotalCorrectGuesses() {
        totalCorrectGuesses++;
        updateAccuracy();
    }

    public void incrementCryptogramsSuccessfullyCompleted() { cryptogramsSuccessfullyCompleted++; }

    public void incrementCryptogramsCompleted() {
        cryptogramsCompleted++;
    }

    public void incrementCryptogramsPlayed() {
        cryptogramsPlayed++;
    }

    private void updateAccuracy(){
        if(this.totalGuesses != 0){
            this.accuracy = (double)this.totalCorrectGuesses / (double)this.totalGuesses;
        }
    }
}