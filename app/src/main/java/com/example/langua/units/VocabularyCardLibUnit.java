package com.example.langua.units;

public class VocabularyCardLibUnit{
    private String word;
    private String translate;
    private String ID;
    private int level;

    public VocabularyCardLibUnit(String word, String translate, String ID, int level) {
        this.word = word;
        this.translate = translate;
        this.ID = ID;
        this.level = level;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
