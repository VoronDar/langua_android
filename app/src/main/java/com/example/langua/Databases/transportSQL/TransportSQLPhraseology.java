package com.example.langua.Databases.transportSQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.langua.cards.Card;
import com.example.langua.Databases.DBHelpers.DBHelperPhraseology;
import com.example.langua.Databases.DBHelpers.DBHelperTodayPhraseologyPractise;
import com.example.langua.Databases.DBHelpers.DBHelperTodayPhraseologyRepeat;
import com.example.langua.Databases.DBHelpers.DBHelperTodayPhraseologyStudy;
import com.example.langua.cards.PhraseologyCard;
import com.example.langua.declaration.consts;
import com.example.langua.transportPreferences.transportPreferences;


import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.langua.ApproachManager.ApproachManager.PHRASEOLOGY_INDEX;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_CALL_HELP;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_EXAMPLE;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_EXAMPLE_TRANSLATE;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_GROUP;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_HELP;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_MEANING;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_MEANING_NATIVE;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_MEM;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_PART;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_PRACTICE_LEVEL;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_REPEAT_LEVEL;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_REPETITION_DAY;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_SPECIFIC_DIALECT;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_TIP;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_TRANSLATE;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_WORD;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_WRITE_SENTENCE;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.COLUMN_WRITE_SENTENCE_NATIVE;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.GET_RANDOM_FROM_STUDY;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.PRACTICE_TABLE_NAME;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.TABLE_NAME;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.TODAY_REPEAT_TABLE_NAME;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.TODAY_TABLE_STUDY_NAME;


/*

PHRASEOLOGY DATABASES


this class maintain link between databases and the Ruler class

DON'T USE THESE FUNCTIONS IN THE UPPER INTERFACE - USE RULER! usually, you can use getCardCount anyway!

 */


public class TransportSQLPhraseology extends TransportSQL implements TransportSQLInterface{

