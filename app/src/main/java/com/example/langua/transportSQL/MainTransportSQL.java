package com.example.langua.transportSQL;

import android.content.Context;
import android.util.Log;

import com.example.langua.cards.Card;
import com.example.langua.declaration.consts;

import java.util.ArrayList;

import static com.example.langua.ApproachManager.ApproachManager.PHRASEOLOGY_INDEX;
import static com.example.langua.ApproachManager.ApproachManager.VOCABULARY_INDEX;

public class MainTransportSQL {
    public static TransportSQLInterface getTransport(int index, Context context){
        switch (index){
            case VOCABULARY_INDEX:
                return new TransportSQLVocabulary(context);
            case PHRASEOLOGY_INDEX:
                return new TransportSQLPhraseology(context);
            default:
                Log.i("ERROR", "MainTransportSQL - Got wrong chapter index: " + index);
                return null;
        }
    }

}
