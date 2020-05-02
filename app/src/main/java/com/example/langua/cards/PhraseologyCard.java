package com.example.langua.cards;

import com.example.langua.declaration.consts;

public class PhraseologyCard extends Card{
    private String dialect;
    private String word;
    private String meaning;
    private String meaningNative;
    private String translate;
    private String tip;
    private String help;
    private String callHelp;
    private String group;
    private String part;
    private String exampleLearn;
    private String exampleTranslate;
    private String columnWriteSentence;
    private String columnWriteSentenceNative;

    public PhraseologyCard(String id, String dialect, String word, String meaning,
                           String meaningNative, String translate, String tip, String help, String callHelp,
                           String group, String part, String exampleLearn, String exampleTranslate, String columnWriteSentence,
                           String columnWriteSentenceNative) {
        super(id, 0, 0, 0);
        this.dialect = dialect;
        this.word = word;
        this.meaning = meaning;
        this.meaningNative = meaningNative;
        this.translate = translate;
        this.tip = tip;
        this.help = help;
        this.callHelp = callHelp;
        this.group = group;
        this.part = part;
        this.exampleLearn = exampleLearn;
        this.exampleTranslate = exampleTranslate;
        this.columnWriteSentence = columnWriteSentence;
        this.columnWriteSentenceNative = columnWriteSentenceNative;
        this.mem = null;
        setNullProgressCard();
    }

    public PhraseologyCard(String id, String dialect, String word, String meaning, String meaningNative,
                           String translate, String tip, String mem, String help, String callHelp,
                           String group, String part, String exampleLearn, String exampleTranslate,
                           String columnWriteSentence, String columnWriteSentenceNative, int repeatlevel,
                           int practiceLevel, int repetitionDat) {
        super(id, repeatlevel, practiceLevel, repetitionDat);
        this.dialect = dialect;
        this.word = word;
        this.meaning = meaning;
        this.meaningNative = meaningNative;
        this.translate = translate;
        this.tip = tip;
        this.mem = mem;
        this.help = help;
        this.callHelp = callHelp;
        this.group = group;
        this.part = part;
        this.exampleLearn = exampleLearn;
        this.exampleTranslate = exampleTranslate;
        this.columnWriteSentence = columnWriteSentence;
        this.columnWriteSentenceNative = columnWriteSentenceNative;
    }


    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
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

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }


    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getCallHelp() {
        return callHelp;
    }

    public void setCallHelp(String callHelp) {
        this.callHelp = callHelp;
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

    public String getExampleLearn() {
        return exampleLearn;
    }

    public void setExampleLearn(String exampleLearn) {
        this.exampleLearn = exampleLearn;
    }

    public String getExampleTranslate() {
        return exampleTranslate;
    }

    public void setExampleTranslate(String exampleTranslate) {
        this.exampleTranslate = exampleTranslate;
    }

    public String getColumnWriteSentence() {
        return columnWriteSentence;
    }

    public void setColumnWriteSentence(String columnWriteSentence) {
        this.columnWriteSentence = columnWriteSentence;
    }

    public String getColumnWriteSentenceNative() {
        return columnWriteSentenceNative;
    }

    public void setColumnWriteSentenceNative(String columnWriteSentenceNative) {
        this.columnWriteSentenceNative = columnWriteSentenceNative;
    }


    @Override
    public String toString() {
        return super.toString()+
                "-> PhraseologyCard{" +
                "dialect='" + dialect + '\'' +
                ", word='" + word + '\'' +
                ", meaning='" + meaning + '\'' +
                ", meaningNative='" + meaningNative + '\'' +
                ", translate='" + translate + '\'' +
                ", tip='" + tip + '\'' +
                ", help='" + help + '\'' +
                ", callHelp='" + callHelp + '\'' +
                ", group='" + group + '\'' +
                ", part='" + part + '\'' +
                ", exampleLearn='" + exampleLearn + '\'' +
                ", exampleTranslate='" + exampleTranslate + '\'' +
                ", columnWriteSentence='" + columnWriteSentence + '\'' +
                ", columnWriteSentenceNative='" + columnWriteSentenceNative + '\'' +
                '}';
    }
}