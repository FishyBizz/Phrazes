package edu.msu.cse476.phrazes.phrazes;

public class WordSet {
    private String[] wordArray;
    private boolean isOnline;
    private String wordSetAuthor;

    // Default constructor
    public WordSet() {
        // Initialize variables or add other setup logic as needed
    }

    // Parameterized constructor
    public WordSet(String[] wordArray, boolean isOnline, String wordSetAuthor) {
        this.wordArray = wordArray;
        this.isOnline = isOnline;
        this.wordSetAuthor = wordSetAuthor;
    }

    // Getter methods
    public String[] getWordArray() {
        return wordArray;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getWordSetAuthor() {
        return wordSetAuthor;
    }

    // Setter methods (if needed)
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
