package com.example.langua.declaration;

public class consts {


    // short intro:
    // priority - main component - it defines availability info on card and on test
    // language - the language of info
    // availability - if it is appear only on card - it defines
    // that's for vocabulary
    public static final String IMAGE_PRIORITY_PREFERENCE = "imagePriority";
    public static final String TRANSLATE_PRIORITY_PREFERENCE = "translatePriority";
    public static final String MEANING_PRIORITY_PREFERENCE = "meaningPriority";

    public static final String MEANING_LANGUAGE_PREFERENCE = "meaningLanguage";
    public static final String EXAMPLE_LANGUAGE_PREFERENCE = "exampleLanguage";

    public static final String SYNONYM_AVAILABILITY_PREFERENCE = "synonymAvailability";
    public static final String ANTONYM_AVAILABILITY_PREFERENCE = "antonymAvailability";
    public static final String EXAMPLE_AVAILABILITY_PREFERENCE = "exampleAvailability";



    // preferences too
    public static final String IS_GAME_STARTED = "isstarted";                                                           // true - if application has been opened before
    public static final String FIRST_DAY = "firstday";                                                                  // absolute value of the first day
    public static final String EXTRA_DAYS = "extradays";                                                                // skip days quantity
    public static final String LAST_DAT = "lastday";                                                                    // last open day
    public static final String IS_TODAY_STUDY_CARD_LOADED = "isstudyloaded";                                            // 0 - none, 1 - loading 2- completed (loaded to TODAY tables)
    public static final String IS_TODAY_REPEAT_CARD_LOADED = "isrepeatloaded";                                          // 0 - none, 1 - loading 2- completed (loaded to TODAY tables)
    public static final String IS_TODAY_CARD_STUDY_RETURNED = "isstudyreturned";                                        // 0 - none, 1 - loading 2- completed (loaded from study table to the main)
    public static final String IS_TODAY_CARD_REPEAT_RETURNED = "isrepeatreturned";                                      // 0 - none, 1 - loading 2- completed (loaded from repeat table to the main)
    public static final String IS_TODAY_PHRASEOLOGY_STUDY_CARD_LOADED = "isstudyloaded";                                // 0 - none, 1 - loading 2- completed (loaded to TODAY tables)
    public static final String IS_TODAY_PHRASEOLOGY_REPEAT_CARD_LOADED = "isrepeatloaded";                              // 0 - none, 1 - loading 2- completed (loaded to TODAY tables)
    public static final String IS_TODAY_PHRASEOLOGY_CARD_STUDY_RETURNED = "isstudyreturned";                            // 0 - none, 1 - loading 2- completed (loaded from study table to the main)
    public static final String IS_TODAY_PHRASEOLOGY_CARD_REPEAT_RETURNED = "isrepeatreturned";                          // 0 - none, 1 - loading 2- completed (loaded from repeat table to the main)



    public static final String TODAY_LEFT_VOCABULARY_STUDY_CARD_QUANTITY_PREFERENCE= "todayvocstudycardquantity";
    public static final String TODAY_LEFT_VOCABULARY_REPEAT_CARD_QUANTITY_PREFERENCE= "todayvocrepeatcardquantity";
    public static final String TODAY_LEFT_VOCABULARY_PRACTICE_CARD_QUANTITY_PREFERENCE= "todayvocpracticecardquantity";

    public static final String TODAY_LEFT_PHRASEOLOGY_STUDY_CARD_QUANTITY_PREFERENCE= "todayphrstudycardquantity";
    public static final String TODAY_LEFT_PHRASEOLOGY_REPEAT_CARD_QUANTITY_PREFERENCE= "todayphrrepeatcardquantity";
    public static final String TODAY_LEFT_PHRASEOLOGY_PRACTICE_CARD_QUANTITY_PREFERENCE= "todayphrpracticecardquantity";

    public static final String PACE_VOCABULARY_PREFERENCE= "pacevocabulary";
    public static final String PACE_PHRASEOLOGY_PREFERENCE= "pacephraseology";


