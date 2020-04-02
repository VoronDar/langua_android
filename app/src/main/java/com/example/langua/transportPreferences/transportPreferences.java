package com.example.langua.transportPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.langua.ApproachManager.ApproachManager;
import com.example.langua.declaration.consts;

public class transportPreferences {
    public static String AVAILABLE_ID = "card_id_pref";                                             // indicate on ID you can use for creating new cards


    public static int getImagePriority(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.IMAGE_PRIORITY_PREFERENCE, consts.PRIORITY_MAX);
    }
    public static void setImagePriority(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.IMAGE_PRIORITY_PREFERENCE, value).apply();
    }

    public static int getTranslatePriority(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.TRANSLATE_PRIORITY_PREFERENCE, consts.PRIORITY_MAX);
    }
    public static void setTranslatePriority(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.TRANSLATE_PRIORITY_PREFERENCE, value).apply();
    }

    public static int getMeaningPriority(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.MEANING_PRIORITY_PREFERENCE, consts.PRIORITY_MAX);
    }
    public static void setMeaningPriority(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.MEANING_PRIORITY_PREFERENCE, value).apply();
    }
    public static int getMeaningLanguage(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.MEANING_LANGUAGE_PREFERENCE, consts.LANGUAGE_BOTH);
    }
    public static void setMeaningLanguage(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.MEANING_LANGUAGE_PREFERENCE, value).apply();
    }


    public static int getSynomymAvailability(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.SYNONYM_AVAILABILITY_PREFERENCE, consts.AVAILABILITY_ON);
    }
    public static void setSynomymAvailability(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.SYNONYM_AVAILABILITY_PREFERENCE, value).apply();
    }


    public static int getAntonymAvailability(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.ANTONYM_AVAILABILITY_PREFERENCE, consts.AVAILABILITY_ON);
    }
    public static void setAntonymAvailability(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.ANTONYM_AVAILABILITY_PREFERENCE, value).apply();
    }



    public static int getExampleAvailability(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.EXAMPLE_AVAILABILITY_PREFERENCE, consts.AVAILABILITY_ON);
    }
    public static void setExampleAvailability(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.EXAMPLE_AVAILABILITY_PREFERENCE, value).apply();
    }
    public static int getExampleLanguage(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.EXAMPLE_LANGUAGE_PREFERENCE, consts.LANGUAGE_BOTH);
    }
    public static void setExampleLanguage(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.EXAMPLE_LANGUAGE_PREFERENCE, value).apply();
    }


    public static boolean isStarted(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(consts.IS_GAME_STARTED, false);
    }
    public static void setStarted(Context context, boolean value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(consts.IS_GAME_STARTED, value).apply();
    }


    public static int getStartDay(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.FIRST_DAY, 0);
    }
    public static void setStartDay(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.FIRST_DAY, value).apply();
    }


    public static int getExtraDayCount(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.EXTRA_DAYS, 0);
    }
    public static void setExtraDayCount(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.EXTRA_DAYS, value).apply();
    }


    public static int getTodayStudyLoaded(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.IS_TODAY_STUDY_CARD_LOADED, 0);
    }
    public static void setTodayStudyLoaded(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.IS_TODAY_STUDY_CARD_LOADED, value).apply();
    }

    public static int getTodayRepeatLoaded(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.IS_TODAY_REPEAT_CARD_LOADED, 0);
    }
    public static void setTodayRepeatLoaded(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.IS_TODAY_REPEAT_CARD_LOADED, value).apply();
    }

    public static int getTodayStudyReturned(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.IS_TODAY_CARD_STUDY_RETURNED, 0);
    }
    public static void setTodayStudyReturned(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.IS_TODAY_CARD_STUDY_RETURNED, value).apply();
    }

    public static int getTodayRepeatReturned(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.IS_TODAY_CARD_REPEAT_RETURNED, 0);
    }
    public static void setTodayRepeatReturned(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.IS_TODAY_CARD_REPEAT_RETURNED, value).apply();
    }


    public static int getLastDayComming(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.LAST_DAT, 0);
    }
    public static void setLastDayComming(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.LAST_DAT, value).apply();
    }

    public static int getTodayLeftVocabularyStudyCardQuantityPreference(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.TODAY_LEFT_VOCABULARY_STUDY_CARD_QUANTITY_PREFERENCE, 0);
    }
    public static void setTodayLeftVocabularyStudyCardQuantityPreference(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.TODAY_LEFT_VOCABULARY_STUDY_CARD_QUANTITY_PREFERENCE, value).apply();
    }

    public static int getTodayLeftVocabularyRepeatCardQuantityPreference(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.TODAY_LEFT_VOCABULARY_REPEAT_CARD_QUANTITY_PREFERENCE, 0);
    }
    public static void setTodayLeftVocabularyRepeatCardQuantityPreference(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.TODAY_LEFT_VOCABULARY_REPEAT_CARD_QUANTITY_PREFERENCE, value).apply();
    }

    public static int getTodayLeftVocabularyPracticeCardQuantityPreference(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.TODAY_LEFT_VOCABULARY_PRACTICE_CARD_QUANTITY_PREFERENCE, 0);
    }
    public static void setTodayLeftVocabularyPracticeCardQuantityPreference(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.TODAY_LEFT_VOCABULARY_PRACTICE_CARD_QUANTITY_PREFERENCE, value).apply();
    }

    public static int getPaceVocabulary(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.PACE_VOCABULARY_PREFERENCE, 5);
    }
    public static void setPaceVocabulary(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.PACE_VOCABULARY_PREFERENCE, value).apply();
    }


    public static int getTodayPhraseologyStudyLoaded(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.IS_TODAY_PHRASEOLOGY_STUDY_CARD_LOADED, 0);
    }
    public static void setTodayPhraseologyStudyLoaded(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.IS_TODAY_PHRASEOLOGY_STUDY_CARD_LOADED, value).apply();
    }

    public static int getTodayPhraseologyRepeatLoaded(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.IS_TODAY_PHRASEOLOGY_REPEAT_CARD_LOADED, 0);
    }
    public static void setTodayPhraseologyRepeatLoaded(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.IS_TODAY_PHRASEOLOGY_REPEAT_CARD_LOADED, value).apply();
    }

    public static int getTodayPhraseologyStudyReturned(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.IS_TODAY_PHRASEOLOGY_CARD_STUDY_RETURNED, 0);
    }
    public static void setTodayPhraseologyStudyReturned(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.IS_TODAY_PHRASEOLOGY_CARD_STUDY_RETURNED, value).apply();
    }

    public static int getTodayPhraseologyRepeatReturned(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.IS_TODAY_PHRASEOLOGY_CARD_REPEAT_RETURNED, 0);
    }
    public static void setTodayPhraseologyRepeatReturned(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.IS_TODAY_PHRASEOLOGY_CARD_REPEAT_RETURNED, value).apply();
    }

    public static int getTodayLeftPhraseologyStudyCardQuantityPreference(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.TODAY_LEFT_PHRASEOLOGY_STUDY_CARD_QUANTITY_PREFERENCE, 0);
    }
    public static void setTodayLeftPhraseologyStudyCardQuantityPreference(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.TODAY_LEFT_PHRASEOLOGY_STUDY_CARD_QUANTITY_PREFERENCE, value).apply();
    }

    public static int getTodayLeftPhraseologyRepeatCardQuantityPreference(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.TODAY_LEFT_PHRASEOLOGY_REPEAT_CARD_QUANTITY_PREFERENCE, 0);
    }
    public static void setTodayLeftPhraseologyRepeatCardQuantityPreference(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.TODAY_LEFT_PHRASEOLOGY_REPEAT_CARD_QUANTITY_PREFERENCE, value).apply();
    }

    public static int getTodayLeftPhraseologyPracticeCardQuantityPreference(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.TODAY_LEFT_PHRASEOLOGY_PRACTICE_CARD_QUANTITY_PREFERENCE, 0);
    }
    public static void setTodayLeftPhraseologyPracticeCardQuantityPreference(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.TODAY_LEFT_PHRASEOLOGY_PRACTICE_CARD_QUANTITY_PREFERENCE, value).apply();
    }

    public static int getPacePhraseology(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(consts.PACE_PHRASEOLOGY_PREFERENCE, 5);
    }
    public static void setPacePhraseology(Context context, int value){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(consts.PACE_PHRASEOLOGY_PREFERENCE, value).apply();
    }


    public static int getVocabularyApproach(Context context){                                       // return audio or read approach
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(ApproachManager.VOC_APPROACH_PREFERENCE, ApproachManager.VOC_READ_APPROACH);
    }
    public static void setVocabularyApproach(Context context, int value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(ApproachManager.VOC_APPROACH_PREFERENCE, value).apply();
    }

    public static int getCriticalValue(Context context, int section){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(ApproachManager.getSectionString(section)+consts.CRITICAL_PREFERENCE, 15);
    }
    public static void setCriticalValue(Context context, int section, int value){                      // set value, when new cards don't add
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(ApproachManager.getSectionString(section)+consts.CRITICAL_PREFERENCE, value).apply();
    }

    public static int getAvailableId(Context context, int section){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(ApproachManager.getSectionString(section)+AVAILABLE_ID, 0);
    }
    public static void setAvailableId(Context context, int section, int value){                     // set value, when new cards don't add
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(ApproachManager.getSectionString(section)+AVAILABLE_ID, value).apply();
    }

}
