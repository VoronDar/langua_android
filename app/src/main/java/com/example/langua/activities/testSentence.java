package com.example.langua.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.langua.R;
import com.example.langua.SenteceCheck.SentenceChecker;
import com.example.langua.activities.utilities.ActivitiesUtils;
import com.example.langua.activities.utilities.Test;
import com.example.langua.activities.utilities.TrasportActivities;
import com.example.langua.cards.Card;
import com.example.langua.cards.PhraseologyCard;
import com.example.langua.cards.VocabularyCard;
import com.example.langua.declaration.consts;
import com.example.langua.ruler.Ruler;

import java.util.ArrayList;

import static com.example.langua.ApproachManager.ApproachManager.VOCABULARY_INDEX;
import static com.example.langua.SenteceCheck.SentenceChecker.isDecodable;
import static com.example.langua.activities.utilities.ActivitiesUtils.removeTitleBar;
import static com.example.langua.activities.utilities.ActivitiesUtils.setOrientation;

public class testSentence extends Test {
    private boolean isRight;
    private String writeSentence;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_test_sentence, null);
        return inflater.inflate(R.layout.activity_test_sentence, container, false);

    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            ruler.closeDatabase();

            TextView word = this.view.findViewById(R.id.word);
            if (index == VOCABULARY_INDEX)
                writeSentence = ((VocabularyCard) card).getTrainNative();
            else
                writeSentence = ((PhraseologyCard) card).getColumnWriteSentenceNative();
            word.setText(writeSentence);

            view.findViewById(R.id.check).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    answerClick(v);
                }
            });
            TextView editText = this.view.findViewById(R.id.editText);
            if (mainPlain.sizeRatio < mainPlain.triggerRatio){
                editText.setTextSize(mainPlain.sizeHeight/(25f*multiple));
                word.setTextSize(mainPlain.sizeHeight/(30f*multiple));
            }
        }
        catch (Error e){
            ActivitiesUtils.createErrorWindow(mainPlain.activity, new ActivitiesUtils.DoWhileError() {
                        @Override
                        public void action() {
                            goNext(null);
                        }
                    }, card.getId(),
                    getResources().getString(R.string.unknown_error));
        }


    }


    private String deleteOtherChar(String string){
        return string.replaceAll("!|,|\n|\\?|\\.|\"", "");
    }

    public void answerClick(View view) {


        EditText editText = this.view.findViewById(R.id.editText);

        VocabularyCard vocabularyCard = (VocabularyCard)card;
        String rightString;
        String describe;

        if (isDecodable(vocabularyCard.getTrain())) {
            isRight = SentenceChecker.check(deleteOtherChar(editText.getText().toString()), ((VocabularyCard) card).getTrain());
            rightString = SentenceChecker.getRightString();
            describe = SentenceChecker.showCommand();
        }
        else{

            String getText = deleteOtherChar(editText.getText().toString().trim().toLowerCase());
            String answer;
            if (index == VOCABULARY_INDEX)
                answer = ((VocabularyCard)card).getTrain();
            else
                answer = ((PhraseologyCard)card).getColumnWriteSentence();
            //ArrayList<String> answers = new ArrayList<>();


            while (answer.contains("|")){
                String thisString = answer.substring(0, answer.indexOf("|"));
                answer = answer.substring(answer.indexOf("|") + 1);
                //answers.add(thisString);
                if (deleteOtherChar(thisString.trim().toLowerCase()).equals(getText)){
                    isRight = true;
                }
            }
           // answers.add(answer.trim().toLowerCase());
            Log.i("main", answer);
            if (deleteOtherChar(answer.trim().toLowerCase()).equals(getText)){
                isRight = true;
            }
            rightString = answer;
            describe = "";
        }

        if (isRight){
            editText.setTextColor(getResources().getColor(R.color.colorWhiteRight));
            mainPlain.repeatTTS.speak(editText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
        }
        else{
            mainPlain.repeatTTS.speak(rightString, TextToSpeech.QUEUE_FLUSH, null);
            AlertDialog.Builder adb = new AlertDialog.Builder(mainPlain.activity);
            View my_custom_view = getLayoutInflater().inflate(R.layout.error_layout, null);
            adb.setView(my_custom_view);

            TextView rightAnswer = my_custom_view.findViewById(R.id.rightAnswer);


            rightAnswer.setText(rightString);
            TextView yourAnswer = my_custom_view.findViewById(R.id.yourAnswer);
            yourAnswer.setText(editText.getText().toString() + "\n"  +  describe);
            adb.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    goNext(null);
                }
            });
            final AlertDialog ad = adb.create();
            my_custom_view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ad.cancel();
                }
            });

            if (mainPlain.sizeRatio < mainPlain.triggerRatio){
                rightAnswer.setTextSize(mainPlain.sizeHeight/(30f*mainPlain.multiple));
                yourAnswer.setTextSize(mainPlain.sizeHeight/(30f*mainPlain.multiple));
                ((TextView)my_custom_view.findViewById(R.id.errorName)).
                        setTextSize(mainPlain.sizeHeight/(40f*mainPlain.multiple));
                ((Button)my_custom_view.findViewById(R.id.next)).
                        setTextSize(mainPlain.sizeHeight/(35f*mainPlain.multiple));
                ((Button)my_custom_view.findViewById(R.id.next)).
                        setHeight((int)(mainPlain.sizeHeight/(12f)));
                rightAnswer.setTextSize(mainPlain.sizeHeight/(30f*mainPlain.multiple));
            }

            ad.show();
    }

        this.view.findViewById(R.id.check).setEnabled(false);


        TrasportActivities.putWrongcardsBack(mainPlain.activity, isRight,  practiceState, card, index);

        if (isRight)
            TrasportActivities.goNextCardActivity(this, true, practiceState,card, index);

    }
    public void skipTest(View view){
        TrasportActivities.skipPracticeCard(this, card, index);
    }

    public void goNext(View view) {
        TrasportActivities.goNextCardActivity(this, isRight, practiceState, card, index);

    }

}

