package com.example.langua.transportSQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.example.langua.DBHelpers.DBHelper;
import com.example.langua.DBHelpers.DBHelperTodayRepeatVocabulary;
import com.example.langua.DBHelpers.DBHelperTodayVocabulary;
import com.example.langua.DBHelpers.DBHelperVocabularyPractice;
import com.example.langua.cards.Card;
import com.example.langua.cards.VocabularyCard;
import com.example.langua.contracts.VocabularyContract;
import com.example.langua.declaration.consts;
import com.example.langua.ruler.CardRepeatManage;
import com.example.langua.ruler.Ruler;
import com.example.langua.transportPreferences.transportPreferences;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.langua.ApproachManager.ApproachManager.VOCABULARY_INDEX;
import static com.example.langua.contracts.Entry.COLUMN_REPEAT_LEVEL;
import static com.example.langua.contracts.VocabularyContract.VocabularyEntry.GET_RANDOM_FROM_STUDY;
import static com.example.langua.contracts.VocabularyContract.VocabularyEntry.PRACTICE_TABLE_NAME;
import static com.example.langua.contracts.VocabularyContract.VocabularyEntry.TABLE_NAME;
import static com.example.langua.contracts.VocabularyContract.VocabularyEntry.TODAY_REPEAT_TABLE_NAME;
import static com.example.langua.contracts.VocabularyContract.VocabularyEntry.TODAY_TABLE_STUDY_NAME;


/*

this class maintain link between databases and the Ruler class

DON'T USE THESE FUNCTIONS IN THE UPPER INTERFACE - USE MainTransportSQL.getTransport in Ruler

And! FUNCTIONS DOCS READ IN INTERFACE

 */

public final class TransportSQLVocabulary extends TransportSQL implements TransportSQLInterface {


    //////// OPEN TABLES ///////////////////////////////////////////////////////////////////////////
    TransportSQLVocabulary(Context context) {
            super(context);
            createTables();
        }
    private void createTables(){
            dbHelper = new DBHelper(context);
            database = dbHelper.getWritableDatabase();

            dbHelperRepeat      = new DBHelperTodayRepeatVocabulary(context);
            databaseRepeat      = dbHelperRepeat.getWritableDatabase();
            dbHelperStudy       = new DBHelperTodayVocabulary(context);
            databaseStudy       = dbHelperStudy.getWritableDatabase();
            dbHelperPractice    = new DBHelperVocabularyPractice(context);
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
        return VOCABULARY_INDEX;
    }




