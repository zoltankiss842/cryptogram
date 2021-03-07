package main.game;

import main.cryptogram.Cryptogram;
import main.cryptogram.LetterCryptogram;
import main.cryptogram.NumberCryptogram;
import main.exceptions.*;
import main.players.Player;
import main.players.Players;
import main.view.Frame;
import main.view.GameCompletedMessagePane;
import main.view.OverWriteOptionPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Random;


public class Game {

    private HashMap<Player, Cryptogram> playerGameMapping;

    /*
        K: letters from the phrase
        V: user input
     */
    private HashMap<Character, Character> inputFromUserLetter;

    /*
        K: numbers from the phrase
        V: user input
     */
    private HashMap<Integer, Character> inputFromUserNumber;

    private Player currentPlayer;
    private ArrayList<String> entered;
    private ArrayList<String> sentences;
    private String currentPhrase;
    private Frame gameGui;
    private boolean overwrite = false;

    public Game(String userName) throws Exception {
        this(new Player(userName), LetterCryptogram.TYPE, new ArrayList<String>(), true);
        playGame();
    }

    public Game(Player p, String cryptType, boolean createGui) throws Exception {
        try{
            currentPlayer = loadPlayer(p);
            entered = new ArrayList<>();

            if(createGui){
                gameGui = new Frame(currentPlayer.getUsername(), this);
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            System.exit(0);
        }


    }

    public Game(Player p, String cryptType, ArrayList<String> sentences, boolean createGui) throws Exception {
        this(p, cryptType, createGui);
        this.sentences = sentences;
        loadSentences();
        generateCryptogram(currentPlayer, cryptType);

    }

    public char getHint(){
        if(currentPhrase.equals("")){
            System.out.println("Empty phrase");
        }else{
            char[] myArray = currentPhrase.toCharArray();
            ArrayList<Character> phrasechars = new ArrayList<>();
            Set<Character> set = new HashSet();
            for(int i=0;i<myArray.length;i++){
                if(!set.add(myArray[i])){
                    phrasechars.add(myArray[i]);
                }
            }

            Random rand = new Random();
            System.out.println("Here the hint");
            return(phrasechars.get(rand.nextInt(set.size())));
        }

        return 'c';
    }

    public Player loadPlayer(Player p){
        if(Players.findPlayer(p)){
            if(loadGame(p.getUsername())){
                return p;
            }

        }

        return p;
    }

    public void playGame(){
        gameGui.displayNewGame(playerGameMapping.get(currentPlayer));
    }

    public void enterLetter(String cryptoLetter, String newLetter) throws Exception{
        Cryptogram c = playerGameMapping.get(currentPlayer);

        if(c == null){
            throw new NoGameBeingPlayed("Start a new game to enter a letter!");
        }

        if(c instanceof NumberCryptogram){
            try{
                int number = Integer.parseInt(cryptoLetter);

                if(number<=0 || number>26){
                    throw new NumberFormatException("Number is out of bounds!");
                }
                else{
                    enterLetter(number, newLetter);
                }
            }
            catch(NumberFormatException e){
                System.err.println(e.getMessage());
            }
        }
        else{

            char cryptoChar = cryptoLetter.charAt(0);
            Character plainLetterAtCryptoChar = inputFromUserLetter.get(cryptoChar);

            char newChar = newLetter.charAt(0);


            if(!isLetterUsedLetter(cryptoChar)){
                throw new NoSuchCryptogramLetter("This cryptogram letter is not used in this sentence");
            }

            if(plainLetterAtCryptoChar != null && plainLetterAtCryptoChar != newChar){
                if(gameGui != null){
                    OverWriteOptionPane pane = new OverWriteOptionPane(gameGui.getFrame(),
                            String.valueOf(plainLetterAtCryptoChar.charValue()),
                            String.valueOf(newChar));

                    if(pane.getResult()){

                        for (Map.Entry<Character, Character> entry : inputFromUserLetter.entrySet()) {
                            if (entry.getValue() != null && entry.getValue().equals(newChar) && entry.getKey() != cryptoChar) {
                                throw new PlainLetterAlreadyInUse("Plain letter already in use for cyptogram: " + entry.getKey());
                            }
                        }

                        overwrite = true;

                        inputFromUserLetter.put(cryptoChar, newChar);

                        String phrase = playerGameMapping.get(currentPlayer).getPhrase();
                        phrase = phrase.replace(cryptoChar, newLetter.charAt(0));
                        playerGameMapping.get(currentPlayer).setPhrase(phrase);

                        currentPlayer.incrementTotalGuesses();

                        if(c instanceof LetterCryptogram){
                            LetterCryptogram letter = (LetterCryptogram)c;
                            Character original = (Character) letter.getCryptogramAlphabet().get(cryptoChar);
                            char temp = (Character) original;

                            if(temp == newLetter.charAt(0)){
                                currentPlayer.incrementTotalCorrectGuesses();
                            }
                        }
                    }
                }
                else{
                    Scanner sc = new Scanner(System.in);

                    System.out.println("Mapping from " + cryptoChar + " to " + newChar + " is already present.");
                    System.out.println("Would you like to replace it? Y/N");
                    String answer = sc.nextLine();

                    if(answer.equals("Y")){
                        inputFromUserLetter.put(cryptoChar, newChar);

                        String phrase = playerGameMapping.get(currentPlayer).getPhrase();
                        phrase = phrase.replace(cryptoChar, newChar);
                        playerGameMapping.get(currentPlayer).setPhrase(phrase);

                        currentPlayer.incrementTotalGuesses();

                        if(c instanceof LetterCryptogram){
                            LetterCryptogram letter = (LetterCryptogram)c;
                            Character original = (Character) letter.getCryptogramAlphabet().get(cryptoChar);
                            char temp = (Character) original;

                            if(temp == newChar){
                                currentPlayer.incrementTotalCorrectGuesses();
                            }
                        }
                    }
                }

            }
            else{

                overwrite = true;

                for (Map.Entry<Character, Character> entry : inputFromUserLetter.entrySet()) {
                    if (entry.getValue() != null && entry.getValue().equals(newChar) && entry.getKey() != cryptoChar) {
                        throw new PlainLetterAlreadyInUse("Plain letter already in use for cyptogram: " + entry.getKey());
                    }
                }

                    inputFromUserLetter.put(cryptoChar, newChar);

                    String phrase = playerGameMapping.get(currentPlayer).getPhrase();
                    phrase = phrase.replace(cryptoChar, newChar);
                    playerGameMapping.get(currentPlayer).setPhrase(phrase);

                    currentPlayer.incrementTotalGuesses();

                    if(c instanceof LetterCryptogram){
                        LetterCryptogram letter = (LetterCryptogram)c;
                        Character original = (Character) letter.getCryptogramAlphabet().get(cryptoChar);
                        char temp = (Character) original;

                        if(temp == newChar){
                            currentPlayer.incrementTotalCorrectGuesses();
                        }
                    }
            }

            if(isEverythingMappedLetter()){
                boolean success = checkAnswer();
                if(success){
                    currentPlayer.incrementCryptogramsCompleted();  // This is the successful completion
                    currentPlayer.incrementCryptogramsPlayed();
                    GameCompletedMessagePane complete = new GameCompletedMessagePane(gameGui.getFrame(), success);
                }
                else{
                    currentPlayer.incrementCryptogramsPlayed();
                    GameCompletedMessagePane complete = new GameCompletedMessagePane(gameGui.getFrame(), success);
                }

                playerGameMapping.put(currentPlayer, null);
                inputFromUserNumber = null;
                inputFromUserLetter = null;
            }

        }

    }

    private void enterLetter(int number, String newLetter) throws Exception {

        if(!isLetterUsedNumber(number)){
            throw new NoSuchCryptogramLetter("This letter is not used in this sentence");
        }

        if(inputFromUserNumber.get(number) != null){

            if(gameGui != null){
                OverWriteOptionPane pane = new OverWriteOptionPane(gameGui.getFrame(),
                        String.valueOf(inputFromUserNumber.get(number).charValue()),
                        String.valueOf(newLetter.charAt(0)));

                if(pane.getResult()){

                    overwrite = true;

                    inputFromUserNumber.put(number, newLetter.charAt(0));

                    currentPlayer.incrementTotalGuesses();

                    NumberCryptogram c = (NumberCryptogram) playerGameMapping.get(currentPlayer);

                    Object original = c.getCryptogramAlphabet().get(number);
                    char temp = (Character) original;

                    if(temp == newLetter.charAt(0)){
                        currentPlayer.incrementTotalCorrectGuesses();
                    }
                }
            }
            else{
                Scanner sc = new Scanner(System.in);

                System.out.println("Mapping from " + number + " to " + newLetter + " is already present.");
                System.out.println("Would you like to replace it? Y/N");
                String answer = sc.nextLine();

                if(answer.equals("Y")){

                    inputFromUserNumber.put(number, newLetter.charAt(0));

                    currentPlayer.incrementTotalGuesses();

                    NumberCryptogram c = (NumberCryptogram) playerGameMapping.get(currentPlayer);

                    Object original = c.getCryptogramAlphabet().get(number);
                    char temp = (Character) original;

                    if(temp == newLetter.charAt(0)){
                        currentPlayer.incrementTotalCorrectGuesses();
                    }
                }
            }


        }
        else{

            for(Map.Entry<Integer, Character> entry : inputFromUserNumber.entrySet()){
                if(entry.getValue() != null && entry.getValue().equals(newLetter.charAt(0))){
                    throw new PlainLetterAlreadyInUse("Plain letter already in use for cyptogram: " + entry.getKey());
                }
            }

            inputFromUserNumber.put(number, newLetter.charAt(0));

            currentPlayer.incrementTotalGuesses();

            NumberCryptogram c = (NumberCryptogram) playerGameMapping.get(currentPlayer);

            Object original = c.getCryptogramAlphabet().get(number);
            char temp = (Character) original;

            if(temp == newLetter.charAt(0)){
                currentPlayer.incrementTotalCorrectGuesses();
            }
        }

        if(isEverythingMappedNumber()){
            if(checkAnswer()){
                System.out.println("You have successfully completed the cryptogram!");
                currentPlayer.incrementCryptogramsCompleted();  // This is the successful completion
                currentPlayer.incrementCryptogramsPlayed();
            }
            else{
                System.out.println("Better luck next time...");
                currentPlayer.incrementCryptogramsPlayed();
            }

            playerGameMapping.put(currentPlayer, null);
        }

    }

    public void undoLetter(String letter) throws Exception {
        Cryptogram c = playerGameMapping.get(currentPlayer);

        if(letter.isEmpty() || letter.isBlank() || letter.equals(" ") || letter == null){
            return;
        }

        if(c instanceof LetterCryptogram){
            boolean found = false;
            char key = letter.charAt(0);

            if(inputFromUserLetter.containsKey(key)){
                Character before = inputFromUserLetter.get(key);

                if(before == null){
                    return;
                }

                inputFromUserLetter.put(key, null);

                String phrase = c.getPhrase();
                phrase = phrase.replace(before, key);
                c.setPhrase(phrase);
            }
            else{
                throw new NoSuchPlainLetter("No such letter was mapped");
            }
        }
        else if(c instanceof NumberCryptogram){
            boolean found = false;
            for(Map.Entry<Integer, Character> entry : inputFromUserNumber.entrySet()){
                if(entry.getValue() != null && entry.getValue().equals(letter.charAt(0))){
                    inputFromUserNumber.put(entry.getKey(), null);
                    found = true;
                }
            }

            if(!found){
                throw new NoSuchPlainLetter("No such letter was mapped");
            }
        }


    }

    public boolean viewFrequencies(){
        File f = new File("saves.txt");
        Scanner mys;
        try {
            mys = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        while(mys.hasNextLine()){
            String data = mys.nextLine();
            String[] tokens = data.split(" ");
            System.out.println(tokens[3]);
        }
        return true;
    }

    public void saveGame(String name){
        try{
            File f = new File("saves.txt");
            if(f.createNewFile()){
                System.out.println("File created: "+f.getName());
            }else{
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
        try{
            FileWriter myw = new FileWriter("saves.txt");
            myw.write(name+" "+"game");
            myw.close();
            System.out.println("Successfully saved");
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean loadGame(String name){
        File f = new File("saves.txt");
        Scanner mys;
        try {
            mys = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        while(mys.hasNextLine()){
            String data = mys.nextLine();
            String[] tokens = data.split(" ");
            if(name.equals(tokens[0])){
                return true;
            }
        }
        return false;
    }

    public boolean loadSentences(){
//        File f = new File("phrases.txt");
//        Scanner mys;
//        try{
//            mys = new Scanner(f);
//        }catch(FileNotFoundException e){
//            e.printStackTrace();
//            return false;
//        }
//        ArrayList<String> phrases = new ArrayList<>();
//        Random rand = new Random();
//        while(mys.hasNextLine()){
//            phrases.add(mys.nextLine());
//        }

        sentences.add("This is a very long sentence that will be displayed so we will see what is going to happen");
        sentences.add("He was so preoccupied with whether or not he could that he failed to stop to consider if he should");
        sentences.add("Pair your designer cowboy hat with scuba gear for a memorable occasion");
        sentences.add("For oil spots on the floor, nothing beats parking a motorbike in the lounge");
        sentences.add("He said he was not there yesterday however many people saw him there");

        //String chosenphrase = ArrayList.get(rand.nextInt(ArrayList.size()));

        return false;
    }

    public void showSolution(){
        if(currentPlayer!=null){
            /*Cryptogram c = playerGameMapping.get(currentPlayer);
            show(c.phrase);*/
        }
    }

    public HashMap<Player, Cryptogram> getPlayerGameMapping() {
        return playerGameMapping;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean checkAnswer(){
        Cryptogram c = playerGameMapping.get(currentPlayer);

        if(c instanceof LetterCryptogram){
            LetterCryptogram letter = (LetterCryptogram) c;
            for(Map.Entry<Character, Character> entry : inputFromUserLetter.entrySet()){
                if(letter.getPlainLetter(entry.getKey()) != entry.getValue()){
                    return false;
                }
            }
        }
        else if(c instanceof NumberCryptogram){
            NumberCryptogram number = (NumberCryptogram) c;
            for(Map.Entry<Integer, Character> entry : inputFromUserNumber.entrySet()){
                if(number.getPlainLetter(entry.getKey()) != entry.getValue()){
                    return false;
                }
            }
        }

        return true;
    }

    private void generateCryptogram(Player player, String type) throws Exception{
        Random rnd = new Random();
        String solution = "";
        Cryptogram cryptogram;

        if(sentences.size()>0){
             solution = sentences.get(rnd.nextInt(sentences.size()));
        }
        else{
            throw new NoPhrasesToGenerate("There are no sentences, exiting...");
        }

        if(type.equals(LetterCryptogram.TYPE)){
            cryptogram = new LetterCryptogram(solution);
        }
        else if(type.equals(NumberCryptogram.TYPE)){
            cryptogram = new NumberCryptogram(solution);
        }
        else{
            throw new NoSuchGameType("No such game type, exiting...");
        }

        playerGameMapping = new HashMap<>();
        playerGameMapping.put(player, cryptogram);

        if(cryptogram instanceof LetterCryptogram){
            inputFromUserLetter = new HashMap<>();
            for(char c : cryptogram.getPhrase().toCharArray()){
                if(c != ' '){
                    inputFromUserLetter.put(c, null);
                }
            }
            currentPhrase = cryptogram.getPhrase();
        }
        else if(cryptogram instanceof NumberCryptogram){
            inputFromUserNumber = new HashMap<>();
            for(int number : ((NumberCryptogram) cryptogram).getSolutionInIntegerFormat()){
                inputFromUserNumber.put(number, null);
            }
            currentPhrase = cryptogram.getPhrase();
        }



    }

    public ArrayList<String> getSentences() {
        return sentences;
    }

    public void setSentences(ArrayList<String> sentences) {
        this.sentences = sentences;
    }

    private boolean isEverythingMappedNumber(){
        for(Map.Entry<Integer, Character> entry : inputFromUserNumber.entrySet()){
            if(entry.getValue() == null){
                return false;
            }
        }

        return true;
    }

    private boolean isEverythingMappedLetter(){
        for(Map.Entry<Character, Character> entry : inputFromUserLetter.entrySet()){
            if(entry.getValue() == null){
                return false;
            }
        }

        return true;
    }

    public HashMap<Character, Character> getInputFromUserLetter() {
        return inputFromUserLetter;
    }

    private boolean isLetterUsedLetter(char letter){
        return inputFromUserLetter.containsKey(letter);
    }

    private boolean isLetterUsedNumber(int number){
        return inputFromUserNumber.containsKey(number);
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public boolean isOverwrite() {
        return overwrite;
    }
}
