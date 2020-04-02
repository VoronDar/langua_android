package com.example.langua.DBHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.langua.contracts.VocabularyContract;

public class DBHelperVocabularyPractice extends SQLiteOpenHelper {

    public static final String NAME = "vocabularyPractice.db";
    public static final int VERSION = 6;

    public DBHelperVocabularyPractice(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VocabularyContract.VocabularyEntry.CREATE_PRACTICE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(VocabularyContract.VocabularyEntry.DROP_PRACTICE_TABLE);
        onCreate(db);
    }
}
