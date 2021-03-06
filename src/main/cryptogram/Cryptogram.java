package main.cryptogram;

import java.util.HashMap;

public  class Cryptogram {

    protected String phrase;
    protected String solution;
    protected HashMap<Object, Object> cryptogramAlphabet;

    public String getPhrase() {
        return phrase;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public HashMap<Object, Object> getCryptogramAlphabet() {
        return cryptogramAlphabet;
    }

    public void setCryptogramAlphabet(HashMap<Object, Object> cryptogramAlphabet) {
        this.cryptogramAlphabet = cryptogramAlphabet;
    }
}
