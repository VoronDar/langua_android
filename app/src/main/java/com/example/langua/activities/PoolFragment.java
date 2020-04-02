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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.R;
import com.example.langua.ruler.Ruler;
import com.example.langua.adapters.VocabularyPracticeAdapter;
import com.example.langua.units.VocabularyPracticeUnit;
import com.example.langua.declaration.consts;
import com.example.langua.transportPreferences.transportPreferences;

import java.util.ArrayList;

public class PoolFragment extends Fragment {
    private VocabularyPracticeAdapter adapter;
    private Context context;
    private ArrayList<VocabularyPracticeUnit> units;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_pool, null);
        return inflater.inflate(R.layout.fragment_pool, container, false);

    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();


        if (mainPlain.sizeRatio < mainPlain.triggerRatio)
        ((TextView)view.findViewById(R.id.practiceType)).setTextSize(mainPlain.sizeHeight/45f);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        TextView message = view.findViewById(R.id.messageError);
           if (!Ruler.isDailyPlanCompleted(context)) {                                                         // user didn't complete the daily plan
                 recyclerView.setVisibility(View.GONE);
                message.setVisibility(View.VISIBLE);
               if (mainPlain.sizeRatio < mainPlain.triggerRatio)
                   message.setTextSize(mainPlain.sizeHeight/40f);

        }
        else{                                                                                                  // user completed the daily plan
        recyclerView.setVisibility(View.VISIBLE);
        message.setVisibility(View.GONE);
        units = new ArrayList<>(4);
        units.add(new VocabularyPracticeUnit(consts.SELECT_REPEAT_NEXT_DAY, context.getResources().getString(R.string.select_test_only_next_level)));
        units.add(new VocabularyPracticeUnit(consts.SELECT_REPEAT_ALL_COURSE, context.getResources().getString(R.string.select_test_all_course)));
        //units.add(new VocabularyPracticeUnit(consts.SELECT_PASS_EXAM, context.getResources().getString(R.string.select_test_pass_exam)));
        units.add(new VocabularyPracticeUnit(consts.SELECT_ALL_TODAY_CARDS, context.getResources().getString(R.string.select_test_all_today_cards)));



        adapter = new VocabularyPracticeAdapter(view.getContext(), units);
        adapter.setBlockListener(new VocabularyPracticeAdapter.BlockListener() {
            @Override
            public void onClick(int position) {
                Ruler.setPractiseChapter(units.get(position).getID());
                ((mainPlain) getActivity()).goTothePractice();
            }
        });
        if ((mainPlain.sizeRatio < mainPlain.triggerRatio))
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2,
                    RecyclerView.VERTICAL, false));
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
                recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        adapter.notifyDataSetChanged();
        }



    }

}