    //////// OPEN TABLES ///////////////////////////////////////////////////////////////////////////
    TransportSQLPhraseology(Context context) {
        super(context);
        createTables();
    }
    protected void createTables(){

        dbHelper = new DBHelperPhraseology(context);
        database = dbHelper.getWritableDatabase();
        dbHelperRepeat      = new DBHelperTodayPhraseologyRepeat(context);
        databaseRepeat      = dbHelperRepeat.getWritableDatabase();
        dbHelperStudy       = new DBHelperTodayPhraseologyStudy(context);
        databaseStudy       = dbHelperStudy.getWritableDatabase();
        dbHelperPractice    = new DBHelperTodayPhraseologyPractise(context);
        databasePractice    = dbHelperPractice.getWritableDatabase();

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    //////// GET TABLE NAMES ///////////////////////////////////////////////////////////////////////
    @Override protected String getTableName(){
        return TABLE_NAME;
    }
    @Override protected String getStudyTableName(){
        return TODAY_TABLE_STUDY_NAME;
    }
    @Override protected String getRepeatTableName(){
        return TODAY_REPEAT_TABLE_NAME;
    }
    @Override protected String getPracticeTableName(){
        return PRACTICE_TABLE_NAME;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////



    //////// GET CHAPTER INDEX /////////////////////////////////////////////////////////////////////
    public int getIndex(){
        return PHRASEOLOGY_INDEX;
    }



    /////// GET CARD BY CURSOR /////////////////////////////////////////////////////////////////////
    protected Card getCard(Cursor cursor){
        String id = cursor.getString(cursor.getColumnIndex(_ID));
        String dialect = cursor.getString(cursor.getColumnIndex(COLUMN_SPECIFIC_DIALECT));
        String word = cursor.getString(cursor.getColumnIndex(COLUMN_WORD));
        String meaning = cursor.getString(cursor.getColumnIndex(COLUMN_MEANING));
        String meaning_Native = cursor.getString(cursor.getColumnIndex(COLUMN_MEANING_NATIVE));
        String translate = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSLATE));
        String tip = cursor.getString(cursor.getColumnIndex(COLUMN_TIP));
        String mem = cursor.getString(cursor.getColumnIndex(COLUMN_MEM));
        String help = cursor.getString(cursor.getColumnIndex(COLUMN_HELP));
        String callHelp = cursor.getString(cursor.getColumnIndex(COLUMN_CALL_HELP));
        String group = cursor.getString(cursor.getColumnIndex(COLUMN_GROUP));
        String part = cursor.getString(cursor.getColumnIndex(COLUMN_PART));
        String exampleLearn = cursor.getString(cursor.getColumnIndex(COLUMN_EXAMPLE));
        String exampleTranslate = cursor.getString(cursor.getColumnIndex(COLUMN_EXAMPLE_TRANSLATE));
        String columnWriteSentence = cursor.getString(cursor.getColumnIndex(COLUMN_WRITE_SENTENCE));
        String columnWriteSentenceNative = cursor.getString(cursor.getColumnIndex(COLUMN_WRITE_SENTENCE_NATIVE));
        int repeatLevel = cursor.getInt(cursor.getColumnIndex(COLUMN_REPEAT_LEVEL));
        int practiceLevel = cursor.getInt(cursor.getColumnIndex(COLUMN_PRACTICE_LEVEL));
        int repetitionDat = cursor.getInt(cursor.getColumnIndex(COLUMN_REPETITION_DAY));
        return new PhraseologyCard(id, dialect, word, meaning, meaning_Native, translate,
                tip, mem, help, callHelp, group, part, exampleLearn, exampleTranslate,
                columnWriteSentence, columnWriteSentenceNative, repeatLevel, practiceLevel,
                repetitionDat);
    }
    protected Card getCardFromPractise(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(_ID));
        String word = cursor.getString(cursor.getColumnIndex(COLUMN_WORD));
        String meaning = cursor.getString(cursor.getColumnIndex(COLUMN_MEANING));
        String meaning_Native = cursor.getString(cursor.getColumnIndex(COLUMN_MEANING_NATIVE));
        String translate = cursor.getString(cursor.getColumnIndex(COLUMN_TRANSLATE));
        String columnWriteSentence = cursor.getString(cursor.getColumnIndex(COLUMN_WRITE_SENTENCE));
        String columnWriteSentenceNative = cursor.getString(cursor.getColumnIndex(COLUMN_WRITE_SENTENCE_NATIVE));
        int repeatLevel = cursor.getInt(cursor.getColumnIndex(COLUMN_REPEAT_LEVEL));
        int practiceLevel = cursor.getInt(cursor.getColumnIndex(COLUMN_PRACTICE_LEVEL));
        return new PhraseologyCard(id, null, word, meaning, meaning_Native, translate,
                null, null, null, null, null, null, null, null,
                columnWriteSentence, columnWriteSentenceNative, repeatLevel, practiceLevel, 0);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////



