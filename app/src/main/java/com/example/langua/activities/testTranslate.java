package com.example.langua.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.langua.ApproachManager.ApproachManager;
import com.example.langua.R;
import com.example.langua.activities.utilities.ActivitiesUtils;
import com.example.langua.activities.utilities.Test;
import com.example.langua.activities.utilities.TrasportActivities;
import com.example.langua.cards.PhraseologyCard;
import com.example.langua.cards.VocabularyCard;
import com.example.langua.declaration.consts;
import com.example.langua.transportPreferences.transportPreferences;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.langua.ApproachManager.ApproachManager.VOCABULARY_INDEX;
import static com.example.langua.activities.utilities.ActivitiesUtils.getPreparedSoundManager;

public class testTranslate extends Test {


    Button firstAnswer;
    Button secondAnswer ;
    Button thirdAnswer;
    Button fourthAnswer;
    String right;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_test_translate, null);
        return inflater.inflate(R.layout.activity_test_translate, container, false);

    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView word = view.findViewById(R.id.word);
        if (transportPreferences.getVocabularyApproach(getContext()) ==
                ApproachManager.VOC_AUDIO_APPROACH){
            soundManager = getPreparedSoundManager(mainPlain.activity, card.getId());
            isRealSound = soundManager.isSoundExist();
            word.setVisibility(View.GONE);
            view.findViewById(R.id.soundPlay).setVisibility(View.VISIBLE);
            view.findViewById(R.id.soundPlay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playWord(v);
                }
            });
            playWord(null);
        }
        else {
            word.setVisibility(View.VISIBLE);
            if (index == VOCABULARY_INDEX)
                word.setText(((VocabularyCard) card).getWord());
            else
                word.setText(((PhraseologyCard) card).getWord());
        }

        boolean isTranslate = true;
        boolean isNativeMeaning = true;

        ArrayList<String> answer = new ArrayList<String>(4);


        if (transportPreferences.getTranslatePriority(getContext()) > consts.PRIORITY_NEVER &&
        ((VocabularyCard)card).getTranslate() != null){
            answer.add(((VocabularyCard) card).getTranslate());
        }
        else if(((VocabularyCard)card).getMeaning() != null ||
                ((VocabularyCard)card).getMeaningNative() != null){
            isTranslate = false;
            if (((VocabularyCard)card).getMeaningNative() != null && (transportPreferences.getMeaningLanguage(getContext()) == consts.LANGUAGE_BOTH ||
                    transportPreferences.getMeaningLanguage(getContext()) == consts.LANGUAGE_NATIVE)) {
                answer.add(((VocabularyCard) card).getMeaningNative());
                isNativeMeaning = true;
            }
            else if (((VocabularyCard)card).getMeaning() != null &&
                    transportPreferences.getMeaningLanguage(getContext()) >= consts.LANGUAGE_NEW){
                isNativeMeaning = false;
                answer.add(((VocabularyCard) card).getMeaning());
            }
            else if (((VocabularyCard)card).getMeaningNative() != null){
                answer.add(((VocabularyCard) card).getMeaningNative());
                isNativeMeaning = true;
            }
            else if (((VocabularyCard)card).getMeaning() != null){
                isNativeMeaning = false;
                answer.add(((VocabularyCard) card).getMeaning());
            }
        }
        else{
            ActivitiesUtils.createErrorWindow(mainPlain.activity, new ActivitiesUtils.DoWhileError() {
                @Override
                public void action() {
                    goNext(null);
                }
            }, card.getId(), getResources().getString(R.string.no_translate_meaning));
            return;
        }
        right = answer.get(0);


        if (index == VOCABULARY_INDEX) {
            for (int i = 0; i < 3; i++) {
                VocabularyCard newCard;
                String translate;
                do {
                    newCard = ((VocabularyCard)ruler.getDatabase().getRandomCard());
                    if (isTranslate)
                        translate = newCard.getTranslate();
                    else if (isNativeMeaning)
                        translate = newCard.getMeaningNative();
                    else
                        translate = (newCard).getMeaning();

                }
                while (translate == null || answer.indexOf(translate) != -1 ||
                        newCard.getWord().equals(((VocabularyCard)card).getWord()));
                answer.add(translate);
            }
        }
        else{
            answer.add(((PhraseologyCard)card).getTranslate());
            for (int i = 0; i < 3; i++) {
                String translate;
                do {
                    translate = ((PhraseologyCard)ruler.getDatabase().getRandomCard()).getTranslate();;
                }
                while (answer.indexOf(translate) != -1);
                answer.add(translate);
            }
        }
        ruler.closeDatabase();

        Collections.shuffle(answer);
        firstAnswer = view.findViewById(R.id.firstAnswer);
        firstAnswer.setText(answer.get(0));
        firstAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerClick(v);
            }
        });
        secondAnswer = view.findViewById(R.id.secondAnswer);
        secondAnswer.setText(answer.get(1));
        secondAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerClick(v);
            }
        });
        thirdAnswer = view.findViewById(R.id.thirdAnswer);
        thirdAnswer.setText(answer.get(2));
        thirdAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerClick(v);
            }
        });
        fourthAnswer = view.findViewById(R.id.fourthAnswer);
        fourthAnswer.setText(answer.get(3));
        fourthAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerClick(v);
            }
        });

        if (mainPlain.sizeRatio < mainPlain.triggerRatio){
            if (isTranslate){
            firstAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/(15*multiple));
            secondAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/(15*multiple));
            thirdAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/(15*multiple));
            fourthAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/(15*multiple));}
            else{
                firstAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/(30*multiple));
                secondAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/(30*multiple));
                thirdAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/(30*multiple));
                fourthAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/(30*multiple));
            }
            word.setTextSize((mainPlain.sizeHeight/(20*multiple)));
            ((ImageView)view.findViewById(R.id.container)).setMaxHeight((int)(mainPlain.sizeHeight/2.75f));
            ((ImageView)view.findViewById(R.id.soundPlay)).setMaxHeight(mainPlain.sizeHeight/7);
        }
        else{
            ((ImageView)view.findViewById(R.id.container)).setMaxHeight((int)(mainPlain.sizeHeight/2.45f));
            firstAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/45);
            secondAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/45);
            thirdAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/45);
            fourthAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/45);
        }

    }

    private void answerClick(View view) {

        if (((Button)view).getText().equals(right)){
            ((Button)view).setTextColor(getResources().getColor(R.color.colorWhiteRight));
            isRight = true;
        }
        else{
            isRight = false;
    }       ((Button)view).setTextColor(getResources().getColor(R.color.colorAccent));
            if (firstAnswer.getText().equals(right)){
                firstAnswer.setTextColor(getResources().getColor(R.color.colorWhiteRight));}
            else if (secondAnswer.getText().equals(right))                    {
                      secondAnswer.setTextColor(getResources().getColor(R.color.colorWhiteRight));}
            else if (thirdAnswer.getText().equals(right)){
                thirdAnswer.setTextColor(getResources().getColor(R.color.colorWhiteRight));}
            else
                fourthAnswer.setTextColor(getResources().getColor(R.color.colorWhiteRight));


        fourthAnswer.setClickable(false);
        firstAnswer.setClickable(false);
        secondAnswer.setClickable(false);
        thirdAnswer.setClickable(false);

        buttonNext.setEnabled(true);

        TrasportActivities.putWrongcardsBack(mainPlain.activity, isRight,  practiceState, card, index);

}

}
