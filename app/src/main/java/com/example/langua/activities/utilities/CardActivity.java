package com.example.langua.activities.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.R;
import com.example.langua.activities.mainPlain;
import com.example.langua.adapters.TextBlockAdapter;
import com.example.langua.cards.Card;
import com.example.langua.ruler.Ruler;
import com.example.langua.transportSQL.MainTransportSQL;
import com.example.langua.transportSQL.TransportSQLInterface;
import com.example.langua.units.TextBlock;


import java.util.ArrayList;

import static com.example.langua.activities.mainPlain.repeatTTS;
import static com.example.langua.activities.utilities.ActivitiesUtils.setRememberButtonText;
import static com.example.langua.ruler.CardRepeatManage.riseFromFall;

public abstract class CardActivity extends Fragment {

    protected int sqlIndex;
    protected RecyclerView recyclerView;
    protected ArrayList<TextBlock> textBlocksList;
    protected TextBlockAdapter adapter;
    protected Card abstractCard;
    protected ImageView soundButton;
    protected Context context;
    protected View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, null);
        return inflater.inflate(R.layout.activity_main, container, false);

    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = view.getContext();
        this.view = view;

        sqlIndex = Ruler.getSQLIndex();
        abstractCard = Ruler.getLastCard();
        setRememberButtonText(abstractCard.getRepetitionDat(), view);

        soundButton = view.findViewById(R.id.playSound);


        LinearLayout buttonsCheck = view.findViewById(R.id.checkRemember);
        //LinearLayout buttonsNext = view.findViewById(R.id.layout_next);
        CardView closeThing = view.findViewById(R.id.closetThing);

        //if ((card.getRepeatlevel() >= consts.START_TEST || card.getPracticeLevel() >= consts.START_TEST) &&
        //        (card.getRepeatlevel() <= consts.SENTENVE)){                                                // пользователю можно определять - знает ли он слово самому до первого показа теста
        //    buttonsCheck.setVisibility(View.GONE);
        //    buttonsNext.setVisibility(View.VISIBLE);
        //    closeThing.setVisibility(View.GONE);
        //}
        // если повторение больше предложения - то и шло оно к чертям, показываем просто карточки
        //else{
        buttonsCheck.setVisibility(View.VISIBLE);
        //buttonsNext.setVisibility(View.GONE);
        closeThing.setVisibility(View.VISIBLE);
        //}


        closeThing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCard(v);
            }
        });

        Button remember = view.findViewById(R.id.buttonRemember);
        Button forget = view.findViewById(R.id.buttonForget);
        remember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemember(v);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForget(v);
            }
        });
        view.findViewById(R.id.memButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMem(v);
            }
        });
        if (mainPlain.triggerRatio > mainPlain.sizeRatio){
            forget.setTextSize(mainPlain.sizeHeight/
                    (40f*mainPlain.multiple));
            forget.setHeight(mainPlain.sizeHeight/10);
            remember.setTextSize(mainPlain.sizeHeight/ (40f*mainPlain.multiple));
            remember.setHeight(mainPlain.sizeHeight/10);
            ((TextView)view.findViewById(R.id.open)).setTextSize(mainPlain.sizeHeight/(30f*mainPlain.multiple));
        }
    }


    protected void prepareAdapter(){
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new TextBlockAdapter(context, textBlocksList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(null);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        adapter.notifyDataSetChanged();
        adapter.setBlockListener(new TextBlockAdapter.BlockListener() {
            @Override
            public void onClick(int position) {
                repeatTTS.speak(textBlocksList.get(position).getText(), TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }


    public void deleteCard(final View view) {
        view.setClickable(false);
        Animation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(400);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    public void onForget(View view) {
        Ruler ruler = new Ruler(context, sqlIndex);
        ruler.reloadErrorStringWhileStudy(abstractCard);

        Fragment fragment = ruler.changeActivityWhileStudy(context);
        ruler.closeDatabase();
        ((mainPlain) getActivity()).slideFragment(fragment);
    }

    public void onRemember(View view) {
        Ruler ruler = new Ruler(context, sqlIndex);
        riseFromFall(abstractCard);
        Log.i("mainCardDay", Integer.toString(abstractCard.getRepetitionDat()));
        ruler.reloadRightStringWhileStudy(abstractCard);

        Fragment fragment = ruler.changeActivityWhileStudy(context);
        ruler.closeDatabase();
        ((mainPlain) getActivity()).slideFragment(fragment);

    }


    public void setEnableAllCardButtons(Boolean enabled){                                           // make all buttons unusable/usable
        view.findViewById(R.id.playSound).setClickable(enabled);
        view.findViewById(R.id.memButton).setClickable(enabled);
        view.findViewById(R.id.buttonRemember).setClickable(enabled);
        view.findViewById(R.id.buttonForget).setClickable(enabled);
        //view.findViewById(R.id.layout_next).setClickable(enabled);
    }

    public void addMem(View v){
        setEnableAllCardButtons(false);
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        final View my_custom_view = getLayoutInflater().inflate(R.layout.mem_add_layout, null);
        adb.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                setEnableAllCardButtons(true);
            }
        });

        if (mainPlain.sizeRatio < mainPlain.triggerRatio){
            ((TextView)my_custom_view.findViewById(R.id.association)).
                    setTextSize(mainPlain.sizeHeight/(30f*mainPlain.multiple));
            ((TextView)my_custom_view.findViewById(R.id.errorName)).
                    setTextSize(mainPlain.sizeHeight/(30f*mainPlain.multiple));
            ((TextView)my_custom_view.findViewById(R.id.yourAnswer)).
                    setTextSize(mainPlain.sizeHeight/(30f*mainPlain.multiple));
            ((Button)my_custom_view.findViewById(R.id.cancel)).
                    setTextSize(mainPlain.sizeHeight/(60f*mainPlain.multiple));
            ((Button)my_custom_view.findViewById(R.id.add)).
                    setTextSize(mainPlain.sizeHeight/(60f*mainPlain.multiple));
        }

        if (abstractCard.getMem() == null){
            my_custom_view.findViewById(R.id.association).setVisibility(View.GONE);
            my_custom_view.findViewById(R.id.divider).setVisibility(View.GONE);
        }
        else{
            TextView text = (my_custom_view.findViewById(R.id.association));
            text.setText(abstractCard.getMem());
        }


        adb.setView(my_custom_view);
        final AlertDialog ad = adb.create();
        my_custom_view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnableAllCardButtons(true);
                ad.cancel();
            }
        });

        my_custom_view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (my_custom_view.findViewById(R.id.yourAnswer));
                abstractCard.setMem(text.getText().toString());
                TransportSQLInterface transportSql =  MainTransportSQL.getTransport(sqlIndex,context);
                transportSql.reloadMem(abstractCard.getId(), abstractCard.getMem());
                prepareAdapter();
                ad.cancel();
                addMemInCard();
                adapter.notifyDataSetChanged();
            }
        });

        ad.show();
    }

    protected void addMemInCard(){
        textBlocksList.add(new TextBlock(25, abstractCard.getMem(), getResources().getColor(R.color.colorPrimary), true));
    }

}
