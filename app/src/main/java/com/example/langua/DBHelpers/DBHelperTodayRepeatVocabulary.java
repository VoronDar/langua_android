package com.example.langua.DBHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.langua.contracts.VocabularyContract;

public class DBHelperTodayRepeatVocabulary extends SQLiteOpenHelper {

    public static final String NAME = "vocabularyTodayRepeatRUS.db";
    public static final int VERSION = 3;

    public DBHelperTodayRepeatVocabulary(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VocabularyContract.VocabularyEntry.CREATE_VOCABULARY_REPEAT_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(VocabularyContract.VocabularyEntry.DROP_TODAY_REPEAT_TABLE);
        onCreate(db);
    }
}
