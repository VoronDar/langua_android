package com.example.langua.cards;

import java.util.Arrays;

import static com.example.langua.declaration.consts.NOUN_LEVEL;

public class VocabularyCard extends Card{
    private String word;
    private String meaning;
    private String meaningNative;
    private String translate;
    private String transcription;
    private String synonym;
    private String antonym;
    private String help;
    private String group;
    private String part;
    private String example;
    private String exampleNative;
    private String train;
    private String trainNative;

    public VocabularyCard(String id, int repeatlevel, int practiceLevel, int repetitionDat, String word, String meaning, String meaningNative, String translate, String transcription, String synonym, String antonym, String help, String group, String part, String example, String exampleNative, String train, String trainNative, String mem) {
        super(id, repeatlevel, practiceLevel, repetitionDat);
        put(word, meaning, meaningNative, translate, transcription, synonym, antonym, help, group,
                part, example, exampleNative, train, trainNative, mem);
    }

    private void put(String word, String meaning, String meaningNative, String translate, String transcription, String synonym, String antonym, String help, String group, String part, String example, String exampleNative, String train, String trainNative, String mem) {
        this.word = word;
        this.meaning = meaning;
        this.meaningNative = meaningNative;
        this.translate = translate;
        this.transcription = transcription;
        this.synonym = synonym;
        this.antonym = antonym;
        this.help = help;
        this.group = group;
        this.part = part;
        this.example = example;
        this.exampleNative = exampleNative;
        this.train = train;
        this.trainNative = trainNative;
        this.mem = mem;
    }

    private void putNone() {
        this.word = null;
        this.meaning = null;
        this.meaningNative = null;
        this.translate = null;
        this.transcription = null;
        this.synonym = null;
        this.antonym = null;
        this.help = null;
        this.group = null;
        this.part = null;
        this.example = null;
        this.exampleNative = null;
        this.train = null;
        this.trainNative = null;
        this.mem = null;
    }


    public VocabularyCard(String id, String word, String meaning, String meaningNative, String translate, String transcription, String synonym, String antonym, String help, String group, String part, String example, String exampleNative, String train, String trainNative, String mem) {
        super(id, 0, 0, 0);
        setNullProgressCard();
        put(word, meaning, meaningNative, translate, transcription, synonym, antonym, help, group,
                part, example, exampleNative, train, trainNative, mem);
    }

    public VocabularyCard() {
        super(null,NOUN_LEVEL,NOUN_LEVEL,0);
        setNullProgressCard();
        putNone();
    }

    public VocabularyCard(String id) {
        super(id,NOUN_LEVEL,NOUN_LEVEL,0);
        setNullProgressCard();
        putNone();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaningNative() {
        return meaningNative;
    }

    public void setMeaningNative(String meaningNative) {
        this.meaningNative = meaningNative;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public String getAntonym() {
        return antonym;
    }

    public void setAntonym(String antonym) {
        this.antonym = antonym;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExampleNative() {
        return exampleNative;
    }

    public void setExampleNative(String exampleNative) {
        this.exampleNative = exampleNative;
    }

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train;
    }

    public String getTrainNative() {
        return trainNative;
    }

    public void setTrainNative(String trainNative) {
        this.trainNative = trainNative;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VocabularyCard that = (VocabularyCard) o;

        if (!word.equals(that.word)) return false;
        if (meaning != null ? !meaning.equals(that.meaning) : that.meaning != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (meaningNative != null ? !meaningNative.equals(that.meaningNative) : that.meaningNative != null)
            return false;
        if (translate != null ? !translate.equals(that.translate) : that.translate != null)
            return false;
        if (transcription != null ? !transcription.equals(that.transcription) : that.transcription != null)
            return false;
        if (synonym != null ? !synonym.equals(that.synonym) : that.synonym != null) return false;
        if (antonym != null ? !antonym.equals(that.antonym) : that.antonym != null) return false;
        if (help != null ? !help.equals(that.help) : that.help != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (part != null ? !part.equals(that.part) : that.part != null) return false;
        if (example != null ? !example.equals(that.example) : that.example != null) return false;
        if (exampleNative != null ? !exampleNative.equals(that.exampleNative) : that.exampleNative != null)
            return false;
        if (train != null ? !train.equals(that.train) : that.train != null) return false;
        if (trainNative != null ? !trainNative.equals(that.trainNative) : that.trainNative != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return true;
    }

    @Override
    public int hashCode() {
        int result = word.hashCode();
        result += id.hashCode();
        result = 31 * result + (meaning != null ? meaning.hashCode() : 0);
        result = 31 * result + (meaningNative != null ? meaningNative.hashCode() : 0);
        result = 31 * result + (translate != null ? translate.hashCode() : 0);
        result = 31 * result + (transcription != null ? transcription.hashCode() : 0);
        result = 31 * result + (synonym != null ? synonym.hashCode() : 0);
        result = 31 * result + (antonym != null ? antonym.hashCode() : 0);
        result = 31 * result + (help != null ? help.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (part != null ? part.hashCode() : 0);
        result = 31 * result + (example != null ? example.hashCode() : 0);
        result = 31 * result + (exampleNative != null ? exampleNative.hashCode() : 0);
        result = 31 * result + (train != null ? train.hashCode() : 0);
        result = 31 * result + (trainNative != null ? trainNative.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VocabularyCard{" +
                "word='" + word + '\'' +
                ", meaning='" + meaning + '\'' +
                ", meaningNative='" + meaningNative + '\'' +
                ", translate='" + translate + '\'' +
                ", transcription='" + transcription + '\'' +
                ", synonym='" + synonym + '\'' +
                ", antonym='" + antonym + '\'' +
                ", help='" + help + '\'' +
                ", group='" + group + '\'' +
                ", part='" + part + '\'' +
                ", example='" + example + '\'' +
                ", exampleNative='" + exampleNative + '\'' +
                ", train='" + train + '\'' +
                ", trainNative='" + trainNative + '\'' +
                ", id='" + id + '\'' +
                ", repeatlevel=" + repeatlevel +
                ", practiceLevel=" + practiceLevel +
                ", repetitionDat=" + repetitionDat +
                ", mem='" + mem + '\'' +
                '}';
    }
}