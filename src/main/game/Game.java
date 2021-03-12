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
import main.view.Word;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Random;

/**
 * This class is the main controller class.
 * This handles every user input and display output.
 */
public class Game {

    /**
     * A current mapping between a player and a cryptogram.
     * A player can only play one game at a time.
     * When a player finished with a game (regardless of the outcome),
     * the cryptogram sets to null.
     */
    private HashMap<Player, Cryptogram> playerGameMapping;

    /**
     * This mapping only used, when the user is playing a letter cryptogram, else its null.
     * This contains the used letters from the phrase and what did the user enter for that letter.
     * Key:   letters from the phrase
     * Value: user input
     *
     * So on a new game, if the game is a letter cryptogram, this is going to be initialized, with a encrypted letters
     * only once that are used from the encrypted sentence and a null value, as the user has not entered any letters yet.
     * Using the Cryptogram class example, this map would exactly contain these values:
     * {g=null, k=null, p=null, f=null, q=null, m=null, l=null}
     *
     * After the user enters a plain letter to any of the encrypted letters, it will look like this:
     * {g=f, k=null, p=null, f=o, q=null, m=m, l=null}
     *
     * If the user deletes one of the plain letters that they enter, it resets to null and it will look like this:
     * {g=null, k=null, p=null, f=o, q=null, m=m, l=null}
     *
     * For a good solution, the whole mapping will look like this:
     * {g=i, k=l, p=k, f=e, q=a, m=p, l=s}
     *
     * @see Cryptogram#getPhrase()
     */
    private HashMap<Character, Character> inputFromUserLetter;

    /**
     * This mapping only used, when the user is playing a letter cryptogram, else its null.
     * This contains the used letters from the phrase and what did the user enter for that letter.
     * Key:   numbers from the phrase
     * Value: user input
     *
     * So on a new game, if the game is a letter cryptogram, this is going to be initialized, with a encrypted letters
     * only once that are used from the encrypted sentence and a null value, as the user has not entered any letters yet.
     * Using the Cryptogram class example, this map would exactly contain these values:
     * {5=null, 12=null, 7=null, 2=null, 11=null, 24=null, 8=null}
     *
     * After the user enters a plain letter to any of the encrypted letters, it will look like this:
     * {5=m, 12=null, 7=p, 2=null, 11=k, 24=null, 8=q}
     *
     * If the user deletes one of the plain letters that they enter, it resets to null and it will look like this:
     * {5=m, 12=null, 7=p, 2=null, 11=null, 24=null, 8=q}
     *
     * For a good solution, the whole mapping will look like this:
     * {5=i, 12=l, 7=k, 2=e, 11=a, 24=p, 8=s}
     *
     * @see Cryptogram#getPhrase()
     */
    private HashMap<Integer, Character> inputFromUserNumber;

    private final Player currentPlayer;

    private ArrayList<String> sentences;
    private String currentPhrase;
    private Frame gameGui;
    private boolean overwrite = false;

    /**
     * This constructor is used by our program with UI in mind. It is getting generated from
     * NewPlayerFrame.
     *
     * @see main.view.NewPlayerFrame
     *
     * @param userName                   String that the user is given
     * @throws NoSuchGameType            if the user chooses a non-existing game type
     * @throws NoSentencesToGenerateFrom if there are no sentences to generate
     */
    public Game(String userName) throws NoSuchGameType, NoSentencesToGenerateFrom {
        this(new Player(userName), LetterCryptogram.TYPE, new ArrayList<>(), true);
        playGame();
    }

    /**
     * This constructor is mainly used for unit tests, it creates better accessibility.
     * @param p                          current player
     * @param cryptType                  cryptogram type
     * @param sentences                  an arraylist of sentences
     * @param createGui                  should the constructor generate a UI for this program
     * @throws NoSuchGameType            if the user chooses a non-existing game type
     * @throws NoSentencesToGenerateFrom if there are no sentences to generate
     */
    public Game(Player p, String cryptType, ArrayList<String> sentences, boolean createGui) throws NoSuchGameType, NoSentencesToGenerateFrom {
        currentPlayer = loadPlayer(p);
        if(createGui) gameGui = new Frame(currentPlayer.getUsername(), this);
        this.sentences = sentences;
        loadSentences();
        generateCryptogram(currentPlayer, cryptType);
    }

