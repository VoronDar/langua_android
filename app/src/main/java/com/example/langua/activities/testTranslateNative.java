package com.example.langua.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.langua.ApproachManager.ApproachManager;
import com.example.langua.R;
import com.example.langua.activities.utilities.SoundManager;
import com.example.langua.activities.utilities.Test;
import com.example.langua.activities.utilities.TrasportActivities;
import com.example.langua.cards.PhraseologyCard;
import com.example.langua.cards.VocabularyCard;
import com.example.langua.transportPreferences.transportPreferences;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.langua.ApproachManager.ApproachManager.VOCABULARY_INDEX;

public class testTranslateNative extends Test {
    private int approach;
    private Button firstAnswer;
    private Button secondAnswer;
    private Button thirdAnswer;
    private Button fourthAnswer;
    private ArrayList<String> answer;
    private ArrayList<View> answerButtons;
    private int answerSelectId          = -1;
    private int rightId                 = -1;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getInformation();
        approach = transportPreferences.getVocabularyApproach(getContext());
            if (approach == ApproachManager.VOC_AUDIO_APPROACH){
                inflater.inflate(R.layout.activity_test_translate_sound, null);
                return inflater.inflate(R.layout.activity_test_translate_sound, container, false);
            }
            inflater.inflate(R.layout.activity_test_translate, null);
            return inflater.inflate(R.layout.activity_test_translate, container, false);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (approach == ApproachManager.VOC_AUDIO_APPROACH) {
            answerButtons = new ArrayList<>(4);
            answerButtons.add((view.findViewById(R.id.sound1)));
            answerButtons.add((view.findViewById(R.id.sound2)));
            answerButtons.add((view.findViewById(R.id.sound3)));
            answerButtons.add((view.findViewById(R.id.sound4)));
            buttonNext.setEnabled(true);
        }

        setMeaningAndTranslate();

        answer = new ArrayList<>(4);

        if (approach == ApproachManager.VOC_AUDIO_APPROACH && index == VOCABULARY_INDEX){
            answer.add(card.getId());
            for (int i = 0; i < 3; i++) {
                String translate;
                do {
                    translate = ruler.getDatabase().getRandomCard().getId();
                }
                while (answer.indexOf((translate)) != -1);
                answer.add(translate);
            }
            Collections.shuffle(answer);

            soundManager = new SoundManager(mainPlain.activity);
            isRealSound = soundManager.loadSeveralSound(answer);
            if (!isRealSound)
                answer.clear();

            Collections.shuffle(answer);
            for (int i = 0; i < answer.size(); i++){
                Log.i("main", "a- get " + answer.get(i));
                if (answer.get(i).equals(card.getId())){
                    rightId = i;
                    break;
                }
            }
        }
        if (approach != ApproachManager.VOC_AUDIO_APPROACH || !isRealSound) {
            if (index == VOCABULARY_INDEX) {
                answer.add(((VocabularyCard) card).getWord());
                for (int i = 0; i < 3; i++) {
                    String translate;
                    do {
                        translate = ((VocabularyCard) ruler.getDatabase().getRandomCard()).getWord();
                    }
                    while (answer.indexOf((translate)) != -1);
                    answer.add(translate);
                }
            } else {
                answer.add(((PhraseologyCard) card).getWord());
                for (int i = 0; i < 3; i++) {
                    String translate;
                    do {
                        translate = ((PhraseologyCard) ruler.getDatabase().getRandomCard()).getWord();
                    }
                    while (answer.indexOf((translate)) != -1);
                    answer.add(translate);
                }

                }
            String right = answer.get(0);
            Collections.shuffle(answer);
            for (int i = 0; i < answer.size(); i++){
                if (answer.get(i).equals(right)){
                    rightId = i;
                    break;
            }
            }
        }
        Log.i("main", "rightId - " + rightId);
        ruler.closeDatabase();


        if (approach == ApproachManager.VOC_AUDIO_APPROACH){
            view.findViewById(R.id.check).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    answerClick(v);
                }
            });

            for (View n: answerButtons)
                n.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectSound(v);
                    }
                });
        }
        else {
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
                firstAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/(15*multiple));
                secondAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/(15*multiple));
                thirdAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/(15*multiple));
                fourthAnswer.setTextSize((mainPlain.sizeHeight/2.75f)/(15*multiple));
                ((ImageView)view.findViewById(R.id.container)).setMaxHeight((int)(mainPlain.sizeHeight/2.75f));
            }
        }


        if (mainPlain.sizeRatio < mainPlain.triggerRatio){
            ((ImageView)view.findViewById(R.id.container)).setMaxWidth((int)(mainPlain.sizeWidth*0.85f));
        }

    }

    public void answerClick(View view) {

        playWord(null);
        if (approach == ApproachManager.VOC_AUDIO_APPROACH && index == VOCABULARY_INDEX){
            isRight = (answerSelectId == rightId);
            for (int i = 0; i < answerButtons.size(); i++){
                answerButtons.get(i).setAlpha(0.3f);
                answerButtons.get(i).setEnabled(false);
            }
            answerButtons.get(rightId).setAlpha(1);
            this.view.findViewById(R.id.check).setVisibility(View.GONE);
            this.view.findViewById(R.id.next).setVisibility(View.VISIBLE);
        }
        else {

            String word;
            if (index == VOCABULARY_INDEX)
                word = ((VocabularyCard)card).getWord();
            else
                word = ((PhraseologyCard)card).getWord();
            if (((Button) view).getText().equals(word)) {
                ((Button) view).setTextColor(getResources().getColor(R.color.colorWhiteRight));
                isRight = true;
            } else {
                isRight = false;
            }
            ((Button) view).setTextColor(getResources().getColor(R.color.colorAccent));
            if (firstAnswer.getText().equals(word)) {
                firstAnswer.setTextColor(getResources().getColor(R.color.colorWhiteRight));
            } else if (secondAnswer.getText().equals(word)) {
                secondAnswer.setTextColor(getResources().getColor(R.color.colorWhiteRight));
            } else if (thirdAnswer.getText().equals(word)) {
                thirdAnswer.setTextColor(getResources().getColor(R.color.colorWhiteRight));
            } else
                fourthAnswer.setTextColor(getResources().getColor(R.color.colorWhiteRight));


            fourthAnswer.setClickable(false);
            firstAnswer.setClickable(false);
            secondAnswer.setClickable(false);
            thirdAnswer.setClickable(false);
            buttonNext.setEnabled(true);
        }



        TrasportActivities.putWrongcardsBack(mainPlain.activity, isRight,  practiceState, card, index);


}

    private void selectSound(View view){
        if (view.getId() == R.id.sound1)
            answerSelectId = 0;
        else if (view.getId() == R.id.sound2)
            answerSelectId = 1;
        else if (view.getId() == R.id.sound3)
            answerSelectId = 2;
        else if (view.getId() == R.id.sound4)
            answerSelectId = 3;
        playSound(answerSelectId);
        for (int i = 0; i < answerButtons.size(); i++)
            answerButtons.get(i).setAlpha(1);
        view.setAlpha(0.5f);
        Log.i("main", "id - " + answerSelectId);
    }

    private void playSound(int select) {
        if (isRealSound)
            soundManager.playSound(select);
        else
            mainPlain.repeatTTS.speak(answer.get(select), TextToSpeech.QUEUE_FLUSH,
                    null);
    }

}
