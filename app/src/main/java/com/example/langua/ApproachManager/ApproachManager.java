package com.example.langua.ApproachManager;

import android.content.Context;

import com.example.langua.R;

public class ApproachManager {

    public static final int VOC_AUDIO_APPROACH          = 0;                                        // used for emphasize the vocabulary approach
    public static final int VOC_READ_APPROACH           = 1;
    public static final String VOC_APPROACH_PREFERENCE  = "vocapproachpreference";

    public static final int VOCABULARY_INDEX                    = 0;                                                    // индексы разделов
    public static final int PHRASEOLOGY_INDEX                   = 1;

    public static final String getSectionString(int index){
        switch (index){
            case VOCABULARY_INDEX:
                return "voc";
            case PHRASEOLOGY_INDEX:
                return "phr";
            default:
                return null;
        }
    }


}
