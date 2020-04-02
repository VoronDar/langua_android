package com.example.langua.activities.utilities;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.langua.R;
import com.example.langua.activities.mainPlain;
import com.example.langua.cards.Card;
import com.example.langua.cards.PhraseologyCard;
import com.example.langua.cards.VocabularyCard;
import com.example.langua.declaration.consts;
import com.example.langua.ruler.Ruler;
import com.example.langua.transportPreferences.transportPreferences;

import static com.example.langua.ApproachManager.ApproachManager.VOCABULARY_INDEX;

public class Test extends Fragment {

    protected Button buttonNext;
    protected Button buttonCheck;
    protected Button buttonSkip;
    protected Ruler ruler;
    protected consts.cardState practiceState;
    protected boolean isRight;
    protected Card card;
    protected int index;
    protected View view;

    protected SoundManager soundManager;
    protected boolean isRealSound;
    public static int multiple;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void getInformation(){
        practiceState = Ruler.getCardState(Ruler.IsProblemTrain());
        index = Ruler.getSQLIndex();
        ruler = new Ruler(getContext(), index);
        card = Ruler.getLastCard();
    }
    protected void setButtonsListeners(){
        this.view.findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipTest(v);
            }
        });
        if (this.view.findViewById(R.id.next) != null) {
            this.view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goNext(v);
                }
            });
        }
    }

    public void skipTest(View view){
        TrasportActivities.skipPracticeCard(this, card, index);
    }
    public void goNext(View view) {
        TrasportActivities.goNextCardActivity(this, isRight, practiceState, card, index);

    }


    @Override
    public void onDestroy() {
        if (soundManager != null)
            soundManager.closeSound();
        super.onDestroy();
    }
    protected void playWord(View view){
        if (isRealSound)
            soundManager.playSound();
        else{
            if (index == VOCABULARY_INDEX)
                mainPlain.repeatTTS.speak(((VocabularyCard)card).getWord(), TextToSpeech.QUEUE_FLUSH,
                        null);
            else
                mainPlain.repeatTTS.speak(((PhraseologyCard)card).getWord(), TextToSpeech.QUEUE_FLUSH,
                        null);
        }
    }

    protected void setButtonNextDisabled(){
        buttonNext = this.view.findViewById(R.id.next);
        if (buttonNext != null)
            buttonNext.setEnabled(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getInformation();

        this.view = view;
        buttonCheck = view.findViewById(R.id.check);
        setButtonNextDisabled();
        buttonSkip = view.findViewById(R.id.skip);

        ScrollView recyclerView = view.findViewById(R.id.testScroll);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);


        if (mainPlain.sizeHeight < 1300)
            multiple = 1;
        else
            multiple = 2;


        if (mainPlain.sizeRatio < mainPlain.triggerRatio) {
            ((TextView) view.findViewById(R.id.part)).setTextSize((mainPlain.sizeHeight /(20f*multiple))
                    / (mainPlain.sizeRatio));
            setNewButtonSize(buttonCheck);
            setNewButtonSize(buttonSkip);
            setNewButtonSize(buttonNext);
        }

        TrasportActivities.showSkipButton(mainPlain.activity);
        setButtonsListeners();
    }

    private void setNewButtonSize(Button button){
        if (button != null){
            button.setTextSize((mainPlain.sizeHeight/(35f*multiple)));
            button.setHeight(mainPlain.sizeHeight /8);
        }
    }

    protected void setMeaningAndTranslate(){

        TextView meaning = view.findViewById(R.id.meaning);
        String meaningText = "";
        boolean isMeaning = false;
        if (transportPreferences.getMeaningPriority(getContext()) > consts.PRIORITY_NEVER_TEST){
            if (index == VOCABULARY_INDEX){
                if (((VocabularyCard)card).getMeaning() != null &&
                        (transportPreferences.getMeaningLanguage(getContext()) != consts.LANGUAGE_NATIVE)){
                    meaningText+=(((VocabularyCard)card).getMeaning()) + "\n";
                    meaning.setVisibility(View.VISIBLE);
                    isMeaning = true;
                }
                if (((VocabularyCard)card).getMeaningNative() != null &&
                        (transportPreferences.getMeaningLanguage(getContext()) != consts.LANGUAGE_NEW)){
                    meaningText+=((VocabularyCard)card).getMeaningNative();
                    meaning.setVisibility(View.VISIBLE);
                    isMeaning = true;
                }
                if (!isMeaning && ((VocabularyCard)card).getMeaningNative() != null){
                    meaningText+=((VocabularyCard)card).getMeaningNative();
                    meaning.setVisibility(View.VISIBLE);
                    isMeaning = true;
                }
                else if (!isMeaning && ((VocabularyCard)card).getMeaning() != null){
                    meaningText+=((VocabularyCard)card).getMeaning();
                    meaning.setVisibility(View.VISIBLE);
                    isMeaning = true;
                }
            }
            else {
                if (((PhraseologyCard) card).getMeaning() != null &&
                        (transportPreferences.getMeaningLanguage(getContext()) != consts.LANGUAGE_NATIVE)) {
                    meaning.setText(((PhraseologyCard) card).getMeaning());
                    meaning.setVisibility(View.VISIBLE);
                }
                if (((PhraseologyCard) card).getMeaningNative() != null &&
                        (transportPreferences.getMeaningLanguage(getContext()) != consts.LANGUAGE_NEW)) {
                    meaning.setText(((PhraseologyCard) card).getMeaningNative());
                    meaning.setVisibility(View.VISIBLE);
                }
            }
        }
        TextView word = view.findViewById(R.id.word);
        if (transportPreferences.getTranslatePriority(getContext()) > consts.PRIORITY_NEVER_TEST ||
                !isMeaning){
            word.setVisibility(View.VISIBLE);
            if (((VocabularyCard)card).getTranslate() == null && !isMeaning){
                boolean isNoneMeaning = false;
                Log.i("main", "HERE");
                if (((VocabularyCard)card).getMeaning() != null &&
                        (transportPreferences.getMeaningLanguage(getContext()) != consts.LANGUAGE_NATIVE)){
                    meaningText+=(((VocabularyCard)card).getMeaning()) + "\n";
                    meaning.setVisibility(View.VISIBLE);
                    isNoneMeaning = true;
                    Log.i("main", "here1");
                }
                if (((VocabularyCard)card).getMeaningNative() != null &&
                        (transportPreferences.getMeaningLanguage(getContext()) != consts.LANGUAGE_NEW)){
                    meaningText+=((VocabularyCard)card).getMeaningNative();
                    meaning.setVisibility(View.VISIBLE);
                    isNoneMeaning = true;
                    Log.i("main", ((VocabularyCard)card).getMeaningNative());
                }
                if (!isNoneMeaning && ((VocabularyCard)card).getMeaningNative() != null){
                    meaningText+=((VocabularyCard)card).getMeaningNative();
                    meaning.setVisibility(View.VISIBLE);
                    isNoneMeaning = true;
                    Log.i("main", "here3");
                }
                else if (!isNoneMeaning && ((VocabularyCard)card).getMeaning() != null){
                    meaningText+=((VocabularyCard)card).getMeaning();
                    meaning.setVisibility(View.VISIBLE);
                    isNoneMeaning = true;
                    Log.i("main", "here4");
                }
                if (!isNoneMeaning){
                    Log.i("main", "here5");
                    ActivitiesUtils.createErrorWindow(mainPlain.activity, new ActivitiesUtils.DoWhileError() {
                        @Override
                        public void action() {
                            goNext(null);
                        }
                    }, card.getId(), getResources().getString(R.string.no_translate_meaning));
                    return;
                }
            }
            if (index == VOCABULARY_INDEX)
                word.setText(((VocabularyCard)card).getTranslate());
            else
                word.setText(((PhraseologyCard)card).getTranslate());
        }

        meaning.setText(meaningText);


        if (mainPlain.sizeRatio < mainPlain.triggerRatio){
            word.setTextSize((mainPlain.sizeHeight/(20f*multiple)));
            meaning.setTextSize((mainPlain.sizeHeight/(30f*multiple)));
        }

    }
}
