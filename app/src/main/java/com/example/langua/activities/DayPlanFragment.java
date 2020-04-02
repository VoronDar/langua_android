package com.example.langua.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.print.PrintJob;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.R;
import com.example.langua.declaration.consts;
import com.example.langua.ruler.Ruler;
import com.example.langua.adapters.ToDoAdapter;
import com.example.langua.transportSQL.TransportSQL;
import com.example.langua.units.toDoUnit;
import com.example.langua.transportPreferences.transportPreferences;

import java.util.ArrayList;

import static com.example.langua.ApproachManager.ApproachManager.PHRASEOLOGY_INDEX;
import static com.example.langua.ApproachManager.ApproachManager.VOCABULARY_INDEX;
import static com.example.langua.activities.mainPlain.smallWidth;

public class DayPlanFragment extends Fragment {
    private ToDoAdapter adapter;
    private TextView dayCount;
    private Context context;
    private ArrayList<toDoUnit> units;
    private int newIndex;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_day_plan, null);
        return inflater.inflate(R.layout.fragment_day_plan, container, false);

    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        TextView message = view.findViewById(R.id.messageError);

        if (mainPlain.sizeRatio < mainPlain.triggerRatio){
        ((TextView)view.findViewById(R.id.dailyPlan)).setTextSize(mainPlain.sizeHeight/45f);
        ((TextView)view.findViewById(R.id.messageError)).setTextSize(mainPlain.sizeHeight/30f);
        ((TextView)view.findViewById(R.id.dayCount)).setTextSize(mainPlain.sizeHeight/(30f*mainPlain.multiple));
        }
        if (!Ruler.isDailyPlanCompleted(context)) {     // user didn't complete the daily plan

            recyclerView.setVisibility(View.VISIBLE);
            message.setVisibility(View.GONE);

            units = new ArrayList<>();

            final String vocabularyStart = "слова";
            final String phraseologyStart = "фразы";


            int cardCountVocabulary = transportPreferences.getTodayLeftVocabularyRepeatCardQuantityPreference(context);
            int cardCountPhraseology = transportPreferences.getTodayLeftPhraseologyRepeatCardQuantityPreference(context);


            if (cardCountVocabulary > 0) {
                    units.add(new toDoUnit(false, "повторение", vocabularyStart,
                            VOCABULARY_INDEX, cardCountVocabulary));
            }
            //if (cardCountPhraseology > 0){
            //        units.add(new toDoUnit(false, "повторение",
            //                phraseologyStart, PHRASEOLOGY_INDEX, cardCountPhraseology));
            //}

            int cardCountStudyVocabulary = transportPreferences.getTodayLeftVocabularyStudyCardQuantityPreference(context);
            int cardCountStudyPhraseology = transportPreferences.getTodayLeftPhraseologyStudyCardQuantityPreference(context);


            if (cardCountStudyVocabulary > 0) {
                units.add(new toDoUnit(false, "изучение", vocabularyStart,
                        VOCABULARY_INDEX, cardCountStudyVocabulary));
            }
            //if (cardCountStudyPhraseology > 0) {
            //    units.add(new toDoUnit(false, "изучение", phraseologyStart,
            //            PHRASEOLOGY_INDEX, cardCountStudyPhraseology));
            //}


            adapter = new ToDoAdapter(view.getContext(), units);
            adapter.setBlockListener(new ToDoAdapter.BlockListener() {

                @Override
                public void onClick(int position) {
                    newIndex = units.get(position).getIndex();
                    goToTheLearn(position);
                }

                @Override
                public void onLongClick(int position) {

                }
            });

            if ((mainPlain.sizeRatio < mainPlain.triggerRatio) && mainPlain.sizeWidth > smallWidth)
                recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2, RecyclerView.VERTICAL, false));
            else
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(adapter);
            recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            adapter.notifyDataSetChanged();
        }
        else{
            recyclerView.setVisibility(View.GONE);
            message.setVisibility(View.VISIBLE);
        }

        dayCount = view.findViewById(R.id.dayCount);
        dayCount.setText(Integer.toString(Ruler.getDay(context) - transportPreferences.getStartDay(context) + 1));

        View dayCountBg = view.findViewById(R.id.dayCountBG);
        while (dayCount.getWidth() > dayCountBg.getWidth()){
            dayCount.setTextSize(dayCount.getTextSize()-1);
        Log.i("mainDC_width", Integer.toString(dayCount.getWidth()));}

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void goToTheLearn(int position) {
            if (units.get(position).getChapterName().equals("повторение")) {
                Ruler.dayState = consts.dayState.repeat;
                ((mainPlain) getActivity()).goToTheRepeating(newIndex);
            }
            if (units.get(position).getChapterName().equals("изучение")) {
                Ruler.dayState = consts.dayState.study;
                ((mainPlain) getActivity()).goToTheRepeating(newIndex);
            }
    }
    private void delete_card(int position){
        if (units.get(position).isRemovable()){
            // ЧУ_ЧУТЬ позже
        }
    }

}
