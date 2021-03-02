package cryptogram;

public  class Cryptogram {
protected String phrase;
protected String solution;
protected String cryptogramAlphabet;

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

    public String getCryptogramAlphabet() {
        return cryptogramAlphabet;
    }

    public void setCryptogramAlphabet(String cryptogramAlphabet) {
        this.cryptogramAlphabet = cryptogramAlphabet;
    }
}
