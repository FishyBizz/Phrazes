package edu.msu.cse476.phrazes.phrazes;

public class WordSet {
    private String[] wordArray;
    private boolean isOnline;
    private String wordSetAuthor;

    public WordSet() {
        this.wordArray = new String[]{};
        this.isOnline = false;
        this.wordSetAuthor = "";
    }

    public WordSet(String[] wordArray, boolean isOnline, String wordSetAuthor) {
        this.wordArray = wordArray;
        this.isOnline = isOnline;
        this.wordSetAuthor = wordSetAuthor;
    }

    // getters/setters
    public String[] getWordArray() {
        return wordArray;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getWordSetAuthor() {
        return wordSetAuthor;
    }

    public void setWordArray(String[] wordArray) {
        this.wordArray = wordArray;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setWordSetAuthor(String wordSetAuthor) {
        this.wordSetAuthor = wordSetAuthor;
    }
}
