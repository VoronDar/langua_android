package com.example.langua.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.R;
import com.example.langua.activities.utilities.ActivitiesUtils;
import com.example.langua.activities.utilities.CardActivity;
import com.example.langua.adapters.TextBlockAdapter;
import com.example.langua.cards.PhraseologyCard;
import com.example.langua.declaration.consts;
import com.example.langua.ruler.Ruler;
import com.example.langua.transportPreferences.transportPreferences;
import com.example.langua.units.TextBlock;

import java.util.ArrayList;

import static com.example.langua.activities.utilities.ActivitiesUtils.getExampleColor;
import static com.example.langua.activities.utilities.ActivitiesUtils.getHelpColor;
import static com.example.langua.activities.utilities.ActivitiesUtils.getMeaningColor;
import static com.example.langua.activities.utilities.ActivitiesUtils.getTranslateColor;
import static com.example.langua.activities.utilities.ActivitiesUtils.setGroup;
import static com.example.langua.activities.utilities.ActivitiesUtils.setPart;

public class cardPhraseology extends CardActivity {



    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        soundButton.setVisibility(View.GONE);

        textBlocksList = new ArrayList<>();


        PhraseologyCard card = (PhraseologyCard)abstractCard;


        TextView word = view.findViewById(R.id.word);
        word.setText(card.getWord());

        boolean isMeaning = ((card.getMeaning() != null &&
                transportPreferences.getMeaningLanguage(context) >= consts.LANGUAGE_NEW) ||
                (card.getMeaningNative() != null && (transportPreferences.getMeaningLanguage(context) == consts.LANGUAGE_NATIVE ||
                        transportPreferences.getMeaningLanguage(context) == consts.LANGUAGE_BOTH)));
        boolean isTranslate = ((transportPreferences.getTranslatePriority(context) >= consts.PRIORITY_NEVER_TEST) && card.getTranslate() != null);

        // add priorityMeaning
        String meaning = new String();
        if (isMeaning) {

            if (isMeaning) {
                meaning += getResources().getString(R.string.meaning) + ": ";
                if (card.getMeaning() != null && transportPreferences.getMeaningLanguage(context) >= consts.LANGUAGE_NEW) {
                    meaning += card.getMeaning() + "\n";
                }
                if (card.getMeaningNative() != null && (transportPreferences.getMeaningLanguage(context) == consts.LANGUAGE_BOTH ||
                        transportPreferences.getMeaningLanguage(context) == consts.LANGUAGE_NATIVE)) {
                    meaning += "\n" + card.getMeaningNative();
                }
            }
            textBlocksList.add(new TextBlock(20, meaning, getMeaningColor(view)));
        }


        if (isTranslate) {
            textBlocksList.add(new TextBlock(20, card.getTranslate(), getTranslateColor(view)));
        }

        textBlocksList.add(new TextBlock(20, card.getHelp(), getHelpColor(view), true));


        if (transportPreferences.getExampleAvailability(context) == consts.AVAILABILITY_ON) {
            String example = "";
            if (card.getExampleLearn() != null && transportPreferences.getExampleLanguage(context) >= consts.LANGUAGE_NEW) {
                example += card.getExampleLearn() + "\n";
                if (card.getExampleTranslate() != null && ((transportPreferences.getExampleLanguage(context) == consts.LANGUAGE_NATIVE ||
                        transportPreferences.getExampleLanguage(context) == consts.LANGUAGE_BOTH))) {
                    example += card.getExampleTranslate();
                }
                textBlocksList.add(new TextBlock(17, example, getExampleColor(view), true));
            }
        }

        setGroup(view, card.getGroup());
        setPart(view, card.getPart());

        addMemInCard();

        prepareAdapter();

    }

}