    /////// GET CARD BY CURSOR /////////////////////////////////////////////////////////////////////
    @Override protected Card getCard(Cursor cursor){

            String id = cursor.getString(cursor.getColumnIndex(_ID));
            String dialect = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_SPECIFIC_DIALECT));
            String word = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_WORD));
            String meaning = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_MEANING));
            String meaning_Native = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_MEANING_NATIVE));
            String translate = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_TRANSLATE));
            String transcription = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_TRANSCRIPTION));
            String image = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_IMAGE));
            String tip = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_TIP));
            String synonym = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_SYNONYM));
            String antonym = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_ANTONYM));
            String mem = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_MEM));
            String help = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_HELP));
            String callHelp = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_CALL_HELP));
            String group = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_GROUP));
            String part = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_PART));
            String exampleLearn = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_EXAMPLE));
            String exampleTranslate = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_EXAMPLE_TRANSLATE));
            String readSentence = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_READ_SENTENCE));
            String readSentenceAnswers = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_READ_SENTENCE_ANWSNERS));
            String columnWriteSentence = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_WRITE_SENTENCE));
            String columnWriteSentenceNative = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_WRITE_SENTENCE_NATIVE));
            int repeatLevel = cursor.getInt(cursor.getColumnIndex(COLUMN_REPEAT_LEVEL));
            int practiceLevel = cursor.getInt(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_PRACTICE_LEVEL));
            int repetitionDat = cursor.getInt(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_REPETITION_DAY));
            String similar = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_SIMILAR));
            int mistakes = cursor.getInt(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_MISTAKES));
            return new VocabularyCard(id, dialect, word, meaning, meaning_Native, translate, transcription, image,
                    tip, synonym, antonym, mem, help, callHelp, group, part, exampleLearn, exampleTranslate,
                    readSentence, readSentenceAnswers, columnWriteSentence, columnWriteSentenceNative,
                    repeatLevel, practiceLevel, repetitionDat, similar, mistakes);
        }
    @Override protected Card getCardFromPractise(Cursor cursor){

            String word = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_WORD));
            String id = cursor.getString(cursor.getColumnIndex(_ID));
            String meaning = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_MEANING));
            String meaning_Native = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_MEANING_NATIVE));
            String translate = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_TRANSLATE));
            String image = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_IMAGE));
            String columnWriteSentence = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_WRITE_SENTENCE));
            String columnWriteSentenceNative = cursor.getString(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_WRITE_SENTENCE_NATIVE));
            int repeatLevel = cursor.getInt(cursor.getColumnIndex(COLUMN_REPEAT_LEVEL));
            int practiceLevel = cursor.getInt(cursor.getColumnIndex(VocabularyContract.VocabularyEntry.COLUMN_PRACTICE_LEVEL));
            return new VocabularyCard(id, null, word, meaning, meaning_Native, translate, null, image,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, columnWriteSentence, columnWriteSentenceNative,
                    repeatLevel, practiceLevel, 0, null, 0);
        }

    ////////////////////////////////////////////////////////////////////////////////////////////////


    /////// GET CARD FROM TABLE ////////////////////////////////////////////////////////////////////
    public Card getString(String ID){
        return getCardByID(ID, database, getTableName());
    }
    public Card getStringFromStudy(String ID){
        return getCardByID(ID, databaseStudy, getStudyTableName());
    }
    public Card getStringFromRepeat(String ID){
        return getCardByID(ID, databaseRepeat, getRepeatTableName());
    }
    public Card getRandomCard() {
        return getRandomCard(database, GET_RANDOM_FROM_STUDY);
    }
    public ArrayList<Card> getAllCardsFromCommon(){
        return getAllCards(database, getTableName());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ////// FILL CONTENT VALUES /////////////////////////////////////////////////////////////////////
    @Override protected ContentValues fillContentValues(Card getCard){
            VocabularyCard card = (VocabularyCard) getCard;

            ContentValues contentValues = new ContentValues();
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_ANTONYM, card.getAntonym());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_SIMILAR, card.getSimular());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_SPECIFIC_DIALECT, card.getDialect());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_SYNONYM, card.getSynonym());
            contentValues.put(_ID, card.getId());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_WORD, card.getWord());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_TIP, card.getTip());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_MEANING, card.getMeaning());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_MEANING_NATIVE, card.getMeaningNative());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_TRANSCRIPTION, card.getTranscription());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_TRANSLATE, card.getTranslate());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_IMAGE, card.getImage());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_MEM, card.getMem());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_HELP, card.getHelp());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_CALL_HELP, card.getCallHelp());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_GROUP, card.getGroup());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_PART, card.getPart());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_EXAMPLE, card.getExampleLearn());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_EXAMPLE_TRANSLATE, card.getExampleTranslate());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_READ_SENTENCE, card.getReadSentence());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_READ_SENTENCE_ANWSNERS, card.getReadSentenceAnswners());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_WRITE_SENTENCE, card.getColumnWriteSentence());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_WRITE_SENTENCE_NATIVE, card.getColumnWriteSentenceNative());
            contentValues.put(COLUMN_REPEAT_LEVEL, card.getRepeatlevel());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_PRACTICE_LEVEL, card.getPracticeLevel());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_REPETITION_DAY, card.getRepetitionDat());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_MISTAKES, 0);
            return contentValues;
        }
    @Override protected ContentValues fillContentValuesForPractice(Card getCard){
            VocabularyCard card = (VocabularyCard) getCard;
            ContentValues contentValues = new ContentValues();
            contentValues.put(_ID, card.getId());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_WORD, card.getWord());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_MEANING, card.getMeaning());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_MEANING_NATIVE, card.getMeaningNative());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_TRANSLATE, card.getTranslate());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_IMAGE, card.getImage());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_WRITE_SENTENCE, card.getColumnWriteSentence());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_WRITE_SENTENCE_NATIVE, card.getColumnWriteSentenceNative());
            contentValues.put(COLUMN_REPEAT_LEVEL, card.getRepeatlevel());
            contentValues.put(VocabularyContract.VocabularyEntry.COLUMN_PRACTICE_LEVEL, card.getPracticeLevel());
            return contentValues;
        }
    ////////////////////////////////////////////////////////////////////////////////////////////////



    ////// FILL TODAY TABLES ///////////////////////////////////////////////////////////////////////
    public int fillTodayStudyDatabase(){
        transportPreferences.setTodayStudyLoaded(context, consts.DATABASE_LOAD_LOADING);

        int size = fillTodayStudyDatabase(transportPreferences.getPaceVocabulary(context));

        transportPreferences.setTodayLeftVocabularyStudyCardQuantityPreference(context, size);
        transportPreferences.setTodayStudyLoaded(context, consts.DATABASE_LOAD_COMPLETED);

        return size;

    }
    public int fillTodayRepeatDatabase() {

        transportPreferences.setTodayRepeatLoaded(context, consts.DATABASE_LOAD_LOADING);

        int size = fillTodayRepeatDatabaseIs();

        transportPreferences.setTodayLeftVocabularyRepeatCardQuantityPreference(context, size);
        transportPreferences.setTodayRepeatLoaded(context, consts.DATABASE_LOAD_COMPLETED);

        return size;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////



    ////// LET TODAY TABLES ////////////////////////////////////////////////////////////////////////
    public void returnTodayStudyDatabase(){
            transportPreferences.setTodayStudyReturned(context, consts.DATABASE_LOAD_LOADING);
            letTodayStudyDatabase(true);
            transportPreferences.setTodayStudyReturned(context, consts.DATABASE_LOAD_COMPLETED);
    }
    public void returnTodayRepeatDatabase(){                                                  // return data from the repeatDatabase
            transportPreferences.setTodayRepeatReturned(context, consts.DATABASE_LOAD_LOADING);             // set reload state
            letTodayStudyDatabase(false);
            transportPreferences.setTodayRepeatReturned(context, consts.DATABASE_LOAD_COMPLETED);           // set reload state
        }
    ////////////////////////////////////////////////////////////////////////////////////////////////



    ////// REDUCE CARD COUNT ///////////////////////////////////////////////////////////////////////
    public void reduceLeftStudyCount(){
        transportPreferences.setTodayLeftVocabularyStudyCardQuantityPreference(context,
         transportPreferences.getTodayLeftVocabularyStudyCardQuantityPreference(context) - 1);
    }
    public void reduceLeftRepeatCount(){
        transportPreferences.setTodayLeftVocabularyRepeatCardQuantityPreference(context,
        transportPreferences.getTodayLeftVocabularyRepeatCardQuantityPreference(context) - 1);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    }

