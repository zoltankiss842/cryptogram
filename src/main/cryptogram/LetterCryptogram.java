package main.cryptogram;

import java.util.*;

public class LetterCryptogram extends Cryptogram {

    public final static String TYPE = "LETTER";

    /*
        Key: mapped letter
        Value: original letter

        m -> a
        q -> b
        t -> c
        ...
     */
    private HashMap<Character, Character> letterCryptogramAlphabet;

    public LetterCryptogram(String solution) {
        solution = solution.toLowerCase();
        createAlphabet();
        super.setSolution(solution);
        super.setCryptogramAlphabet(new HashMap<Object, Object>(letterCryptogramAlphabet));
        super.setPhrase(solutionToPhrase(solution));
    }


    public char getPlainLetter(char cryptoLetter){
        Character plain = letterCryptogramAlphabet.get(Character.valueOf(cryptoLetter));
        return plain.charValue();
    }

    private void createAlphabet(){
        letterCryptogramAlphabet = new HashMap<>();

        Character temp[] = new Character[26];
        int flag = 0;
        for(int i = 'a'; i <= 'z'; ++i){
            temp[flag++] = (char) i;
        }

        shuffle(temp);

        int originalLetter = 'a';
        for(int i = 0; i < temp.length; ++i){
            letterCryptogramAlphabet.put(temp[i], (char) originalLetter);
            originalLetter++;
        }
    }

    private void shuffle(Character[] array){
        List<Character> temp = Arrays.asList(array);
        Collections.shuffle(temp);
        temp.toArray(array);

    }

    private String solutionToPhrase(String solution){

        StringBuilder builder = new StringBuilder();

        solution = solution.toLowerCase();

        for(int i = 0; i < solution.length(); ++i){
            if(solution.charAt(i) != ' '){
                char result = getEncyptedLetter(solution.charAt(i));
                if(result != '#'){
                    builder.append(result);
                    builder.append('!');

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

    private char getEncyptedLetter(char plainLetter){
        for (Map.Entry<Character, Character> entry : letterCryptogramAlphabet.entrySet()) {
            if (Objects.equals(Character.valueOf(plainLetter), entry.getValue())) {
                return entry.getKey().charValue();
            }
        }

        return '#';
    }

}