    public static final String CRITICAL_PREFERENCE = "critical";


    // values - for preferences


    public static final int PRIORITY_NEVER      = 0;                                                                    // never - never card\never test
    public static final int PRIORITY_NEVER_TEST = 1;                                                                    // always card (if parent is show too)\never test
    public static final int PRIORITY_ALWAYS     = 2;                                                                    // always - always card\is in test if that`s not max value
    public static final int PRIORITY_MAX        = 3;                                                                    // max - always card\always test
   /*   ADVICE:
        it must be at l element with max_priority and at least 1 element with always_priority
        (if there is only 1 max_property element)*/


    public static final int LANGUAGE_NATIVE     = 0;                                                                    // show only native language
    public static final int LANGUAGE_NEW        = 1;                                                                    // show only new language
    public static final int LANGUAGE_BOTH       = 2;                                                                    // show both languages

    public static final int AVAILABILITY_OFF    = 0;                                                                    // never show
    public static final int AVAILABILITY_ON     = 1;                                                                    // always show


    public static final int DATABASE_LOAD_NONE      = 0;                                                                // database - unloaded
    public static final int DATABASE_LOAD_LOADING   = 1;                                                                // uncompleted but started
    public static final int DATABASE_LOAD_COMPLETED = 2;                                                                // completed

    public enum cardState{                                                                                              // enum for check the card state
        stepTrain,                                                                                                      // this step practice
        problemTrain,                                                                                                   // problem practice
        study,                                                                                                          // study (card)
        practice                                                                                                        // free practice
    }


    public enum dayState{                                                                                               // mode
        repeat,
        study,
        practicr
    }



    // intervals
    public static final int TRANSLATE_LEARN     = 1;                                                                    // practice intervals
    public static final int TRANSLATE_NATIVE    = 2;
    public static final int SOUND               = 3;
    public static final int WRITING             = 4;
    public static final int SENTENVE            = 5;
    public static final int START_TEST          = 1;                                                                    // first practice interval
    public static final int NOUN_LEVEL          = -1;                                                                   // first interval
    public static final int END_TEST            = SENTENVE;                                                             // last practice interval

    public static final int P_TRANSLATE_LEARN   = 1;                                                                    // intervals for phraseologyMode
    public static final int P_TRANSLATE_NATIVE  = 2;
    public static final int P_WRITING           = 3;
    public static final int P_SENTENCE          = 4;
    public static final int P_END_TEST          = P_SENTENCE;


    // id's for practice mods
    public static final int SELECT_REPEAT_NEXT_DAY              = 0;                                                    // повторить тесты сегодняяшних карточек, которые будут показаны в следующий раз. Результаты не запихиваются в общую таблицу, можно пропускать карточки
    public static final int SELECT_REPEAT_ALL_COURSE            = 1;                                                    // повторить все тесты всех карточек курса, результаты закидываются назад, (если скипнуто, пропустить)
    public static final int SELECT_PASS_EXAM                    = 2;                                                    // пробежаться по аудированию-написанию-предложениям всего курса. Результаты закидываются назад. Неправильные ответы не повторяются.
    public static final int SELECT_ALL_TODAY_CARDS              = 3;                                                    // повторить все тесты сегодняшних карт.


    // indexes for arraylist card count in practice for the next time tests
    public static final int TRANSLATE_INDEX                     = 0;
    public static final int TRANSLATE_NATIVE_INDEX              = 1;
    public static final int SOUND_INDEX                         = 2;
    public static final int WRITING_INDEX                       = 3;
    public static final int SENTENCE_INDEX                      = 4;

    public static final int P_TRANSLATE_INDEX                   = 0;
    public static final int P_TRANSLATE_NATIVE_INDEX            = 1;
    public static final int P_WRITING_INDEX                     = 2;
    public static final int P_SENTENCE_INDEX                    = 3;







}
