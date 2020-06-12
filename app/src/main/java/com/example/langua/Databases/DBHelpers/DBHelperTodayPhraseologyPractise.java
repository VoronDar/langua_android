package com.example.langua.Databases.DBHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.CREATE_PRACTICE_TABLE;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.DROP_PRACTICE_TABLE;

public class DBHelperTodayPhraseologyPractise extends SQLiteOpenHelper {

    public static final String NAME = "phraseologyPractiseRUS.db";
    public static final int VERSION = 4;

    public DBHelperTodayPhraseologyPractise(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRACTICE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_PRACTICE_TABLE);
        onCreate(db);
    }
}