    // Basic getters/setters

    public HashMap<Player, Cryptogram> getPlayerGameMapping() {
        return playerGameMapping;
    }

    public HashMap<Character, Character> getInputFromUserLetter() {
        return inputFromUserLetter;
    }

    public HashMap<Integer, Character> getInputFromUserNumber() {
        return inputFromUserNumber;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }


    /**
     * This method right now only returns the parameter, however
     * this should search a load the players from the text file.
     * As soon as we agree on a text file formatting, it can be done.
     *
     * For now this is a:
     * TODO implement this feature
     * @param p     player with parameters
     * @return      found player
     */
    public Player loadPlayer(Player p){
        if(Players.findPlayer(p)){
            if(loadGame(p.getUsername())){
                return p;
            }

        }

        return p;
    }

    /**
     * This method checks the type of type of cryptogram
     * that need to be generated, generates it and displays it
     * on the UI. This method cannot be used in the unit tests.
     */
    public void playGame(){
        Cryptogram c = playerGameMapping.get(currentPlayer);
        try{
            if (c instanceof LetterCryptogram) {
                generateCryptogram(currentPlayer, LetterCryptogram.TYPE);
            }
            else if(c instanceof NumberCryptogram) {
                generateCryptogram(currentPlayer, NumberCryptogram.TYPE);
            }

            gameGui.displayNewGame(playerGameMapping.get(currentPlayer));
        }
        catch (NoSentencesToGenerateFrom | NoSuchGameType e)
        {
            System.out.println("No cryptogram exists to play");
        }
    }


