package com.example.langua.activities.utilities;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.langua.R;
import com.example.langua.activities.mainPlain;
import com.example.langua.cards.Card;
import com.example.langua.declaration.consts;
import com.example.langua.ruler.Ruler;

public class TrasportActivities {

    public static void goNextCardActivity(Fragment lastFragment, boolean isRight, consts.cardState practiceState, Card card, int sqlIndex){
        Ruler ruler = new Ruler(mainPlain.activity, sqlIndex);
        //Intent intent;
        Fragment fragment;
        if (Ruler.isIsPracticeNow())
            switch (Ruler.getPractiseChapter()){
                case consts.SELECT_REPEAT_NEXT_DAY:
                    Log.i("main", "here");
                    ruler.putCardWhilePracticeForTheNextTest(card, isRight);
                    fragment = ruler.changeActivityWhilePracticeForTheNextTests(lastFragment);
                    break;
                case consts.SELECT_ALL_TODAY_CARDS:
                case consts.SELECT_REPEAT_ALL_COURSE:
                    Log.i("main", "there");
                    ruler.putCardWhilePracticeForTheNextTest(card, isRight);
                    fragment = ruler.changeActivityWhilePracticeForTheAllCards(lastFragment);
                    break;
                default:
                    fragment = ruler.changeActivityWhilePracticeForTheExam(lastFragment);
        }
        else
            fragment = ruler.closeTest(mainPlain.activity, isRight, practiceState, card);
        ruler.closeDatabase();
        mainPlain.activity.slideFragment(fragment);


    }

    public static void skipPracticeCard(Fragment lastFragment, Card card, int sqlIndex){
        Ruler ruler = new Ruler(mainPlain.activity, sqlIndex);
        Fragment fragment;
        switch (Ruler.getPractiseChapter()){
            case consts.SELECT_REPEAT_NEXT_DAY:
                fragment = ruler.skipPracticeNextTest(lastFragment, card.getId());
            break;
            case consts.SELECT_ALL_TODAY_CARDS:
            case consts.SELECT_REPEAT_ALL_COURSE:
                fragment = ruler.skipPracticeAllCards(lastFragment, card.getId());
                break;
            default:
                fragment = null;
        }
        ruler.closeDatabase();
        mainPlain.activity.slideFragment(fragment);
    }

    public static void putWrongcardsBack(AppCompatActivity context, boolean isRight, consts.cardState practiceState, Card card, int sqlIndex){

        Ruler ruler = new Ruler(context, sqlIndex);
        if (!Ruler.isIsPracticeNow()){
            if (!isRight) {
                ruler.putErrorPracticeCardBack(context, practiceState, card);
                ruler.closeDatabase();
            }
        }
        else{
            Button button = context.findViewById(R.id.skip);
            button.setVisibility(View.GONE);
        }
    }

    public static void showSkipButton(AppCompatActivity context){
        if (Ruler.isIsPracticeNow() && Ruler.getPractiseChapter() != consts.SELECT_PASS_EXAM){
            Button button = context.findViewById(R.id.skip);
            button.setVisibility(View.VISIBLE);
            Log.i("main", "show skip button");
        }
    }

}
