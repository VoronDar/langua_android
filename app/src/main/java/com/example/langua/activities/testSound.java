package com.example.langua.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.activities.utilities.SoundManager;
import com.example.langua.activities.utilities.Test;
import com.example.langua.activities.utilities.TrasportActivities;
import com.example.langua.adapters.ButtonLetterAdapter;
import com.example.langua.R;
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

public class testSound extends Test {

    private VocabularyCard nowCard;
    private int lettersCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_test_sound, null);
        return inflater.inflate(R.layout.activity_test_sound, container, false);

    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        nowCard = (VocabularyCard) card;
        final ArrayList<buttonLetterUnit> letters = new ArrayList<>();


        for (int i = 0; i < nowCard.getWord().length(); i++) {
            letters.add(new buttonLetterUnit(nowCard.getWord().substring(i, i + 1)));
        }
        for (int i = 0; i < (letters.size() + 7) % 7; i++) {
            Random r = new Random();
            String c = String.valueOf((char) (r.nextInt(26) + 'a'));
            letters.add(new buttonLetterUnit(c));
        }
        Collections.shuffle(letters);

        final ButtonLetterAdapter adapter = new ButtonLetterAdapter(getContext(), letters);
        final View v = this.view;
        adapter.setBlockListener(new ButtonLetterAdapter.BlockListener() {
            @Override
            public void onClick(int position) {
                TextView word = v.findViewById(R.id.editText);
                if (letters.get(position).isPressed()) {
                    String wordString = word.getText().toString();
                    wordString = wordString.substring(0, letters.get(position).getWordPosition() - 1) + wordString.substring(letters.get(position).getWordPosition());
                    word.setText(wordString);
                    letters.get(position).setPressed(false);
                    int oldPos = letters.get(position).getWordPosition();
                    letters.get(position).setWordPosition(0);
                    for (int i = 0; i < letters.size(); i++) {
                        if (letters.get(i).getWordPosition() != 0 && letters.get(i).getWordPosition() > oldPos) {
                            letters.get(i).setWordPosition(letters.get(i).getWordPosition() - 1);
                        }
                    }
                    lettersCount = word.getText().toString().length();
                    adapter.notifyDataSetChanged();
                } else {
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

        soundManager = getPreparedSoundManager(mainPlain.activity, card.getId());
        isRealSound = soundManager.isSoundExist();

        ImageView sound = view.findViewById(R.id.soundButton);
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playWord(v);
            }
        });

        view.findViewById(R.id.check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerClick(v);
            }
        });

        playWord(null);


        TextView editText = this.view.findViewById(R.id.editText);
        if (mainPlain.sizeRatio < mainPlain.triggerRatio){
            editText.setTextSize(mainPlain.sizeHeight/(20*multiple));
            sound.setMinimumWidth(mainPlain.sizeHeight/7);
        }

    }

    public void answerClick(View view) {

        view.setEnabled(false);

        TextView editText = this.view.findViewById(R.id.editText);
        if (editText.getText().toString().equals(nowCard.getWord())){
            isRight = true;
            editText.setTextColor(getResources().getColor(R.color.colorWhiteRight));
        }
        else{
            playWord(null);
        isRight = false;
            AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
            View my_custom_view = getLayoutInflater().inflate(R.layout.error_layout, null);
            adb.setView(my_custom_view);
            TextView rightAnswer = my_custom_view.findViewById(R.id.rightAnswer);
            rightAnswer.setText(nowCard.getWord());
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

        if (isRight){
            soundManager.closeSound();
            TrasportActivities.goNextCardActivity(this, isRight, practiceState,card, index);
        }

    }
    public void skipTest(View view){
        soundManager.closeSound();
        TrasportActivities.skipPracticeCard(this, card, index);
    }

    public void goNext(View view) {
        soundManager.closeSound();
        TrasportActivities.goNextCardActivity(this, isRight, practiceState, card, index);

    }

}