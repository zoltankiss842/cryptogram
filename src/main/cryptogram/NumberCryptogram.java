package main.cryptogram;

import java.util.*;

/**
 * This class represents a number cryptogram:
 * 12 -> a, 3 -> b, 26 -> c, ...
 */
public class NumberCryptogram extends Cryptogram{

    /**
     * This used at checking what type of Cryptogram is used.
     * Also, instanceof can be used.
     */
    public final static String TYPE = "NUMBER";

    /**
     *   Key: mapped number     (Integer)
     * Value: original letter   (Character)
     *   Eg.: 12 -> a, 3 -> b, 26 -> c, ...
     */
    private HashMap<Integer, Character> numberCryptogramAlphabet;

    /**
     * This is a simple arraylist of integers. This is a other way for 
     * representing the encryptin and is used for easy conversion to a UI object. 
     * Every number is between 0 - 26.
     *      - 0 means a space
     *      - 1 - 26 means a letter.
     * For example: 5 0 12 5 7 2 0 11 24 24 12 7 8
     *
     * Example used from Cryptogram class.
     * @see Cryptogram#phrase
     */
    private ArrayList<Integer> solutionInIntegerFormat;


    public NumberCryptogram(String solution) {
        solution = solution.toLowerCase();
        createAlphabet();
        super.setPhrase(solutionToPhrase(solution));
        super.setSolution(solution);
        // Here we upcasting the alphabet, as Object is every classes parent.
        // However, this means we need to downcast, when we get the alphabet.
        super.setCryptogramAlphabet(new HashMap<Object, Object>(numberCryptogramAlphabet));
    }

    public NumberCryptogram(String solution, HashMap<Integer, Character> numberCryptogramAlphabet) {
        this.numberCryptogramAlphabet = numberCryptogramAlphabet;
        solution = solution.toLowerCase();
        super.setPhrase(solutionToPhrase(solution));
        super.setSolution(solution);
        // Here we upcasting the alphabet, as Object is every classes parent.
        // However, this means we need to downcast, when we get the alphabet.
        super.setCryptogramAlphabet(new HashMap<Object, Object>(numberCryptogramAlphabet));
    }

    // Basic getters

    /**
     * Returns the plain letter from a number, from the alphabet.
     * Or to put in a other way, it returns the value for a number.
     * @param cryptoValue        integer key to be searched
     * @return                   value for the number
     */
    public char getPlainLetter(int cryptoValue){
        Character plain = numberCryptogramAlphabet.get(cryptoValue);
        return plain;
    }

    public ArrayList<Integer> getSolutionInIntegerFormat() {
        return solutionInIntegerFormat;
    }

    public HashMap<Integer, Character> getNumberCryptogramAlphabet() {
        return numberCryptogramAlphabet;
    }

    /**
     * This method is responsible for creating a random mapping from numbers to letters
     * 1. Initializes the numberCryptogramAlphabet
     * 2. Creates a temp array from number 1 to 26 (temp[0] = 1, temp[1] = 2, temp[2] = 3, ...)
     * 3. Shuffles that temp array
     * 4. From number 1 to 26, assigns the random number, to the original letter, thus creating the alphabet
     *
     * (Character and char are interchangeable to a certain level)
     * @return
     */
    public HashMap<Integer, Character> createAlphabet(){
        numberCryptogramAlphabet = new HashMap<>();     // Initialize

        Integer temp[] = new Integer[26];               // Temp char array
        int flag = 0;
        for(int i = 1; i <= 26; ++i){
            temp[flag++] = i;
        }

        temp = shuffle(temp);                           // Shuffle

        int originalLetter = 'a';                       // Create alphabet
        for(int i = 0; i < temp.length; ++i){
            numberCryptogramAlphabet.put(temp[i], (char) originalLetter);
            originalLetter++;
        }

        return numberCryptogramAlphabet;

    }

    /**
     * This function shuffles a Integer array.
     * @param array    array to be shuffled
     */
    private Integer[] shuffle(Integer[] array){
        List<Integer> temp = Arrays.asList(array);
        Collections.shuffle(temp);
        temp.toArray(array);

        return array;
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

        solution = solution.toLowerCase();              // We will only use lower cases
        
        solutionInIntegerFormat = new ArrayList<>();    

        StringBuilder builder = new StringBuilder();
        
        for(int i = 0; i < solution.length(); ++i){
            char currentLetter = solution.charAt(i);
            if(currentLetter != ' '){                   // If the current char is not a whitespace
                int result = getEncryptedLetter(currentLetter);
                solutionInIntegerFormat.add(result);    // We add the result even if its a space
                if(result != 0){                        // If its not a space
                    builder.append(result);
                    builder.append('!');                // This needed for the UI to understand
                }
            }
            else{
                builder.append(' ');
            }

        }

        return builder.toString();
    }

    /**
     * Returns the encrypted number from a plain letter, from the alphabet.
     * Or to put in a other way, it returns the key for a plain letter.
     * @param plainLetter       char value to be searched
     * @return                  key value for the plain letter
     */
    private int getEncryptedLetter(char plainLetter){
        for (Map.Entry<Integer, Character> entry : numberCryptogramAlphabet.entrySet()) {
            if (Objects.equals(plainLetter, entry.getValue())) {
                return entry.getKey();
            }
        }

        return 0;       // If no such letter exists, or a space has been entered we return a placeholder integer
    }



}

