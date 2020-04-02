package com.example.langua.cards;

import androidx.annotation.Nullable;

public class VocabularyCard extends Card{
    private String dialect;
    private String word;
    private String meaning;
    private String meaningNative;
    private String translate;
    private String transcription;
    private String image;
    private String tip;
    private String synonym;
    private String antonym;
    private String help;
    private String callHelp;
    private String group;
    private String part;
    private String exampleLearn;
    private String exampleTranslate;
    private String readSentence;
    private String readSentenceAnswners;
    private String columnWriteSentence;
    private String columnWriteSentenceNative;
    private String simular;

    public VocabularyCard(String id,
                          String dialect, String word, String meaning, String meaningNative,
                          String translate, String transcription, String image, String tip,
                          String synonym, String antonym, String help, String callHelp, String group,
                          String part, String exampleLearn, String exampleTranslate, String readSentence,
                          String readSentenceAnswners, String columnWriteSentence, String columnWriteSentenceNative,
                          String simular) {
        super(id, 0, 0, 0);
        this.dialect = dialect;
        this.word = word;
        this.meaning = meaning;
        this.meaningNative = meaningNative;
        this.translate = translate;
        this.transcription = transcription;
        this.image = image;
        this.tip = tip;
        this.synonym = synonym;
        this.antonym = antonym;
        this.help = help;
        this.callHelp = callHelp;
        this.group = group;
        this.part = part;
        this.exampleLearn = exampleLearn;
        this.exampleTranslate = exampleTranslate;
        this.readSentence = readSentence;
        this.readSentenceAnswners = readSentenceAnswners;
        this.columnWriteSentence = columnWriteSentence;
        this.columnWriteSentenceNative = columnWriteSentenceNative;
        this.simular = simular;
        this.mem = null;

        setNullProgressCard();
    }

    public VocabularyCard(String id, String dialect, String word,
                          String meaning, String meaningNativve, String translate, String transcription,
                          String image, String tip, String synonym, String antonym, String mem,
                          String help, String callHelp, String group, String part,
                          String exampleLearn, String exampleTranslate, String readSentence,
                          String readSentenceAnswners, String columnWriteSentence,
                          String columnWriteSentenceNative, int repeatlevel, int practiceLevel,
                          int repetitionDat, String simular) {
        super(id, repeatlevel, practiceLevel, repetitionDat);
        this.dialect = dialect;
        this.word = word;
        this.meaning = meaning;
        this.meaningNative = meaningNativve;
        this.translate = translate;
        this.transcription = transcription;
        this.image = image;
        this.tip = tip;
        this.synonym = synonym;
        this.antonym = antonym;
        this.mem = mem;
        this.help = help;
        this.callHelp = callHelp;
        this.group = group;
        this.part = part;
        this.exampleLearn = exampleLearn;
        this.exampleTranslate = exampleTranslate;
        this.readSentence = readSentence;
        this.readSentenceAnswners = readSentenceAnswners;
        this.columnWriteSentence = columnWriteSentence;
        this.columnWriteSentenceNative = columnWriteSentenceNative;
        this.simular = simular;
    }

    // DELETE IT!!!!
    public VocabularyCard(String id, String dialect, String word,
                          String meaning, String meaningNativve, String translate, String transcription,
                          String image, String tip, String synonym, String antonym, String mem,
                          String help, String callHelp, String group, String part,
                          String exampleLearn, String exampleTranslate, String readSentence,
                          String readSentenceAnswners, String columnWriteSentence,
                          String columnWriteSentenceNative, int repeatlevel, int practiceLevel,
                          int repetitionDat, String simular, int mistakes) {
        super(id, repeatlevel, practiceLevel, repetitionDat);
        this.dialect = dialect;
        this.word = word;
        this.meaning = meaning;
        this.meaningNative = meaningNativve;
        this.translate = translate;
        this.transcription = transcription;
        this.image = image;
        this.tip = tip;
        this.synonym = synonym;
        this.antonym = antonym;
        this.mem = mem;
        this.help = help;
        this.callHelp = callHelp;
        this.group = group;
        this.part = part;
        this.exampleLearn = exampleLearn;
        this.exampleTranslate = exampleTranslate;
        this.readSentence = readSentence;
        this.readSentenceAnswners = readSentenceAnswners;
        this.columnWriteSentence = columnWriteSentence;
        this.columnWriteSentenceNative = columnWriteSentenceNative;
        this.simular = simular;
    }
/*
    public VocabularyCard(String id, String dialect, String word, String meaning,
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
        this.simular = null;
        this.synonym = null;
        this.antonym = null;
        setNullProgressCard();
    }

 */

