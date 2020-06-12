package com.example.langua.activities;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.langua.ApproachManager.ApproachManager;
import com.example.langua.R;
import com.example.langua.activities.utilities.ActivitiesUtils;
import com.example.langua.activities.utilities.CardActivity;
import com.example.langua.activities.utilities.SoundManager;
import com.example.langua.cards.VocabularyCard;
import com.example.langua.declaration.consts;
import com.example.langua.units.TextBlock;
import com.example.langua.transportPreferences.transportPreferences;

import java.util.ArrayList;

import static com.example.langua.activities.mainPlain.repeatTTS;
import static com.example.langua.activities.utilities.ActivitiesUtils.getExampleColor;
import static com.example.langua.activities.utilities.ActivitiesUtils.getMeaningColor;
import static com.example.langua.activities.utilities.ActivitiesUtils.getPreparedSoundManager;
import static com.example.langua.activities.utilities.ActivitiesUtils.getSynonymColor;
import static com.example.langua.activities.utilities.ActivitiesUtils.getTranslateColor;
import static com.example.langua.activities.utilities.ActivitiesUtils.setGroup;
import static com.example.langua.activities.utilities.ActivitiesUtils.setPart;


public class MainActivity extends CardActivity {

    private SoundManager soundManager;
    private boolean isRealSound;

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textBlocksList = new ArrayList<>();