    /////// GET CARD FROM TABLE ////////////////////////////////////////////////////////////////////
    public Card getString(String ID){
        return getCardByID(ID, database, TABLE_NAME);
    }
    public Card getStringFromStudy(String ID){
        return getCardByID(ID, databaseStudy, TODAY_TABLE_STUDY_NAME);
    }
    public Card getStringFromRepeat(String ID){
        return getCardByID(ID, databaseRepeat, TODAY_REPEAT_TABLE_NAME);
    }
    public Card getRandomCard() {
        return getRandomCard(database, GET_RANDOM_FROM_STUDY);
    }
    public ArrayList<Card> getAllCardsFromCommon(){
        return getAllCards(database, TABLE_NAME);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ////// FILL CONTENT VALUES /////////////////////////////////////////////////////////////////////
    @Override protected ContentValues fillContentValues(Card getCard){
        PhraseologyCard card = (PhraseologyCard) getCard;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SPECIFIC_DIALECT, card.getDialect());
        contentValues.put(_ID, card.getId());
        contentValues.put(COLUMN_WORD, card.getWord());
        contentValues.put(COLUMN_TIP, card.getTip());
        contentValues.put(COLUMN_MEANING, card.getMeaning());
        contentValues.put(COLUMN_MEANING_NATIVE, card.getMeaningNative());
        contentValues.put(COLUMN_TRANSLATE, card.getTranslate());
        contentValues.put(COLUMN_MEM, card.getMem());
        contentValues.put(COLUMN_HELP, card.getHelp());
        contentValues.put(COLUMN_CALL_HELP, card.getCallHelp());
        contentValues.put(COLUMN_GROUP, card.getGroup());
        contentValues.put(COLUMN_PART, card.getPart());
        contentValues.put(COLUMN_EXAMPLE, card.getExampleLearn());
        contentValues.put(COLUMN_EXAMPLE_TRANSLATE, card.getExampleTranslate());
        contentValues.put(COLUMN_WRITE_SENTENCE, card.getColumnWriteSentence());
        contentValues.put(COLUMN_WRITE_SENTENCE_NATIVE, card.getColumnWriteSentenceNative());
        contentValues.put(COLUMN_REPEAT_LEVEL, card.getRepeatlevel());
        contentValues.put(COLUMN_PRACTICE_LEVEL, card.getPracticeLevel());
        contentValues.put(COLUMN_REPETITION_DAY, card.getRepetitionDat());
        return contentValues;
    }
    @Override protected ContentValues fillContentValuesForPractice(Card getCard){
        PhraseologyCard card = (PhraseologyCard) getCard;
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, card.getId());
        contentValues.put(COLUMN_WORD, card.getWord());
        contentValues.put(COLUMN_MEANING, card.getMeaning());
        contentValues.put(COLUMN_MEANING_NATIVE, card.getMeaningNative());
        contentValues.put(COLUMN_TRANSLATE, card.getTranslate());
        contentValues.put(COLUMN_WRITE_SENTENCE, card.getColumnWriteSentence());
        contentValues.put(COLUMN_WRITE_SENTENCE_NATIVE, card.getColumnWriteSentenceNative());
        contentValues.put(COLUMN_REPEAT_LEVEL, card.getRepeatlevel());
        contentValues.put(COLUMN_PRACTICE_LEVEL, card.getPracticeLevel());
        return contentValues;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ////// FILL TODAY TABLES ///////////////////////////////////////////////////////////////////////
    public int fillTodayStudyDatabase(){
        transportPreferences.setTodayPhraseologyStudyLoaded(context, consts.DATABASE_LOAD_LOADING);

        int size = fillTodayStudyDatabase(transportPreferences.getPacePhraseology(context));

        transportPreferences.setTodayLeftPhraseologyStudyCardQuantityPreference(context, size);
        transportPreferences.setTodayPhraseologyStudyLoaded(context, consts.DATABASE_LOAD_COMPLETED);

        return size;

    }
    public int fillTodayRepeatDatabase() {

        transportPreferences.setTodayPhraseologyRepeatLoaded(context, consts.DATABASE_LOAD_LOADING);

        int size = fillTodayRepeatDatabaseIs();

        transportPreferences.setTodayLeftPhraseologyRepeatCardQuantityPreference(context, size);
        transportPreferences.setTodayPhraseologyRepeatLoaded(context, consts.DATABASE_LOAD_COMPLETED);

        return size;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ////// LET TODAY TABLES ////////////////////////////////////////////////////////////////////////
    public void returnTodayStudyDatabase(){
        transportPreferences.setTodayPhraseologyStudyReturned(context, consts.DATABASE_LOAD_LOADING);
        letTodayStudyDatabase(true);
        transportPreferences.setTodayPhraseologyStudyReturned(context, consts.DATABASE_LOAD_COMPLETED);
    }
    public void returnTodayRepeatDatabase(){
        transportPreferences.setTodayPhraseologyRepeatReturned(context, consts.DATABASE_LOAD_LOADING);
        letTodayStudyDatabase(false);
        transportPreferences.setTodayPhraseologyRepeatReturned(context, consts.DATABASE_LOAD_COMPLETED);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ////// REDUCE CARD COUNT ///////////////////////////////////////////////////////////////////////
    public void reduceLeftStudyCount(){
        transportPreferences.setTodayLeftPhraseologyStudyCardQuantityPreference(context,
        transportPreferences.getTodayLeftPhraseologyStudyCardQuantityPreference(context) - 1);
    }
    public void reduceLeftRepeatCount(){
        transportPreferences.setTodayLeftPhraseologyRepeatCardQuantityPreference(context,
       transportPreferences.getTodayLeftPhraseologyRepeatCardQuantityPreference(context) - 1);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

}
