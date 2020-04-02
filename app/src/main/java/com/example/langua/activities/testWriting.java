package com.example.langua.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.activities.utilities.Test;
import com.example.langua.activities.utilities.TrasportActivities;
import com.example.langua.adapters.ButtonLetterAdapter;
import com.example.langua.R;
import com.example.langua.cards.Card;
import com.example.langua.cards.PhraseologyCard;
import com.example.langua.cards.VocabularyCard;
import com.example.langua.declaration.consts;
import com.example.langua.ruler.Ruler;
import com.example.langua.units.buttonLetterUnit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.example.langua.ApproachManager.ApproachManager.VOCABULARY_INDEX;
import static com.example.langua.activities.utilities.ActivitiesUtils.getPreparedSoundManager;
import static com.example.langua.activities.utilities.ActivitiesUtils.removeTitleBar;
import static com.example.langua.activities.utilities.ActivitiesUtils.setOrientation;

public class testWriting extends Test {
    private boolean isRight;
    private int lettersCount = 0;
    private String wordString;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_test_writing, null);
        return inflater.inflate(R.layout.activity_test_writing, container, false);

    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ruler.closeDatabase();

        setMeaningAndTranslate();

        final ArrayList<buttonLetterUnit> letters = new ArrayList<>();


        if (index == VOCABULARY_INDEX)
            wordString = ((VocabularyCard)card).getWord();
        else
            wordString = ((PhraseologyCard)card).getWord();

        for (int i = 0; i < wordString.length(); i++){
            letters.add(new buttonLetterUnit(wordString.substring(i, i+1)));
        }
        for (int i = 0; i < (letters.size()+7)%7; i++){
            Random r = new Random();
            String c = String.valueOf((char)(r.nextInt(26) + 'a'));
            letters.add(new buttonLetterUnit(c));
        }
        Collections.shuffle(letters);

        final ButtonLetterAdapter adapter = new ButtonLetterAdapter(getContext(), letters);
        final View v = this.view;
        adapter.setBlockListener(new ButtonLetterAdapter.BlockListener() {
            @Override
            public void onClick(int position) {
                TextView word = v.findViewById(R.id.editText);
                if (letters.get(position).isPressed()){
                    String wordString = word.getText().toString();
                    wordString = wordString.substring(0, letters.get(position).getWordPosition()-1) + wordString.substring(letters.get(position).getWordPosition());
                    word.setText(wordString);
                    letters.get(position).setPressed(false);
                    int oldPos =  letters.get(position).getWordPosition();
                    letters.get(position).setWordPosition(0);
                    for (int i = 0; i < letters.size(); i++){
                        if (letters.get(i).getWordPosition() != 0 && letters.get(i).getWordPosition() > oldPos){
                            letters.get(i).setWordPosition(letters.get(i).getWordPosition()-1);
                        }
                    }
                    lettersCount = word.getText().toString().length();
                    adapter.notifyDataSetChanged();
                }
                else{
                    lettersCount = word.getText().toString().length();
                    letters.get(position).setPressed(true);
                    letters.get(position).setWordPosition(++lettersCount);
                    adapter.notifyDataSetChanged();
                    word.setText(word.getText() + letters.get(position).getLetter());
                }
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.letterBlocks);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(null);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        adapter.notifyDataSetChanged();

        view.findViewById(R.id.check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerClick(v);
            }
        });

        TextView editText = this.view.findViewById(R.id.editText);
        if (mainPlain.sizeRatio < mainPlain.triggerRatio){
            editText.setTextSize(mainPlain.sizeHeight/(20*multiple));
        }
        
    }


    public void answerClick(View view) {
        view.setEnabled(false);


        TextView editText = this.view.findViewById(R.id.editText);
        if (editText.getText().toString().equals(wordString)){
            isRight = true;
            editText.setTextColor(getResources().getColor(R.color.colorWhiteRight));
        }
        else{
        isRight = false;
            AlertDialog.Builder adb = new AlertDialog.Builder(mainPlain.activity);
            View my_custom_view = getLayoutInflater().inflate(R.layout.error_layout, null);
            adb.setView(my_custom_view);
            TextView rightAnswer = my_custom_view.findViewById(R.id.rightAnswer);
            rightAnswer.setText(wordString);
            TextView yourAnswer = my_custom_view.findViewById(R.id.yourAnswer);
            yourAnswer.setText(editText.getText());
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


        TrasportActivities.putWrongcardsBack(mainPlain.activity, isRight,  practiceState, card, index);

        if (isRight)
            TrasportActivities.goNextCardActivity(this, isRight, practiceState,card, index);

        soundManager = getPreparedSoundManager(mainPlain.activity, card.getId());
        playWord(null);
        if (soundManager != null)
            soundManager.closeSound();

}
    public void skipTest(View view){
        TrasportActivities.skipPracticeCard(this, card, index);
    }

    public void goNext(View view) {
        TrasportActivities.goNextCardActivity(this, isRight, practiceState, card, index);

    }

}
