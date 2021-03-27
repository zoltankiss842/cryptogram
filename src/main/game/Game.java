package main.game;

import main.cryptogram.Cryptogram;
import main.cryptogram.LetterCryptogram;
import main.cryptogram.NumberCryptogram;
import main.exceptions.*;
import main.players.Player;
import main.players.Players;
import main.view.*;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Math.round;

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


    private Player currentPlayer;
    private Players allPlayers;

    private ArrayList<String> sentences;
    private String currentPhrase;
    private Frame gameGui;
    private boolean overwrite = false;
    private boolean finished = true;

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
    public Game(String userName) throws NoSuchGameType, NoSentencesToGenerateFrom, InvalidPlayerCreation, NoSaveGameFound, InvalidGameCreation, NoGameBeingPlayed {
        this(new Player(userName), new ArrayList<>(), true);
        playGame();
    }

    /**
     * This constructor is mainly used for unit tests, it creates better accessibility.
     * @param p                          current player
     * @param sentences                  an arraylist of sentences
     * @param createGui                  should the constructor generate a UI for this program
     * @throws NoSuchGameType            if the user chooses a non-existing game type
     * @throws NoSentencesToGenerateFrom if there are no sentences to generate
     */
    public Game(Player p, ArrayList<String> sentences, boolean createGui) throws NoSuchGameType, NoSentencesToGenerateFrom, InvalidPlayerCreation, NoSaveGameFound, InvalidGameCreation {
        if(createGui) gameGui = new Frame(p.getUsername(), this);
        this.sentences = sentences;


        allPlayers = new Players();
        playerGameMapping = new HashMap<>();
        allPlayers.loadStats();
        try {
            currentPlayer = loadPlayer(p);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            currentPlayer=p;
        }

        if(!allPlayers.findPlayer(currentPlayer))
        {
            allPlayers.add(currentPlayer);
        }
        else
        {
            currentPlayer=allPlayers.replacePlayer(currentPlayer.getUsername());
        }

        loadSentences();


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

    public boolean isFinished() {
        return finished;
    }

    /**
     * This method right now only returns the parameter, however
     * this should search a load the players from the text file.
     * As soon as we agree on a text file formatting, it can be done.
     *
     * For now this is a:
     * @param p     player with parameters
     * @return      found player
     */
    public Player loadPlayer(Player p) throws InvalidPlayerCreation, NoSaveGameFound, InvalidGameCreation {
        if(allPlayers.findPlayer(p)){
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
    public void playGame() throws NoSentencesToGenerateFrom, NoSuchGameType, NoGameBeingPlayed {

        String playerWantsToOverwrite = "";

        if(gameGui != null){
            NewGameTypeOptionPane pane = new NewGameTypeOptionPane(gameGui.getFrame());
            playerWantsToOverwrite = pane.getResult();
        }
        else{
            Scanner sc = new Scanner(System.in);

            System.out.println("What game would you like to play?");
            System.out.println("Letter cyptogram: Y / Number cryptogram: N");
            String answer = sc.nextLine();

            if(answer.equals("Y")){
                playerWantsToOverwrite = "LETTER";
            }
            else{
                playerWantsToOverwrite = "NUMBER";
            }
        }


        try{

            if(playerWantsToOverwrite.equals(LetterCryptogram.TYPE)) {
                resetGameDetails();
                generateCryptogram(currentPlayer, LetterCryptogram.TYPE);
                finished = false;
            }
            else if(playerWantsToOverwrite.equals(NumberCryptogram.TYPE)) {
                resetGameDetails();
                generateCryptogram(currentPlayer, NumberCryptogram.TYPE);
                finished = false;
            }
            else{
                throw new NoGameBeingPlayed();
            }

            currentPlayer.incrementCryptogramsPlayed();

            if (gameGui != null) {
                gameGui.displayNewGame(playerGameMapping.get(currentPlayer));
            }
        }
        catch (NoSentencesToGenerateFrom e) {
            throw new NoSentencesToGenerateFrom("There are no sentences to choose from!");
        }
        catch(NoSuchGameType e){
            throw new NoSuchGameType("Player did not chose a game!");
        }
        catch(NoGameBeingPlayed e){
            if (gameGui != null) {
                if(gameGui.getWordHolder() == null){
                    gameGui.displayEmptyScreen();
                }
            }
            throw new NoGameBeingPlayed("No new game has been selected!");
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
                        updatePhrase(cryptoChar, newChar, playerGameMapping.get(currentPlayer));

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

                    }
                }

            }
            else{

                overwrite = true;

                checkIfPlainAlreadyInUse(cryptoChar, newChar);

                inputFromUserLetter.put(cryptoChar, newChar);

                updatePhrase(cryptoChar, newChar, playerGameMapping.get(currentPlayer));

            }

            if(newLetter != null && !newLetter.isEmpty() && !newLetter.isBlank()){

                if(plainLetterAtCryptoChar == null || plainLetterAtCryptoChar != newChar){
                    currentPlayer.incrementTotalGuesses();  // We increment the total number of guesses

                    LetterCryptogram letter = (LetterCryptogram)c;
                    Character original = (Character) letter.getCryptogramAlphabet().get(cryptoChar);
                    char temp = original;

                    if(temp == newChar){
                        incrementCorrectGuesses();
                    }
                }
            }

            // If the inputFromUserLetter does not contain any null values, that means the player
            // entered a letter to every key, making the game end
            if(isEverythingMappedLetter()) {
                overwrite = false;
                finished = true;
                boolean success = checkAnswer();

                // If we are showing a GUI, we lock/disable the input fields (also greying them out)
                if (gameGui != null) {
                    lockFields();
                }

                // Did the user correctly filled out the fields?
                showGameCompletion(success);

                resetGameDetails();
            }

        }
    }

    private void resetGameDetails() {
        // We reset the mappings
        playerGameMapping.put(currentPlayer, null);
        inputFromUserNumber = null;
        inputFromUserLetter = null;
    }


    public boolean showGameCompletion(boolean success) {
        if (success) {
            currentPlayer.incrementCryptogramsCompleted();
            currentPlayer.incrementCryptogramsSuccessfullyCompleted();

        } else {
            currentPlayer.incrementCryptogramsCompleted();
        }

        if (gameGui != null) {
            GameCompletedMessagePane complete = new GameCompletedMessagePane(gameGui.getFrame(), success);
            return true;
        }return false;
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
                throw new PlainLetterAlreadyInUse("Plain letter already in use for cryptogram: " + entry.getKey());
            }
        }
    }

    private void checkIfPlainAlreadyInUse(int cryptoChar, char newChar) throws PlainLetterAlreadyInUse {
        for (Map.Entry<Integer, Character> entry : inputFromUserNumber.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(newChar) && entry.getKey() != cryptoChar) {
                throw new PlainLetterAlreadyInUse("Plain letter already in use for cryptogram: " + entry.getKey());
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

                    checkIfPlainAlreadyInUse(number, newChar);

                    inputFromUserNumber.put(number, newLetter.charAt(0));
                }
            }
            else{
                Scanner sc = new Scanner(System.in);

                System.out.println("Mapping from " + number + " to " + newLetter + " is already present.");
                System.out.println("Would you like to replace it? Y/N");
                String answer = sc.nextLine();

                if(answer.equals("Y")){

                    inputFromUserNumber.put(number, newLetter.charAt(0));

                }
            }


        }
        else{
            overwrite = true;

            checkIfPlainAlreadyInUse(number, newChar);

            inputFromUserNumber.put(number, newChar);
        }

        if(newLetter != null && !newLetter.isEmpty() && !newLetter.isBlank()){

            if(plainLetterAtCryptoChar == null || plainLetterAtCryptoChar != newChar){
                currentPlayer.incrementTotalGuesses();  // We increment the total number of guesses

                NumberCryptogram letter = (NumberCryptogram) playerGameMapping.get(currentPlayer);
                Character original = (Character) letter.getCryptogramAlphabet().get(number);
                char temp = original;

                if(temp == newChar){
                    incrementCorrectGuesses();
                }
            }
        }

        if(isEverythingMappedNumber()) {
            overwrite = false;
            finished = true;
            boolean success = checkAnswer();

            if (gameGui != null) {
                lockFields();
            }

            showGameCompletion(success);

            resetGameDetails();
        }

    }

    /**
     * This method deletes/resets a letter at a mapping.
     * @param letter                key from mapping
     * @throws NoSuchPlainLetter    if no such key exists
     */
    public void undoLetter(String letter) throws NoSuchCryptogramLetter {
        Cryptogram c = playerGameMapping.get(currentPlayer);

        checkLetter(letter);

        // This has the same methods as the NumberCryptogram
        if(c instanceof LetterCryptogram){

            undoLetterCryptoLetter(letter);

        }
        else if(c instanceof NumberCryptogram){

            undoNumberCryptoLetter(letter);
        }
    }

    public boolean checkLetter(String letter) {
        if(letter.isEmpty() || letter.isBlank() || letter.equals(" ")){
            return true; // letter is indeed empty so we return true
        }
        return false;
    }

    public void undoLetterCryptoLetter(String letter) throws NoSuchCryptogramLetter {
        char key = letter.charAt(0);                         // we convert the string into a char

        if(inputFromUserLetter.containsKey(key)){            // if there is such crypto key
            Character before = inputFromUserLetter.get(key); // we get that user input character

            if(before == null){                              // if the character was null, meaning that the player
                return;                                      // has not yet entered anything, we just return
            }

            inputFromUserLetter.put(key, null);              // else we reset the mapping to null

            updatePhrase(before, key, playerGameMapping.get(currentPlayer));
        }
        else{
            throw new NoSuchCryptogramLetter("No such letter was mapped");
        }
    }

    public void undoNumberCryptoLetter(String letter) throws NoSuchCryptogramLetter {
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

    /**
     * This method will load a saved cryptogame
     * For now we need to agree on a text format, so this is a
     * @param userName
     * @return
     */
    public boolean loadGame(String userName) throws NoSaveGameFound, InvalidPlayerCreation, InvalidGameCreation {
        Scanner mys;
        try {
            FileReader f = new FileReader(userName + ".txt");
            mys = new Scanner(f);

            while(mys.hasNextLine()){
                String name = mys.nextLine();
                if(name.isBlank() || name.isEmpty() || !userName.equals(name)){
                    mys.close();
                    throw new InvalidPlayerCreation("Player save file corrupted or modified for name!");
                }

                String type = mys.nextLine();
                if(type.isBlank() || type.isEmpty()){
                    mys.close();
                    throw new InvalidGameCreation("Game save file corrupted or modified for type!");
                }

                String solution = mys.nextLine();
                if(solution.isBlank() || solution.isEmpty()){
                    mys.close();
                    throw new InvalidGameCreation("Game save file corrupted or modified for solution!");
                }

                String alphabet = mys.nextLine();
                if(alphabet.isBlank() || alphabet.isEmpty()){
                    mys.close();
                    throw new InvalidGameCreation("Game save file corrupted or modified for alphabet!");
                }

                String inputMapping = mys.nextLine();
                if(inputMapping.isBlank() || inputMapping.isEmpty()){
                    mys.close();
                    throw new InvalidGameCreation("Game save file corrupted or modified for inputMapping!");
                }

                String[] tokenisedAlphabet = alphabet.split(";");
                if(tokenisedAlphabet.length != 26){
                    mys.close();
                    throw new InvalidGameCreation("Game save file corrupted or modified for inputMapping!");
                }

                String[] tokenisedInputMapping = inputMapping.split(";");

                if(type.equals(LetterCryptogram.TYPE)){
                    HashMap<Character, Character> alphabetMap = new HashMap<>();
                    HashMap<Character, Character> inputMap = new HashMap<>();


                    for(int i = 0; i < tokenisedAlphabet.length; ++i){
                        String oneMapping = tokenisedAlphabet[i];
                        oneMapping = oneMapping.replaceAll(" ", "");
                        Character key = oneMapping.charAt(0);
                        Character value = oneMapping.charAt(1);

                        alphabetMap.put(key, value);
                    }

                    for(int i = 0; i < tokenisedInputMapping.length; ++i){
                        String oneMapping = tokenisedInputMapping[i];
                        oneMapping = oneMapping.replaceAll(" ", "");
                        Character key = oneMapping.charAt(0);
                        Character value = oneMapping.charAt(1);

                        if(value == '#'){
                            inputMap.put(key, null);
                        }
                        else{
                            inputMap.put(key, value);
                        }

                    }

                    Cryptogram c = new LetterCryptogram(solution, alphabetMap);
                    currentPlayer=allPlayers.replacePlayer(userName);
                    playerGameMapping.put(currentPlayer, c);
                    inputFromUserLetter = inputMap;

                    System.out.println("File reading was successful");
                    if (gameGui!=null) {
                        gameGui.displayNewGame(playerGameMapping.get(currentPlayer));

                        for (int i = 0; i < tokenisedInputMapping.length; ++i) {
                            String oneMapping = tokenisedInputMapping[i];
                            oneMapping = oneMapping.replaceAll(" ", "");
                            Character key = oneMapping.charAt(0);
                            Character value = oneMapping.charAt(1);

                            if (value == '#') {
                                for (Word word : gameGui.getWordHolder().getWords()) {
                                    word.updateLetterLabel(String.valueOf(key), null);
                                }
                            } else {
                                for (Word word : gameGui.getWordHolder().getWords()) {
                                    word.updateLetterLabel(String.valueOf(key), String.valueOf(value));
                                }
                            }

                        }
                    }

                    mys.close();
                    return true;
                }
                else if(type.equals(NumberCryptogram.TYPE)){
                    HashMap<Integer, Character> alphabetMap = new HashMap<>();
                    HashMap<Integer, Character> inputMap = new HashMap<>();


                    for(int i = 0; i < tokenisedAlphabet.length; ++i){
                        String oneMapping = tokenisedAlphabet[i];
                        String[] tempToken = oneMapping.split(" ");
                        Integer key = Integer.parseInt(tempToken[0]);
                        Character value = tempToken[1].charAt(0);

                        alphabetMap.put(key, value);
                    }

                    for(int i = 0; i < tokenisedInputMapping.length; ++i){
                        String oneMapping = tokenisedInputMapping[i];
                        String[] tempToken = oneMapping.split(" ");
                        Integer key = Integer.parseInt(tempToken[0]);
                        Character value = tempToken[1].charAt(0);

                        if(value == '#'){
                            inputMap.put(key, null);
                        }
                        else{
                            inputMap.put(key, value);
                        }
                    }

                    Cryptogram c = new NumberCryptogram(solution, alphabetMap);
                    currentPlayer=allPlayers.replacePlayer(userName);
                    playerGameMapping.put(currentPlayer, c);
                    inputFromUserNumber = inputMap;

                    System.out.println("File reading was successful");

                    gameGui.displayNewGame(playerGameMapping.get(currentPlayer));

                    for(int i = 0; i < tokenisedInputMapping.length; ++i){
                        String oneMapping = tokenisedInputMapping[i];
                        String[] tempToken = oneMapping.split(" ");
                        Integer key = Integer.parseInt(tempToken[0]);
                        Character value = tempToken[1].charAt(0);

                        if(value == '#'){
                            for(Word word : gameGui.getWordHolder().getWords()){
                                word.updateLetterLabel(String.valueOf(key), null);
                            }
                        }
                        else{
                            for(Word word : gameGui.getWordHolder().getWords()){
                                word.updateLetterLabel(String.valueOf(key), String.valueOf(value));
                            }
                        }

                    }

                    mys.close();
                    return true;
                }
                else{
                    mys.close();
                    throw new InvalidGameCreation("Game save file corrupted or modified for inputMapping!");
                }

            }

            mys.close();
            return false;

        } catch (FileNotFoundException e) {
            throw new NoSaveGameFound("No save game found for player: " + userName);
        }
        catch (InvalidPlayerCreation e){
            throw new InvalidPlayerCreation("Player save file corrupted or modified!");
        }
        catch (InvalidGameCreation e){
            throw new InvalidGameCreation("Game save file corrupted or modified!");
        }

    }

    /**
     * This method will load sentences from a text file.
     * @return                              result of loading the sentences from file
     * @throws NoSentencesToGenerateFrom    there were no text files
     */
    public boolean loadSentences() throws NoSentencesToGenerateFrom {
        File f = new File("phrases.txt");
        Scanner mys = null;
        try{
            mys = new Scanner(f);

            while(mys.hasNextLine()){
                sentences.add(mys.nextLine());
            }

            if(sentences == null){
                throw new NoSentencesToGenerateFrom("No sentences");
            }

            mys.close();
        }catch(FileNotFoundException e){
            if(mys != null){
                mys.close();
            }
            e.printStackTrace();
            return false;
        }

        return true;
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

        return true;
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
        String letter = "";
        Cryptogram cryptogram;

        solution = initNewSentence(rnd);

        cryptogram = initNewCryptogram(type, solution);

        if(playerGameMapping == null){
            playerGameMapping = new HashMap<>();
        }
        else{
            playerGameMapping.clear();
        }

        playerGameMapping.put(player, cryptogram);

        initNewInputMap(cryptogram);
    }

    private String initNewSentence(Random rnd) throws NoSentencesToGenerateFrom {
        String solution;
        if(sentences.size()>0){
             solution = sentences.get(rnd.nextInt(sentences.size()));
        }
        else{
            throw new NoSentencesToGenerateFrom("There are no sentences, exiting...");
        }
        return solution;
    }


    private Cryptogram initNewCryptogram(String type, String solution) throws NoSuchGameType {
        Cryptogram cryptogram;
        if(type.equals(LetterCryptogram.TYPE)){
            cryptogram = new LetterCryptogram(solution);
        }
        else if(type.equals(NumberCryptogram.TYPE)){
            cryptogram = new NumberCryptogram(solution);
        }
        else{
            throw new NoSuchGameType("No such game type, exiting...");
        }
        return cryptogram;
    }


    private void initNewInputMap(Cryptogram cryptogram) {
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
                if(number != 0){
                    inputFromUserNumber.put(number, null);
                }
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


    /*shows hint when number or letter has no mapping or the mapping is wrong*/
    public void getHint() {

        Cryptogram c = playerGameMapping.get(currentPlayer);

        // hint for letter crypto
        if (c instanceof LetterCryptogram) {
            HashMap<Character, Character> letterMap = new HashMap<>();

            HashMap<Character, Character> cryptoMapping = ((LetterCryptogram) c).getLetterCryptogramAlphabet();
            for (Map.Entry<Character, Character> entry : cryptoMapping.entrySet()) {
                letterMap.put(entry.getKey(), entry.getValue());
            }

            // generate random hint
            Object[] keys = inputFromUserLetter.keySet().toArray();
            Object hint = keys[new Random().nextInt(keys.length)];

            for (Map.Entry<Character, Character> entry : cryptoMapping.entrySet()) {
                {

                    // shows if the value mapped to hint is null or wrongly mapped
                    if ((inputFromUserLetter.get(hint) == null) || !(letterMap.get(hint).equals(inputFromUserLetter.get(hint)))) {

                        // output in the terminal
                        System.out.println("Your hint: " + hint + "->" + letterMap.get(hint));
                        overwrite = true;
                        inputFromUserLetter.put((Character) hint, letterMap.get(hint));
                        updatePhrase((Character) hint, letterMap.get(hint), playerGameMapping.get(currentPlayer));

                        // update it in the GUI
                        for (Word word : gameGui.getWordHolder().getWords()) {
                            word.updateLetterLabel(String.valueOf(hint), letterMap.get(hint).toString());
                        }

                        // we don't want to give more than one hint at a time so we just stop
                        break;
                    }
                }
            }

            // see if crypto is done, if so, we show completion message
            if (isEverythingMappedLetter()) {
                boolean success = checkAnswer();
                System.out.println("Cryptogram completed, no more hints to give");

                if (gameGui != null) {
                    lockFields();
                }

                showGameCompletion(success);
            }
        }


        // hint for number crypto
        if (c instanceof NumberCryptogram) {

            HashMap<Integer, Character> numberMap = new HashMap<>();

            HashMap<Integer, Character> numCryptoMapping = ((NumberCryptogram) c).getNumberCryptogramAlphabet();
            for (Map.Entry<Integer, Character> entry : numCryptoMapping.entrySet()) {
                numberMap.put(entry.getKey(), entry.getValue());
            }

            // generate random hint
            Object[] userInputsForNum = inputFromUserNumber.keySet().toArray();
            Object numHint = userInputsForNum[new Random().nextInt(userInputsForNum.length)];

            for (Map.Entry<Integer, Character> entry : inputFromUserNumber.entrySet()) {

                if (inputFromUserNumber.get(numHint) == null || !(numberMap.get(numHint).equals(inputFromUserNumber.get(numHint)))) {

                    // output in the terminal
                    System.out.println("Your hint: " + numHint + "->" + numberMap.get(numHint));
                    overwrite = true;
                    inputFromUserNumber.put((Integer) numHint, numberMap.get(numHint));

                    // update it in the GUI
                    for (Word word : gameGui.getWordHolder().getWords()) {
                        word.updateLetterLabel(String.valueOf(numHint), numberMap.get(numHint).toString());
                    }

                    // we don't want to give more than one hint at a time so we just stop
                    break;
                }
            }

            // see if crypto is done, if so, we show completion message
            if (isEverythingMappedNumber()) {
                if (gameGui != null) {
                    lockFields();
                }
                boolean success = checkAnswer();
                System.out.println("Cryptogram completed, no more hints to give");

                if (gameGui != null) {
                    lockFields();
                }

                showGameCompletion(success);
            }
        }
    }


    /* shows letter or number frequencies of the solution */
    // first value: letter from solution
    // second value: number of occurrences of letters in solution sentence
    // third value: percentage of occurrences of letters for all letters in the solution sentence
    public String viewFrequencies() {
        Cryptogram c = playerGameMapping.get(currentPlayer);

        HashMap<Character, Integer> letterFrequencyMap = new HashMap<>(); // frequency map for the keys and their frequencies

        try{

            char[] values = c.getSolution().toCharArray();
            ArrayList<Character> tokenised = new ArrayList<>(26);

            // here we see frequencies for letter crypto
            if(c instanceof LetterCryptogram) {
            for(int i = 0; i < values.length; i++){
                if( !(values[i] == ('!') || values[i] == (' '))) { // counts !'s and spaces so we take them out
                    tokenised.add(values[i]);

                    if(!letterFrequencyMap.containsKey(values[i])){
                        letterFrequencyMap.put(values[i],1); // if map does not contain the key we put that in with frequency 1
                    }
                    else {
                    letterFrequencyMap.put(values[i], letterFrequencyMap.get(values[i])+1); // otherwise we add plus one to the frequency
                    }
                }
            }

            // here we format it to string to look nicer
            StringBuilder sb = new StringBuilder();
            Iterator<Map.Entry<Character, Integer>> iter = letterFrequencyMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Character, Integer> entry = iter.next();
                int percentage = (int)round((double)entry.getValue() /(double)tokenised.size()*100);
                sb.append('\n');
                sb.append(entry.getKey());
                sb.append(" ");
                sb.append(entry.getValue());
                sb.append(" ");
                sb.append(percentage + "%");
                if (iter.hasNext()) {
                    sb.append(',');
                }
            }
            return sb.toString();
        }

        // here we see frequencies for number crypto
            HashMap<Character, Integer> numFrequencyMap = new HashMap<>();
            char [] numValues = c.getSolution().toCharArray();
            ArrayList<Character> tokenised2 = new ArrayList<>(26);

            if(c instanceof NumberCryptogram) {
           for(int i = 0; i < numValues.length; i++){
               if(!(numValues[i]==('!') || numValues[i]==(' '))) { // counts !'s and spaces so we take them out
                   tokenised2.add(values[i]);
                   if(!numFrequencyMap.containsKey(numValues[i])){
                       numFrequencyMap.put(numValues[i],1); // if map does not contain the key we put that in with frequency 1
                   }else{
                       numFrequencyMap.put(numValues[i], numFrequencyMap.get(numValues[i])+1); // otherwise we add plus one to the frequency
                   }}}

           // here we format it to string to look nicer
           StringBuilder sb2 = new StringBuilder();
           Iterator<Map.Entry<Character, Integer>> iter2 = numFrequencyMap.entrySet().iterator();
           while (iter2.hasNext()) {
               Map.Entry<Character, Integer> entry = iter2.next();
               int percentage = (int)round((double)entry.getValue() /(double)tokenised2.size()*100);
               sb2.append('\n');
               sb2.append(entry.getKey());
               sb2.append(" ");
               sb2.append(entry.getValue());
               sb2.append(" ");
               sb2.append(percentage + "%");
               if (iter2.hasNext()) {
                   sb2.append(',');
               }
           }
           return sb2.toString();

           }}
      catch(Exception E)
      {
          System.out.println("No frequencies to show");
      }
      return "";
    }



    public ArrayList<String> getSentences() {
        return sentences;
    }

    public void setSentences(ArrayList<String> sentences) {
        this.sentences = sentences;
    }

    public void showSolution() {
        if (gameGui != null) {
            if (currentPlayer != null) {
                lockFields();
                ShowSolutionPane pane = new ShowSolutionPane(playerGameMapping.get(currentPlayer).getSolution(), gameGui.getFrame());
            }
        }
    }

    public void showstats(){
        Top10Panel top10 = new Top10Panel(allPlayers.readStats());
    }

    public void savegame() {
        allPlayers.saveStats();
        Cryptogram c=playerGameMapping.get(currentPlayer);
        File myFile = new File(currentPlayer.getUsername()+".txt");

        boolean playerWantsToOverwrite = true;

        if(myFile.exists()){
            if(gameGui != null){
                OverWriteSavePane pane = new OverWriteSavePane(gameGui.getFrame());

                playerWantsToOverwrite = pane.getResult();

            }
            else{
                Scanner sc = new Scanner(System.in);

                System.out.println("Save game found for player: " + currentPlayer.getUsername());
                System.out.println("Would you like to overwrite your save? Y/N");
                String answer = sc.nextLine();

                if(answer.equals("Y")){
                    playerWantsToOverwrite = true;
                }
                else{
                    playerWantsToOverwrite = false;
                }
            }
        }

        if(playerWantsToOverwrite){
            FileWriter myWriter = null;
            try {
                myWriter = new FileWriter(myFile);
                myWriter.write(currentPlayer.getUsername() +"\n");
                if(c instanceof NumberCryptogram)
                {
                    myWriter.write( "NUMBER\n");
                }
                else
                    myWriter.write( "LETTER\n");
                myWriter.write( c.getSolution()+"\n");
                if(c instanceof NumberCryptogram)
                {
                    HashMap<Integer, Character> cryptoMapping=((NumberCryptogram) c).getNumberCryptogramAlphabet();
                    for(Map.Entry<Integer, Character> entry : cryptoMapping.entrySet()){
                        myWriter.write( entry.getKey().toString()+" "+cryptoMapping.get(entry.getKey())+";");
                    }
                    myWriter.write("\n");
                    HashMap<Integer, Character>currentState= inputFromUserNumber;
                    String value="";

                    for(Map.Entry<Integer, Character> entry : currentState.entrySet()){

                        if(currentState.get(entry.getKey())==null)
                        {
                            value="#";
                        }
                        else
                        {
                            value=currentState.get(entry.getKey()).toString();
                        }
                        myWriter.write( entry.getKey().toString()+" "+value+";");

                    }

                }
                else
                {
                    HashMap<Character, Character> cryptoMapping= ((LetterCryptogram) c).getLetterCryptogramAlphabet();
                    for(Map.Entry<Character, Character> entry : cryptoMapping.entrySet()){
                        myWriter.write( entry.getKey().toString()+" "+cryptoMapping.get(entry.getKey()).toString()+";");
                    }
                    myWriter.write("\n");
                    String value="";
                    HashMap<Character, Character> currentState= inputFromUserLetter;
                    for(Map.Entry<Character, Character> entry : currentState.entrySet()){
                        if(currentState.get(entry.getKey())==null)
                        {
                            value="#";
                        }
                        else
                        {
                            value=currentState.get(entry.getKey()).toString();
                        }
                        myWriter.write( entry.getKey().toString()+" "+value+";");
                    }

                }

                myWriter.write("\n");

                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                if(myWriter != null){
                    try{
                        myWriter.close();
                    }
                    catch (Exception ex){}
                }
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

}
