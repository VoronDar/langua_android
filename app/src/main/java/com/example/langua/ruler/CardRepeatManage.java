package com.example.langua.ruler;

import android.content.ContentValues;
import android.content.Context;

import com.example.langua.cards.Card;
import com.example.langua.declaration.consts;
import com.example.langua.transportSQL.TransportSQLInterface;

import static com.example.langua.ApproachManager.ApproachManager.PHRASEOLOGY_INDEX;
import static com.example.langua.ApproachManager.ApproachManager.VOCABULARY_INDEX;

public class CardRepeatManage {

    private static int setDay(int day, int level) {
        //return day + getLastDay(level) - getLastDay(level-1);                                     // right memorization bow
        return day + level + 1;                                                                     // fast remembering for our research
    }
    public static void upDay(Card card, Context context, int endTest){
        card.setRepetitionDat(setDay(Ruler.getDay(context), card.getRepeatlevel()));
        card.setRepeatlevel(card.getRepeatlevel()+1);
        if ((card.getPracticeLevel() < card.getRepeatlevel()))                                      // if practiceLevel == end test set it is not need to rise it
            card.setPracticeLevel(card.getRepeatlevel());
        if (card.getRepeatlevel() > getEndTest(endTest))
            card.setPracticeLevel(getEndTest(endTest));

    }
    public static void riseFromFall(Card card){                                                     // it's only for extremely repeat mode (day+level+1). We have null level = -1, because if we want
                                                                                                    // to forget the card we should use this to put it in the current day. And in the card view we
                                                                                                    // have to rise it to 0 to put it to tomorrow
        if (card.getRepeatlevel() == consts.NOUN_LEVEL){
            card.setRepeatlevel(consts.NOUN_LEVEL+1);
        }
    }

    int getLastDay(int level) {                                                                     // for interval
        if (level > -1) {
            return getLastDay(level - 1) * 2 + 1;
        }
        return 0;
    }


    public static int getEndTest(int index){
        switch (index){
            case VOCABULARY_INDEX:
                return consts.END_TEST;
            case PHRASEOLOGY_INDEX:
                return consts.P_END_TEST;
            default:
                return 0;
        }
    }                                                   // get level of the last test of some chapter

}