        try {
            TextView word = this.view.findViewById(R.id.word);
            ImageView sound = this.view.findViewById(R.id.sound);
            TextView transcription = this.view.findViewById(R.id.transcription);

            VocabularyCard card = (VocabularyCard) abstractCard;


            if (transportPreferences.getVocabularyApproach(context) == ApproachManager.VOC_AUDIO_APPROACH) {
                word.setVisibility(View.GONE);
                sound.setVisibility(View.VISIBLE);
                soundButton.setVisibility(View.GONE);
                transcription.setVisibility(View.GONE);
            } else {
                word.setText(card.getWord());
                if (card.getTranscription() != null) {
                    transcription.setText(card.getTranscription());
                }
            }


            boolean isMeaning = (transportPreferences.getMeaningPriority(context) > consts.PRIORITY_NEVER);
            boolean isTranslate = ((transportPreferences.getTranslatePriority(context) >= consts.PRIORITY_NEVER_TEST) && card.getTranslate() != null);
            Log.i("isMeaning", Boolean.toString(isMeaning));

            // add priorityMeaning
            boolean isInCardMeaning = false;
            if (isMeaning) {
                    textBlocksList.add(new TextBlock(0, null, 0, card.getId()));
                    if (card.getMeaning() != null && transportPreferences.getMeaningLanguage(context) >= consts.LANGUAGE_NEW) {

                        textBlocksList.add(new TextBlock(21, card.getMeaning(), getMeaningColor(this.view)));
                        isInCardMeaning = true;
                    }
                    if (card.getMeaningNative() != null && (transportPreferences.getMeaningLanguage(context) == consts.LANGUAGE_BOTH ||
                            transportPreferences.getMeaningLanguage(context) == consts.LANGUAGE_NATIVE)) {
                        textBlocksList.add(new TextBlock(18, card.getMeaningNative(), getMeaningColor(this.view)));
                        isInCardMeaning = true;
                    }
                    if (!isInCardMeaning && card.getMeaningNative() != null){
                     textBlocksList.add(new TextBlock(18, card.getMeaningNative(), getMeaningColor(this.view)));
                        isInCardMeaning = true;
                    }
                    else if (!isInCardMeaning && card.getMeaning() != null){
                        textBlocksList.add(new TextBlock(18, card.getMeaning(), getMeaningColor(this.view)));
                       isInCardMeaning = true;
                    }
            }


            if (isTranslate || !isInCardMeaning) {
                if (card.getTranslate() != null)
                    textBlocksList.add(new TextBlock(23, card.getTranslate(), getTranslateColor(this.view), isInCardMeaning));
                else if (card.getMeaning() != null || card.getMeaningNative() != null) {
                    if (card.getMeaning() != null && transportPreferences.getMeaningLanguage(context) >= consts.LANGUAGE_NEW)
                        textBlocksList.add(new TextBlock(21, card.getMeaning(), getMeaningColor(this.view)));
                    else if (card.getMeaningNative() != null && (transportPreferences.getMeaningLanguage(context) == consts.LANGUAGE_BOTH ||
                            transportPreferences.getMeaningLanguage(context) == consts.LANGUAGE_NATIVE))
                        textBlocksList.add(new TextBlock(18, card.getMeaningNative(), getMeaningColor(this.view)));
                    else {
                        textBlocksList.add(new TextBlock(21, card.getMeaning(), getMeaningColor(this.view)));
                        textBlocksList.add(new TextBlock(18, card.getMeaningNative(), getMeaningColor(this.view)));
                    }
                }
                else {
                    ActivitiesUtils.createErrorWindow(mainPlain.activity, new ActivitiesUtils.DoWhileError() {
                        @Override
                        public void action() {
                            onRemember(null);
                        }
                    }, abstractCard.getId(), getResources().getString(R.string.no_translate_meaning));
                }
            }


            addMemInCard();


            if (transportPreferences.getVocabularyApproach(context) == ApproachManager.VOC_AUDIO_APPROACH) {
                textBlocksList.add(new TextBlock(23, card.getTranscription(), getExampleColor(this.view), true));
                textBlocksList.add(new TextBlock(23, card.getWord(), getTranslateColor(this.view)));
            }

            textBlocksList.add(new TextBlock(20, card.getHelp(), getResources().getColor(R.color.colorBlack), true));


            if (transportPreferences.getExampleAvailability(context) == consts.AVAILABILITY_ON) {
                if (card.getExample() != null && transportPreferences.getExampleLanguage(context) >= consts.LANGUAGE_NEW) {
                    textBlocksList.add(new TextBlock(21, card.getExample(), getExampleColor(this.view), true));
                }
                if (card.getExampleNative() != null && ((transportPreferences.getExampleLanguage(context) == consts.LANGUAGE_NATIVE ||
                        transportPreferences.getExampleLanguage(context) == consts.LANGUAGE_BOTH)))
                    textBlocksList.add(new TextBlock(17, card.getExampleNative(), getExampleColor(this.view), false));
            }


            if ((card.getSynonym() != null && transportPreferences.getSynomymAvailability(context) == consts.AVAILABILITY_ON)
                    || (card.getAntonym() != null && transportPreferences.getAntonymAvailability(context) == consts.AVAILABILITY_ON)) {
                String string = "";
                if ((card.getSynonym() != null && transportPreferences.getSynomymAvailability(context) == consts.AVAILABILITY_ON))
                    string += getResources().getString(R.string.synonym) + ": " + card.getSynonym();
                if ((card.getAntonym() != null && transportPreferences.getAntonymAvailability(context) == consts.AVAILABILITY_ON)) {
                    if (string.length() > 1)
                        string += "\n";
                    string += getResources().getString(R.string.antonym) + ": " + card.getAntonym();
                }
                textBlocksList.add(new TextBlock(20, string, getSynonymColor(this.view), true));
            }

            setGroup(this.view, card.getGroup());
            setPart(this.view, card.getPart());

            prepareAdapter();


            view.findViewById(R.id.playSound).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playSound(v);
                }
            });
            view.findViewById(R.id.sound).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playSound(v);
                }
            });

            if (mainPlain.sizeRatio < mainPlain.triggerRatio){
                word.setTextSize(mainPlain.sizeHeight/(15f*mainPlain.multiple));
                transcription.setTextSize(mainPlain.sizeHeight/(25f*mainPlain.multiple));
                ((TextView)view.findViewById(R.id.part)).setTextSize(mainPlain.sizeHeight/(25f*mainPlain.multiple));
                ((TextView)view.findViewById(R.id.group)).setTextSize(mainPlain.sizeHeight/(25f*mainPlain.multiple));
            }

        }
        catch(Error e){
            ActivitiesUtils.createErrorWindow(mainPlain.activity, new ActivitiesUtils.DoWhileError() {
                        @Override
                        public void action() {
                            onRemember(null);
                        }
                    }, abstractCard.getId(),
                    getResources().getString(R.string.unknown_error));
        }

        soundManager = getPreparedSoundManager(mainPlain.activity, abstractCard.getId());
        isRealSound = soundManager.isSoundExist();
        playSound(null);

    }

    private void playSound(View view){
        if (isRealSound)
            soundManager.playSound();
        else
            repeatTTS.speak(((VocabularyCard)abstractCard).getWord(), TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    public void onDestroy() {
        if (isRealSound)
            soundManager.closeSound();
        super.onDestroy();
    }
}