    /**
     * This method manages user input. Steps for this method:
     * 1. We check if the game is a NumberCryptogram, if so go to enterLetterForNumberCrypto()
     * 2. We check if the cryptoLetter exists in the game
     * 3. We check if there is a plain letter in that place from the user, if so we handle that
     * 4. We check if the plain letter is already used somewhere, if so we handle that
     * 5. We enter the letter, for that place and update the phrase in the cryptogramm class
     * 6. We check if every letter has been mapped, thus ending the game
     * @param cryptoLetter              key for map
     * @param newLetter                 new user value for map
     * @throws NoGameBeingPlayed
     * @throws NoSuchCryptogramLetter
     * @throws PlainLetterAlreadyInUse
     */
    public void enterLetter(String cryptoLetter, String newLetter) throws NoGameBeingPlayed, NoSuchCryptogramLetter, PlainLetterAlreadyInUse{
        Cryptogram c = playerGameMapping.get(currentPlayer);


        if(c == null){  // if the current cryptogram is null, that means a new game must be generated
            throw new NoGameBeingPlayed("Start a new game to enter a letter!");
        }

        if(c instanceof NumberCryptogram){      // in case of a NumberCryptogram, we handle that
            try{
                int number = Integer.parseInt(cryptoLetter);

                if(number<=0 || number>26){
                    throw new NoSuchCryptogramLetter("Entered number is out of bounds!");
                }
                else{
                    enterLetterForNumberCrypto(number, newLetter);
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

            if(!isLetterUsedLetter(cryptoChar)){    // if the cryptogram key does not exist
                throw new NoSuchCryptogramLetter("This cryptogram letter is not used in this sentence");
            }

            // We need to show the overwrite prompt, if the new user input value is the same at the place
            if(plainLetterAtCryptoChar != null && plainLetterAtCryptoChar != newChar){

                // if we are showing the GUI we show a gui prompt
                if(gameGui != null){
                    OverWriteOptionPane pane = showOverWriteOptionPane(plainLetterAtCryptoChar, newChar);

                    if(pane.getResult()){

                        // check if plain letter is already used somewhere
                        checkIfPlainAlreadyInUse(cryptoChar, newChar);

                        overwrite = true;   // this is needed in the View classes, this indicates if it can be overwritten

                        inputFromUserLetter.put(cryptoChar, newChar);   // we put the new value at key

                        // We update the phrase at the Crypto class
                        updatePhrase(cryptoChar, newLetter.charAt(0), playerGameMapping.get(currentPlayer));

                        currentPlayer.incrementTotalGuesses();  // We increment the total number of guesses

                            LetterCryptogram letter = (LetterCryptogram)c;
                            Character original = (Character) letter.getCryptogramAlphabet().get(cryptoChar);
                            char temp = (Character) original;

                            if(temp == newLetter.charAt(0)){
                                incrementCorrectGuesses();
                            }
                    }
                }
                // else we show a terminal prompt
                else{
                    Scanner sc = new Scanner(System.in);

                    System.out.println("Mapping from " + cryptoChar + " to " + newChar + " is already present.");
                    System.out.println("Would you like to replace it? Y/N");
                    String answer = sc.nextLine();

                    if(answer.equals("Y")){
                        inputFromUserLetter.put(cryptoChar, newChar);


                        updatePhrase(cryptoChar, newChar, playerGameMapping.get(currentPlayer));

                        currentPlayer.incrementTotalGuesses();

                            LetterCryptogram letter = (LetterCryptogram)c;
                            Character original = (Character) letter.getCryptogramAlphabet().get(cryptoChar);
                            char temp = original;

                            if(temp == newChar){
                                incrementCorrectGuesses();
                            }
                    }
                }

            }
            else{

                overwrite = true;

                checkIfPlainAlreadyInUse(cryptoChar, newChar);

                inputFromUserLetter.put(cryptoChar, newChar);

                updatePhrase(cryptoChar, newChar, playerGameMapping.get(currentPlayer));

                currentPlayer.incrementTotalGuesses();

                    LetterCryptogram letter = (LetterCryptogram)c;
                    Character original = (Character) letter.getCryptogramAlphabet().get(cryptoChar);
                    char temp = (Character) original;

                    if(temp == newChar){
                        incrementCorrectGuesses();
                    }
            }

            // If the inputFromUserLetter does not contain any null values, that means the player
            // entered a letter to every key, making the game end
            if(isEverythingMappedLetter()){
                boolean success = checkAnswer();

                // If we are showing a GUI, we lock/disable the input fields (also greying them out)
                if(gameGui != null){
                    lockFields();
                }

                // Did the user correctly filled out the fields?
                showGameCompletion(success);

                // TODO #7: create void method, named resetGameDetails()
                // We reset the mappings
                playerGameMapping.put(currentPlayer, null);
                inputFromUserNumber = null;
                inputFromUserLetter = null;
            }

        }

    }

    private void showGameCompletion(boolean success) {
        if (success) {
            currentPlayer.incrementCryptogramsCompleted();  // This is the successful completion
            currentPlayer.incrementCryptogramsPlayed();
            if (gameGui != null) {
                GameCompletedMessagePane complete = new GameCompletedMessagePane(gameGui.getFrame(), success);
            }
        } else {
            currentPlayer.incrementCryptogramsPlayed();

            if (gameGui != null) {
                GameCompletedMessagePane complete = new GameCompletedMessagePane(gameGui.getFrame(), success);
            }

        }
    }

    private void updatePhrase(char cryptoChar, char newChar, Cryptogram cryptogram) {
        String phrase = cryptogram.getPhrase();
        phrase = phrase.replace(cryptoChar, newChar);
        cryptogram.setPhrase(phrase);
    }

    private void incrementCorrectGuesses() {
        currentPlayer.incrementTotalCorrectGuesses();
    }

    private OverWriteOptionPane showOverWriteOptionPane(Character plainLetterAtCryptoChar, char newChar) {
        return new OverWriteOptionPane(gameGui.getFrame(),
                String.valueOf(plainLetterAtCryptoChar.charValue()),
                String.valueOf(newChar));
    }

    private void checkIfPlainAlreadyInUse(char cryptoChar, char newChar) throws PlainLetterAlreadyInUse {
        for (Map.Entry<Character, Character> entry : inputFromUserLetter.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(newChar) && entry.getKey() != cryptoChar) {
                throw new PlainLetterAlreadyInUse("Plain letter already in use for cyptogram: " + entry.getKey());
            }
        }
    }

    /**
     * This method manages user input for a NumberCryptogram. This method works the same way as
     * the enterLetter method, please see that for further information.
     *
     * @see Game#enterLetter(String, String)
     *
     * @param number                    key for map
     * @param newLetter                 new user value for map
     * @throws NoSuchCryptogramLetter
     * @throws PlainLetterAlreadyInUse
     */
    private void enterLetterForNumberCrypto(int number, String newLetter) throws NoSuchCryptogramLetter, PlainLetterAlreadyInUse {
        int key = number;
        Character plainLetterAtCryptoChar = inputFromUserNumber.get(key);

        char newChar = newLetter.charAt(0);

        if(!isLetterUsedNumber(number)){
            throw new NoSuchCryptogramLetter("This cryptogram letter is not used in this sentence");
        }

        if(plainLetterAtCryptoChar != null && plainLetterAtCryptoChar != newChar){

            if(gameGui != null){
                OverWriteOptionPane pane = showOverWriteOptionPane(inputFromUserNumber.get(number), newLetter.charAt(0));

                if(pane.getResult()){

                    overwrite = true;

                    for(Map.Entry<Integer, Character> entry : inputFromUserNumber.entrySet()){
                        if(entry.getValue() != null && entry.getValue().equals(newLetter.charAt(0)) && entry.getKey() != key){
                            throw new PlainLetterAlreadyInUse("Plain letter already in use for cyptogram: " + entry.getKey());
                        }
                    }

                    inputFromUserNumber.put(number, newLetter.charAt(0));

                    currentPlayer.incrementTotalGuesses();

                    NumberCryptogram c = (NumberCryptogram) playerGameMapping.get(currentPlayer);

                    Object original = c.getCryptogramAlphabet().get(number);
                    char temp = (Character) original;

                    if(temp == newLetter.charAt(0)){
                        incrementCorrectGuesses();
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
                        incrementCorrectGuesses();
                    }
                }
            }


        }
        else{
            overwrite = true;

            for(Map.Entry<Integer, Character> entry : inputFromUserNumber.entrySet()){
                if(entry.getValue() != null && entry.getValue().equals(newLetter.charAt(0)) && entry.getKey() != key){
                    throw new PlainLetterAlreadyInUse("Plain letter already in use for cyptogram: " + entry.getKey());
                }
            }

            inputFromUserNumber.put(number, newLetter.charAt(0));

            currentPlayer.incrementTotalGuesses();

            NumberCryptogram c = (NumberCryptogram) playerGameMapping.get(currentPlayer);

            Object original = c.getCryptogramAlphabet().get(number);
            char temp = (Character) original;

            if(temp == newLetter.charAt(0)){
                incrementCorrectGuesses();
            }
        }

        if(isEverythingMappedNumber()){
            boolean success = checkAnswer();

            if(gameGui != null){
                lockFields();
            }

            showGameCompletion(success);

            // TODO #7: create void method, named resetGameDetails()
            playerGameMapping.put(currentPlayer, null);
            inputFromUserNumber = null;
            inputFromUserLetter = null;
        }

    }

    /**
     * This method deletes/resets a letter at a mapping.
     * @param letter                key from mapping
     * @throws NoSuchPlainLetter    if no such key exists
     */
    public void undoLetter(String letter) throws NoSuchCryptogramLetter {
        Cryptogram c = playerGameMapping.get(currentPlayer);

        // TODO #8: create boolean method in if condition, named checkLetter()
        if(letter.isEmpty() || letter.isBlank() || letter.equals(" ")){
            return; // if the input letter is empty, we do not do anything
        }

        // This has the same methods as the NumberCryptogram
        if(c instanceof LetterCryptogram){

            // TODO #9: create void method, named undoLetterCryptoLetter()
            char key = letter.charAt(0);                         // we convert the string into a char

            if(inputFromUserLetter.containsKey(key)){            // if there is such crypto key
                Character before = inputFromUserLetter.get(key); // we get that user input character

                if(before == null){                              // if the character was null, meaning that the player
                    return;                                      // has not yet entered anything, we just return
                }

                inputFromUserLetter.put(key, null);              // else we reset the mapping to null

                updatePhrase(before, key, c);
            }
            else{
                throw new NoSuchCryptogramLetter("No such letter was mapped");
            }
        }
        else if(c instanceof NumberCryptogram){

            // TODO #9: create void method, named undoNumberCryptoLetter()
            boolean found = false;
            int key = -1;
            try{
                key = Integer.parseInt(letter);
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
            if(inputFromUserNumber.containsKey(key)){
                Character before = inputFromUserNumber.get(key);

                if(before == null){
                    return;
                }

                inputFromUserNumber.put(key, null);

            }
            else{
                throw new NoSuchCryptogramLetter("No such letter was mapped");
            }
        }


    }

    /**
     * This method will load a saved cryptogame
     * For now we need to agree on a text format, so this is a
     * TODO: implement this method correctly
     * @param name
     * @return
     */
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

    /**
     * This method will load sentences from a text file.
     * For now we just generate hard-coded sentences.
     * TODO #11: create text file with sentences, create a sanitizer and load them
     *  into the sentences arraylist
     * @return                              result of loading the sentences from file
     * @throws NoSentencesToGenerateFrom    there were no text files
     */
    public boolean loadSentences() throws NoSentencesToGenerateFrom {
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

        if(sentences == null){
            throw new NoSentencesToGenerateFrom("No sentences");
        }

        sentences.add("This is a very long sentence that will be displayed so we will see what is going to happen");
        sentences.add("He was so preoccupied with whether or not he could that he failed to stop to consider if he should");
        sentences.add("Pair your designer cowboy hat with scuba gear for a memorable occasion");
        sentences.add("For oil spots on the floor, nothing beats parking a motorbike in the lounge");
        sentences.add("He said he was not there yesterday however many people saw him there");

        //String chosenphrase = ArrayList.get(rand.nextInt(ArrayList.size()));

        return false;
    }


    /**
     * This method will check if the answer is correct.
     * This is usually called, when isEverythingMapped() return true.
     * This will go through the inputMappings, and checks every mapping
     * from the user mapping and compares them with the cryptogram alphabet.
     *
     * @see Game#isEverythingMappedLetter()
     *
     * @return      if the mapping are the same
     */
    public boolean checkAnswer(){
        Cryptogram c = playerGameMapping.get(currentPlayer);

        if(c instanceof LetterCryptogram){
            LetterCryptogram letter = (LetterCryptogram) c;
            for(Map.Entry<Character, Character> entry : inputFromUserLetter.entrySet()){
                if(letter.getPlainLetter(entry.getKey()) != entry.getValue()){
                    // if even one of the mapping are incorrect, the whole answer is incorrect
                    return false;
                }
            }
        }
        else if(c instanceof NumberCryptogram){
            NumberCryptogram number = (NumberCryptogram) c;
            for(Map.Entry<Integer, Character> entry : inputFromUserNumber.entrySet()){
                if(number.getPlainLetter(entry.getKey()) != entry.getValue()){
                    // if even one of the mapping are incorrect, the whole answer is incorrect
                    return false;
                }
            }
        }

        return false;
    }

    /**
     * This method generates a new cryptogram. Retrieves a random sentence, from the sentences arraylist.
     * Encrypts that sentence and creates the user input mapping.
     * @param player
     * @param type
     * @throws NoSentencesToGenerateFrom
     * @throws NoSuchGameType
     */
    private void generateCryptogram(Player player, String type) throws NoSentencesToGenerateFrom, NoSuchGameType {
        Random rnd = new Random();
        String solution = "";
        Cryptogram cryptogram;

        // TODO #10: create void method for this if-else, named initNewSentence()
        if(sentences.size()>0){
             solution = sentences.get(rnd.nextInt(sentences.size()));
        }
        else{
            throw new NoSentencesToGenerateFrom("There are no sentences, exiting...");
        }

        // TODO #10: create void method for this if-else, named initNewCryptogram()
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

        // TODO #10: create void method for this if-else, named initNewInputMap()
        if(cryptogram instanceof LetterCryptogram){
            inputFromUserLetter = new HashMap<>();
            for(char c : cryptogram.getPhrase().toCharArray()){
                if(c != ' ' && c != '!'){
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

    /**
     * This method check, if the user input mapping contains a null, else it means
     * that the user tried every input field in a NumberCryptogram game
     * @return      if the mapping does not contain any null values
     */
    private boolean isEverythingMappedNumber(){
        for(Map.Entry<Integer, Character> entry : inputFromUserNumber.entrySet()){

            Character temp = entry.getValue();

            if(temp != null && temp.charValue() == '!'){
                continue;
            }

            if(temp == null){
                return false;
            }
        }

        return true;
    }

    /**
     * This method check, if the user input mapping contains a null, else it means
     * that the user tried every input field in a LetterCryptogram game
     * @return      if the mapping does not contain any null values
     */
    private boolean isEverythingMappedLetter(){
        for(Map.Entry<Character, Character> entry : inputFromUserLetter.entrySet()){

            Character temp = entry.getValue();

            if(temp != null && temp.charValue() == '!'){
                continue;
            }

            if(temp == null){
                return false;
            }
        }

        return true;
    }


    private boolean isLetterUsedLetter(char letter){
        return inputFromUserLetter.containsKey(letter);
    }

    private boolean isLetterUsedNumber(int number){
        return inputFromUserNumber.containsKey(number);
    }

    /**
     * This method clears the user input mapping, making every mapping value
     * to null. Indicating that the user reset the game they played.
     */
    public void resetMappings(){
        Cryptogram c = playerGameMapping.get(currentPlayer);

        if(c instanceof LetterCryptogram){
            for(Map.Entry<Character, Character> entry : inputFromUserLetter.entrySet()){
                inputFromUserLetter.put(entry.getKey(), null);
            }

            for(Word words : gameGui.getWordHolder().getWords()){
                words.clearLetterLabel();   // We delete any input from the GUI
            }
        }
        else if(c instanceof NumberCryptogram){
            for(Map.Entry<Integer, Character> entry : inputFromUserNumber.entrySet()){
                inputFromUserNumber.put(entry.getKey(), null);
            }

            for(Word words : gameGui.getWordHolder().getWords()){
                words.clearLetterLabel();
            }
        }
    }

    /**
     * This method will disable every input field for the GUI.
     * Usually called when the game ends.
     */
    public void lockFields(){
        for(Word words : gameGui.getWordHolder().getWords()){
            words.lockFields();
        }
    }

    public char getHint(){
        if(currentPhrase.equals("")){
            System.out.println("Empty phrase");
        }else{
            char[] myArray = currentPhrase.toCharArray();
            ArrayList<Character> phrasechars = new ArrayList<>();
            HashSet set = new HashSet();
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

    public ArrayList<String> getSentences() {
        return sentences;
    }

    public void setSentences(ArrayList<String> sentences) {
        this.sentences = sentences;
    }

    public void showSolution(){
        if(currentPlayer!=null){
            /*Cryptogram c = playerGameMapping.get(currentPlayer);
            show(c.phrase);*/
        }
    }

}
