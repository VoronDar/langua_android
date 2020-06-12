package com.example.langua.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.declaration.consts;
import com.example.langua.units.PracticeTestsUnit;
import com.example.langua.adapters.PracticeTypeAdapter;
import com.example.langua.R;
import com.example.langua.ruler.Ruler;
import java.util.ArrayList;

import static com.example.langua.declaration.consts.P_SENTENCE_INDEX;
import static com.example.langua.declaration.consts.P_TRANSLATE_INDEX;
import static com.example.langua.declaration.consts.P_TRANSLATE_NATIVE_INDEX;
import static com.example.langua.declaration.consts.P_WRITING_INDEX;
import static com.example.langua.declaration.consts.SENTENCE_INDEX;
import static com.example.langua.declaration.consts.SOUND_INDEX;
import static com.example.langua.declaration.consts.TRANSLATE_INDEX;
import static com.example.langua.declaration.consts.TRANSLATE_NATIVE_INDEX;
import static com.example.langua.ApproachManager.ApproachManager.VOCABULARY_INDEX;
import static com.example.langua.declaration.consts.WRITING_INDEX;

public class PracticeSelectPoolFragment extends Fragment {
    private PracticeTypeAdapter adapter;
    private Context context;
    private ArrayList<PracticeTestsUnit> units;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_pool_select, null);
        return inflater.inflate(R.layout.fragment_pool_select, container, false);

    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();

        if (mainPlain.sizeRatio < mainPlain.triggerRatio)
        ((TextView)view.findViewById(R.id.exerciseType)).setTextSize(mainPlain.sizeHeight/45f);

        units = new ArrayList<>(6);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        TextView message = view.findViewById(R.id.messageError);

        if (Ruler.isDailyPlanCompleted(context)) {
            recyclerView.setVisibility(View.VISIBLE);
            message.setVisibility(View.GONE);
            boolean isSomethingPractice = false;

            switch (Ruler.getPractiseChapter()) {
                case consts.SELECT_REPEAT_NEXT_DAY:

                    ArrayList<Integer> CardCount = getCardCountNextTest(VOCABULARY_INDEX);
                    for (int i = 0; i <= consts.END_TEST - consts.START_TEST; i++) {
                        if (CardCount.get(i) > 0) {
                            isSomethingPractice = true;
                            units.add(new PracticeTestsUnit(i, CardCount.get(i), getStringVocabularyByIndex(i), false, getImageVocabularyByIndex(i), VOCABULARY_INDEX));
                        }
                    }
                    //CardCount = getCardCountNextTest(PHRASEOLOGY_INDEX);

                    //for (int i = 0; i <= consts.P_END_TEST - consts.START_TEST; i++) {
                    //    if (CardCount.get(i) > 0) {
                            isSomethingPractice = true;
                    //        units.add(new PracticeTestsUnit(i, CardCount.get(i), getStringPhraseologyByIndex(i), false, getImagePhraseologyByIndex(i), PHRASEOLOGY_INDEX));
                    //    }
                    //}
                    break;

                case consts.SELECT_REPEAT_ALL_COURSE:{
                    isSomethingPractice = true;
                    int cardCount = getAllCardCount(VOCABULARY_INDEX);
                    for (int i = 0; i <= consts.END_TEST - consts.START_TEST; i++) {
                        units.add(new PracticeTestsUnit(i, cardCount, getStringVocabularyByIndex(i), false, getImageVocabularyByIndex(i), VOCABULARY_INDEX));
                    }

                    //cardCount = getAllCardCount(PHRASEOLOGY_INDEX);
                    //for (int i = 0; i <= consts.P_END_TEST - consts.START_TEST; i++) {
                    //    units.add(new PracticeTestsUnit(i, cardCount, getStringPhraseologyByIndex(i), false, getImagePhraseologyByIndex(i), PHRASEOLOGY_INDEX));
                    //}
                    break;
                }
                case consts.SELECT_ALL_TODAY_CARDS:
                    isSomethingPractice = true;
                    int cardCount = getCardCountAllTodayCards(VOCABULARY_INDEX);
                    for (int i = 0; i <= consts.END_TEST - consts.START_TEST; i++) {
                        units.add(new PracticeTestsUnit(i, cardCount, getStringVocabularyByIndex(i), false, getImageVocabularyByIndex(i), VOCABULARY_INDEX));
                    }

                    //cardCount = getCardCountAllTodayCards(PHRASEOLOGY_INDEX);
                    //for (int i = 0; i <= consts.P_END_TEST - consts.START_TEST; i++) {
                    //    units.add(new PracticeTestsUnit(i, cardCount, getStringPhraseologyByIndex(i), false, getImagePhraseologyByIndex(i), PHRASEOLOGY_INDEX));
                    //}
                    break;

        }

            if (!isSomethingPractice){
                recyclerView.setVisibility(View.GONE);
                message.setVisibility(View.VISIBLE);
                message.setText(view.getResources().getString(R.string.there_is_no_practice));
            }
        }
        else{
            recyclerView.setVisibility(View.GONE);
            message.setVisibility(View.VISIBLE);
        }

        adapter = new PracticeTypeAdapter(view.getContext(), units);
        adapter.setBlockListener(new PracticeTypeAdapter.BlockListener() {
            @Override
            public void onClick(int position) {
                        selectTest(position, units.get(position).getSqlIndex());
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        recyclerView.setAdapter(adapter);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        adapter.notifyDataSetChanged();
    }

    private void selectTest(int position, int index){
        Ruler ruler = new Ruler(context, index);
        Ruler.setPractiseTestTypes(units.get(position).getID()+ consts.START_TEST);
        Ruler.setIsPracticeNow(true);
        Fragment fragment;
        switch (Ruler.getPractiseChapter()) {
            case consts.SELECT_ALL_TODAY_CARDS:
            case consts.SELECT_REPEAT_ALL_COURSE:
                fragment = ruler.changeActivityWhilePracticeForTheAllCards(this);
                break;
            case consts.SELECT_REPEAT_NEXT_DAY:
                fragment = ruler.changeActivityWhilePracticeForTheNextTests(this);
                break;
            default:
                fragment = null;
        }
        ruler.closeDatabase();
        mainPlain.activity.slideFragment(fragment);
    }


    private ArrayList<Integer> getCardCountNextTest(int index){

        Ruler ruler = new Ruler(context, index);
        ArrayList<Integer>  cardCount = ruler.fillPracticeDBForNextTest();
        ruler.closeDatabase();
        return cardCount;
    }
    private int getCardCountAllTodayCards(int index){
        ArrayList<Integer> cardCounts = getCardCountNextTest(index);
        int cardCount = 0;
        for (int i = 0; i < cardCounts.size(); i++)
            cardCount += cardCounts.get(i);
        return cardCount;
    }
    private int getAllCardCount(int index){
        Ruler ruler = new Ruler(context, index);
        int cardCount = ruler.fillPracticeDBForExam();
        ruler.closeDatabase();
        return cardCount;
    }

    private String getStringVocabularyByIndex(int index){
        switch (index){
            case TRANSLATE_INDEX:
                return context.getResources().getString(R.string.PracticecTranslate);
            case TRANSLATE_NATIVE_INDEX:
                return context.getResources().getString(R.string.PracticecTranslateNative);
            case SOUND_INDEX:
                return context.getResources().getString(R.string.PracticecAudio);
            case WRITING_INDEX:
                return context.getResources().getString(R.string.PracticecWriting);
            case SENTENCE_INDEX:
                return context.getResources().getString(R.string.PracticecSentence);
            default:
                return null;
        }
    }
    private String getStringPhraseologyByIndex(int index){
        switch (index){
            case P_TRANSLATE_INDEX:
                return context.getResources().getString(R.string.PracticecTranslate);
            case P_TRANSLATE_NATIVE_INDEX:
                return context.getResources().getString(R.string.PracticecTranslateNative);
            case P_WRITING_INDEX:
                return context.getResources().getString(R.string.PracticecWriting);
            case P_SENTENCE_INDEX:
                return context.getResources().getString(R.string.PracticecSentence);
            default:
                return null;
        }
    }
    private int getImageVocabularyByIndex(int index){
        switch (index){
            case TRANSLATE_INDEX:
                return R.drawable.button_translate;
            case TRANSLATE_NATIVE_INDEX:
                return R.drawable.button_translate_native;
            case SOUND_INDEX:
                return R.drawable.button_audio;
            case WRITING_INDEX:
                return R.drawable.button_write;
            case SENTENCE_INDEX:
                return R.drawable.button_sentence;
            default:
                return 0;
        }
    }
    private int getImagePhraseologyByIndex(int index){
        switch (index){
            case P_TRANSLATE_INDEX:
                return R.drawable.button_translate_phras;
            case P_TRANSLATE_NATIVE_INDEX:
                return R.drawable.button_translate_native_phras;
            case P_WRITING_INDEX:
                return R.drawable.button_write_phras;
            case P_SENTENCE_INDEX:
                return R.drawable.button_sentence_phras;
            default:
                return 0;
        }
    }

}
