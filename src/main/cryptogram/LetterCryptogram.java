package main.cryptogram;

import java.util.*;

/**
 * This class represents a letter cryptogram:
 * m -> a, r -> b,  t -> c, ...
 */
public class LetterCryptogram extends Cryptogram {

    /**
     * This used at checking what type of Cryptogram is used.
     * Also, instanceof can be used.
     */
    public final static String TYPE = "LETTER";

    /**
     *   Key: mapped letter     (Character)
     * Value: original letter   (Character)
     *   Eg.: m -> a, r -> b, t -> c, ...
    */
    private HashMap<Character, Character> letterCryptogramAlphabet;

    public LetterCryptogram(String solution) {
        solution = solution.toLowerCase();
        createAlphabet();
        super.setPhrase(solutionToPhrase(solution));
        super.setSolution(solution);
        // Here we upcasting the alphabet, as Object is every classes parent.
        // However, this means we need to downcast, when we get the alphabet.
        super.setCryptogramAlphabet(new HashMap<>(letterCryptogramAlphabet));
    }

    public LetterCryptogram(String solution, HashMap<Character, Character> letterCryptogramAlphabet) {
        this.letterCryptogramAlphabet = letterCryptogramAlphabet;
        solution = solution.toLowerCase();
        super.setPhrase(solutionToPhrase(solution));
        super.setSolution(solution);
        // Here we upcasting the alphabet, as Object is every classes parent.
        // However, this means we need to downcast, when we get the alphabet.
        super.setCryptogramAlphabet(new HashMap<>(letterCryptogramAlphabet));
    }

    //Basic getters

    /**
     *  {@link LetterCryptogram#letterCryptogramAlphabet}
     */
    public HashMap<Character, Character> getLetterCryptogramAlphabet() {
        return letterCryptogramAlphabet;
    }

    /**
     * Returns the plain letter from an encrypted letter, from the alphabet.
     * Or to put in a other way, it returns the value for a encrypted letter.
     * @param cryptoLetter       char key to be searched
     * @return                   value for the encrypted letter
     */
    public char getPlainLetter(char cryptoLetter){
        return letterCryptogramAlphabet.get(cryptoLetter);
    }

    /**
     * This method is responsible for creating a random mapping from letters to letters
     * 1. Initializes the letterCryptogramAlphabet
     * 2. Creates a temp array from the letter a to z (temp[0] = a, temp[1] = b, temp[2] = c, ...)
     * 3. Shuffles that temp array
     * 4. From letter a to z, assigns the random letter, to the original letter, thus creating the alphabet
     *
     * (Character and char are interchangeable to a certain level)
     */
    private void createAlphabet(){
        letterCryptogramAlphabet = new HashMap<>();     // Initialize

        Character[] temp = new Character[26];           // Temp char array
        int flag = 0;
        for(int i = 'a'; i <= 'z'; ++i){
            temp[flag++] = (char) i;
        }

        shuffle(temp);                                  // Shuffle

        int originalLetter = 'a';                       // Create alphabet
        for (Character character : temp) {
            letterCryptogramAlphabet.put(character, (char) originalLetter);
            originalLetter++;
        }
    }

    /**
     * This function shuffles a Character array.
     * @param array    array to be shuffled
     */
    private void shuffle(Character[] array){
        List<Character> temp = Arrays.asList(array);
        Collections.shuffle(temp);
        temp.toArray(array);
    }

    /**
     * This function creates the encrypted phrase from the solution.
     * For this to work, the cryptograms alphabet must be set.
     * Also, this functions formats the phrase in a way, that is
     * understandable for the UI. Making it available to display
     * textfields.
     *
     * @param solution      String to be encrypted
     * @return              encrypted solution, in a UI readable format
     */
    private String solutionToPhrase(String solution){

        solution = solution.toLowerCase();          // We will only use lower cases

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < solution.length(); ++i){
            char currentLetter = solution.charAt(i);
            if(currentLetter != ' '){               // If the current char is not a whitespace
                char encryptedLetter = getEncryptedLetter(currentLetter);
                if(encryptedLetter != '#'){         // If such letter exists
                    builder.append(encryptedLetter);
                    builder.append('!');            // This needed for the UI to understand
                }
                else{
                    builder.append(' ');
                }
            }
            else{
                builder.append(' ');
            }
        }

        return builder.toString();
    }

    /**
     * Returns the encrypted letter from a plain letter, from the alphabet.
     * Or to put in a other way, it returns the key for a plain letter.
     * @param plainLetter       char value to be searched
     * @return                  key value for the plain letter
     */
    private char getEncryptedLetter(char plainLetter){
        for (Map.Entry<Character, Character> entry : letterCryptogramAlphabet.entrySet()) {
            if (Objects.equals(plainLetter, entry.getValue())) {
                return entry.getKey();
            }
        }

        return '#';     // If no such letter exist we return a placeholder char
    }
}
