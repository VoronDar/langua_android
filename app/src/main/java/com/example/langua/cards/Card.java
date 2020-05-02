package com.example.langua.cards;

import com.example.langua.declaration.consts;

public class Card {
    protected String id;
    protected int repeatlevel;
    protected int practiceLevel;
    protected int repetitionDat;
    protected String mem;

    public Card(String id, int repeatlevel, int practiceLevel, int repetitionDat) {
        this.id = id;
        this.repeatlevel = repeatlevel;
        this.practiceLevel = practiceLevel;
        this.repetitionDat = repetitionDat;
    }

    protected void setNullProgressCard(){
        this.repeatlevel = consts.NOUN_LEVEL;
        this.practiceLevel = consts.NOUN_LEVEL;
        this.repetitionDat = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRepeatlevel() {
        return repeatlevel;
    }

    public void setRepeatlevel(int repeatlevel) {
        this.repeatlevel = repeatlevel;
    }

    public int getPracticeLevel() {
        return practiceLevel;
    }

    public void setPracticeLevel(int practiceLevel) {
        this.practiceLevel = practiceLevel;
    }

    public int getRepetitionDat() {
        return repetitionDat;
    }

    public void setRepetitionDat(int repetitionDat) {
        this.repetitionDat = repetitionDat;
    }

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
    }


    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", repeatlevel=" + repeatlevel +
                ", practiceLevel=" + practiceLevel +
                ", repetitionDat=" + repetitionDat +
                ", mem='" + mem + '\'' +
                '}';
    }
}
