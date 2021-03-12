package main.cryptogram;

import java.util.HashMap;

/**
 * This class represents a cryptogram, which either can be a:
 *  - letter cryptogram:  m -> a, r -> b,  t -> c, ...
 *  - number cryptogram: 12 -> a, 3 -> b, 26 -> c, ...
 */
public class Cryptogram {

    /**
     *  From original sentence:                    i     like         apples
     *  This is the encrypted sentence in letters: g     kgpf         qmmkpl
     *  This is the encrypted sentence in numbers: 5   12 5 7 2   11 24 24 12 7 8
     */
    protected String phrase;

    /**
     *  This is the original sentence: i like apples
     */
    protected String solution;

    /**
     * So it would work like:   cryptogramAlphabet.get(m) -> a, cryptogramAlphabet.get(r) -> b,  cryptogramAlphabet.get(t) -> c, ...
     * Or                   :  cryptogramAlphabet.get(12) -> a, cryptogramAlphabet.get(3) -> b, cryptogramAlphabet.get(26) -> c, ...
     * This is the mapping, we are using Object keys, and Object values because, the keys can either be a Character
     * (Letter cryptograms), or Integer (Number cryptograms) and it is good for later, if someone would lik to implement
     * new type of cryptograms. However the value will always be Character.
    */
    protected HashMap<Object, Object> cryptogramAlphabet;

    // Basic getters/setters

    /**
     *  {@link Cryptogram#phrase}
     */
    public String getPhrase() {
        return phrase;
    }

    /**
     *  {@link Cryptogram#solution}
     */
    public String getSolution() {
        return solution;
    }

    /**
     *  {@link Cryptogram#cryptogramAlphabet}
     */
    public HashMap<Object, Object> getCryptogramAlphabet() {
        return cryptogramAlphabet;
    }

    /**
     *  {@link Cryptogram#solution}
     */
    public void setSolution(String solution) {
        this.solution = solution;
    }

    /**
     *  {@link Cryptogram#phrase}
     */
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    /**
     *  {@link Cryptogram#cryptogramAlphabet}
     */
    public void setCryptogramAlphabet(HashMap<Object, Object> cryptogramAlphabet) {
        this.cryptogramAlphabet = cryptogramAlphabet;
    }
}
