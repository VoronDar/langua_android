package com.example.langua.Databases.DBHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.CREATE_VOCABULARY_STUDY_COMMAND;
import static com.example.langua.Databases.contracts.PhraseologyContract.PhraseologyEntry.DROP_TODAY_STUDY_TABLE;

public class DBHelperTodayPhraseologyStudy extends SQLiteOpenHelper {

    public static final String NAME = "phraseologyStudyRUS.db";
    public static final int VERSION = 1;

    public DBHelperTodayPhraseologyStudy(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_VOCABULARY_STUDY_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TODAY_STUDY_TABLE);
        onCreate(db);
    }
}
