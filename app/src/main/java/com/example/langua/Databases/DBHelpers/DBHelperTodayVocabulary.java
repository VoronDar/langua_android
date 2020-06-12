package com.example.langua.Databases.DBHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.langua.Databases.contracts.VocabularyContract;

public class DBHelperTodayVocabulary extends SQLiteOpenHelper {

    public static final String NAME = "vocabularyTodayRUS.db";
    public static final int VERSION = 3;

    public DBHelperTodayVocabulary(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VocabularyContract.VocabularyEntry.CREATE_VOCABULARY_STUDY_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(VocabularyContract.VocabularyEntry.DROP_TODAY_STUDY_TABLE);
        onCreate(db);
    }
}
