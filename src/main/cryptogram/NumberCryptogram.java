package main.cryptogram;

import java.util.*;

public class NumberCryptogram extends Cryptogram{

    public final static String TYPE = "NUMBER";

    /*
    Key: number
    Value: original letter

        5  -> a
        13 -> b
        21 -> c
    ...
    */
    private HashMap<Integer, Character> numberCryptogramAlphabet;
    private ArrayList<Integer> solutionInIntegerFormat;


    public NumberCryptogram(String solution) {
        solution = solution.toLowerCase();
        createAlphabet();
        super.setSolution(solution);
        super.setCryptogramAlphabet(new HashMap<Object, Object>(numberCryptogramAlphabet));
        super.setPhrase(solutionToPhrase(solution));
    }


    public char getPlainLetter(int cryptoValue){
        Character plain = numberCryptogramAlphabet.get(cryptoValue);
        return plain.charValue();
    }

    private void createAlphabet(){
        numberCryptogramAlphabet = new HashMap<>();

        Integer temp[] = new Integer[26];
        int flag = 0;
        for(int i = 1; i <= 26; ++i){
            temp[flag++] = i;
        }

        temp = shuffle(temp);

        int originalLetter = 'a';
        for(int i = 0; i < temp.length; ++i){
            numberCryptogramAlphabet.put(temp[i], Character.valueOf((char)originalLetter));
            originalLetter++;
        }
    }

    private Integer[] shuffle(Integer[] array){
        List<Integer> temp = Arrays.asList(array);
        Collections.shuffle(temp);
        temp.toArray(array);

        return array;
    }

    public HashMap<Integer, Character> getNumberCryptogramAlphabet() {
        return numberCryptogramAlphabet;
    }

    private String solutionToPhrase(String solution){

        solutionInIntegerFormat = new ArrayList<>();

        StringBuilder builder = new StringBuilder();

        solution = solution.toLowerCase();

        for(int i = 0; i < solution.length(); ++i){
            if(solution.charAt(i) != ' '){
                int result = getEncyptedLetter(solution.charAt(i));
                solutionInIntegerFormat.add(result);
                if(result != 0){
                    builder.append(result);
                    builder.append('!');

                }
            }
            else{
                builder.append(' ');
            }

        }

        return builder.toString();
    }

    private int getEncyptedLetter(char plainLetter){
        for (Map.Entry<Integer, Character> entry : numberCryptogramAlphabet.entrySet()) {
            if (Objects.equals(Character.valueOf(plainLetter), entry.getValue())) {
                return entry.getKey();
            }
        }

        return 0;
    }

    public ArrayList<Integer> getSolutionInIntegerFormat() {
        return solutionInIntegerFormat;
    }

    private void setPhrase()
    {
        super.setPhrase("");
        for (int i = 0; i < solutionInIntegerFormat.size(); i++) {

            if(solutionInIntegerFormat.get(i)==0)
            {
                phrase.concat("#");
            }
            else
            {
                phrase.concat(solutionInIntegerFormat.get(i).toString()+"!");
            }

        }
    }

}