    public VocabularyCard(String id) {
        super(id, 0, 0, 0);
        this.dialect = null;
        this.word = null;
        this.meaning = null;
        this.meaningNative = null;
        this.translate = null;
        this.transcription = null;
        this.image = null;
        this.tip = null;
        this.synonym = null;
        this.antonym = null;
        this.mem = null;
        this.help = null;
        this.callHelp = null;
        this.group = null;
        this.part = null;
        this.exampleLearn = null;
        this.exampleTranslate = null;
        this.readSentence = null;
        this.readSentenceAnswners = null;
        this.columnWriteSentence = null;
        this.columnWriteSentenceNative = null;
        this.simular = null;
        setNullProgressCard();
    }

    public VocabularyCard() {
        super(null, 0, 0, 0);
        this.dialect = null;
        this.word = null;
        this.meaning = null;
        this.meaningNative = null;
        this.translate = null;
        this.transcription = null;
        this.image = null;
        this.tip = null;
        this.synonym = null;
        this.antonym = null;
        this.mem = null;
        this.help = null;
        this.callHelp = null;
        this.group = null;
        this.part = null;
        this.exampleLearn = null;
        this.exampleTranslate = null;
        this.readSentence = null;
        this.readSentenceAnswners = null;
        this.columnWriteSentence = null;
        this.columnWriteSentenceNative = null;
        this.simular = null;
        setNullProgressCard();
    }

    public String getMeaningNative() {
        return meaningNative;
    }

    public void setMeaningNative(String meaningNative) {
        this.meaningNative = meaningNative;
    }

    public String getExampleLearn() {
        return exampleLearn;
    }

    public void setExampleLearn(String exampleLearn) {
        this.exampleLearn = exampleLearn;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
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

    public String getExampleTranslate() {
        return exampleTranslate;
    }

    public void setExampleTranslate(String exampleTranslate) {
        this.exampleTranslate = exampleTranslate;
    }

    public String getReadSentence() {
        return readSentence;
    }

    public void setReadSentence(String readSentence) {
        this.readSentence = readSentence;
    }

    public String getReadSentenceAnswners() {
        return readSentenceAnswners;
    }

    public void setReadSentenceAnswners(String readSentenceAnswners) {
        this.readSentenceAnswners = readSentenceAnswners;
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

    public String getSimular() {
        return simular;
    }

    public void setSimular(String simular) {
        this.simular = simular;
    }

    public boolean checkFull(){                                                                     // return true if the cards is ready to use
            return !(word == null || columnWriteSentenceNative == null || columnWriteSentence == null ||
                    id == null || (translate == null && meaning == null && meaningNative == null));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        VocabularyCard another = (VocabularyCard)obj;
        if (another.getColumnWriteSentence() == null)
            return getColumnWriteSentence() == null;
        else if (another.getColumnWriteSentenceNative() == null)
            return getColumnWriteSentenceNative() == null;
        else if (another.getMeaning() == null)
            return getMeaning() == null;
        else if (another.getMeaningNative() == null)
            return getMeaningNative() == null;
        else if (another.getTranslate() == null)
            return getTranslate() == null;
        else if (another.getTranscription() == null)
            return getTranscription() == null;
        else if (another.getAntonym() == null)
            return getAntonym() == null;
        else if (another.getExampleLearn() == null)
            return getExampleLearn() == null;
        else if (another.getExampleTranslate() == null)
            return getExampleTranslate() == null;
        else if (another.getHelp() == null)
            return getHelp() == null;
        else if (another.getGroup() == null)
            return getGroup() == null;
        else if (another.getPart() == null)
            return getPart() == null;
        return  getColumnWriteSentence().equals(another.getColumnWriteSentence()) &&
                getColumnWriteSentenceNative().equals(another.getColumnWriteSentenceNative()) &&
                getMeaning().equals(another.getMeaning()) &&
                getMeaningNative().equals(another.getMeaningNative()) &&
                getTranslate().equals(another.getTranslate()) &&
                getTranscription().equals(another.getTranscription()) &&
                getAntonym().equals(another.getAntonym()) &&
                getSynonym().equals(another.getSynonym()) &&
                getExampleLearn().equals(another.getExampleLearn()) &&
                getExampleTranslate().equals(another.getExampleTranslate()) &&
                getHelp().equals(another.getHelp()) &&
                getGroup().equals(another.getGroup()) &&
                getPart().equals(another.getPart()) &&
                getWord().equals(another.getWord());
    }
}