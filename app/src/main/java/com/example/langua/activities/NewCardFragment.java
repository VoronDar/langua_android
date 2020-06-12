package com.example.langua.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.ApproachManager.ApproachManager;
import com.example.langua.R;
import com.example.langua.SenteceCheck.SentenceChecker;
import com.example.langua.adapters.NewCardElementAdapter;
import com.example.langua.cards.VocabularyCard;
import com.example.langua.transportPreferences.transportPreferences;
import com.example.langua.Databases.transportSQL.MainTransportSQL;
import com.example.langua.Databases.transportSQL.TransportSQLInterface;
import com.example.langua.units.newCardElementUnit;

import java.util.ArrayList;

public class NewCardFragment extends Fragment {


    private NewCardElementAdapter adapter;
    private Context context;
    private boolean isChange;
    private String cardId;
    private ArrayList<newCardElementUnit> units;
    private VocabularyCard initCard;
    public enum ElIds{
        word,
        transl,
        transcript,
        meaning,
        meaningNative,
        example,
        exampleNative,
        train,
        trainNative,
        mem,
        group,
        part,
        help,
        synonym,
        antonym
    }
    private boolean isRight = true;

    public NewCardFragment(boolean isChange, String cardId){
        this.isChange = isChange;
        this.cardId = cardId;
    }
    public NewCardFragment(){
        this.isChange = false;
    }


        @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_card, container, false);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();

        if (mainPlain.sizeRatio < mainPlain.triggerRatio) {
            ((TextView) view.findViewById(R.id.type)).setTextSize(mainPlain.sizeHeight / (45f * mainPlain.multiple));
            ((TextView) view.findViewById(R.id.buttonAccept)).setTextSize(mainPlain.sizeHeight / (35f * mainPlain.multiple));
            ((TextView) view.findViewById(R.id.buttonCancel)).setTextSize(mainPlain.sizeHeight / (35f * mainPlain.multiple));
            ((Button) view.findViewById(R.id.buttonAccept)).setHeight(mainPlain.sizeHeight / 8);
            ((Button) view.findViewById(R.id.buttonCancel)).setHeight(mainPlain.sizeHeight / 8);
        }

        final Button accept = view.findViewById(R.id.buttonAccept);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        units = new ArrayList<>();
        adapter = new NewCardElementAdapter(isChange, view.getContext(), units);
        adapter.setHasStableIds(true);

        if (isChange) {
            ((TextView)view.findViewById(R.id.type)).setText(getString(R.string.change_card));


            TransportSQLInterface transportSQL =
                    MainTransportSQL.getTransport(ApproachManager.VOCABULARY_INDEX, context);
            initCard = (VocabularyCard) transportSQL.getString(cardId);
            transportSQL.closeDatabases();


            units.add(new newCardElementUnit(getString(R.string.word), initCard.getWord(), true, ElIds.word));
            units.add(new newCardElementUnit(getString(R.string.translate), initCard.getTranslate(), true, ElIds.transl));
            units.add(new newCardElementUnit(getString(R.string.meaningLearn), initCard.getMeaning(), true, ElIds.meaning));
            units.add(new newCardElementUnit(getString(R.string.meaningNative), initCard.getMeaningNative(), true, ElIds.meaningNative));
            units.add(new newCardElementUnit(getString(R.string.exampleLearn), initCard.getExample(), false, ElIds.example));
            units.add(new newCardElementUnit(getString(R.string.exampleNative), initCard.getExampleNative(), false, ElIds.exampleNative));
            String train = initCard.getTrain();


            int variantNum = 0;
            int index = units.size();
            if (SentenceChecker.isDecodable(train)){
                units.add(new newCardElementUnit(getString(R.string.exampleTrainLearn),
                        SentenceChecker.generateRightStringFromLib(train), true, ElIds.train, false));
            } else {
                while (train.contains("|")) {
                    if (variantNum == 0)
                        units.add(new newCardElementUnit(getString(R.string.exampleTrainLearn),
                                train.substring(0, train.indexOf("|")), true, ElIds.train));

                    //else
                    //units.add(new newCardElementUnit(getString(R.string.exampleTrainLearn) +
                    //     " (" + getString(R.string.variant) + ")",
                    //    train.substring(0, train.indexOf("|")), false, ElIds.train));
                    //train = train.substring(train.indexOf("|") + 1);
                    variantNum++;
                    break;

                }
                if (variantNum == 0)
                    units.add(new newCardElementUnit(getString(R.string.exampleTrainLearn), train, true, ElIds.train));
            }
            //else
            //    units.add(new newCardElementUnit(getString(R.string.exampleTrainLearn) +
            //            " (" + getString(R.string.variant) + ")", train, false, ElIds.train));
            adapter.setLastSentenceIndex(variantNum+index);

            units.add(new newCardElementUnit(getString(R.string.exampleTrainNative), initCard.getTrainNative(), true, ElIds.trainNative));
            units.add(new newCardElementUnit(getString(R.string.part), initCard.getPart(), false, ElIds.part));
            units.add(new newCardElementUnit(getString(R.string.group), initCard.getGroup(), false, ElIds.group));
            units.add(new newCardElementUnit(getString(R.string.synonym), initCard.getSynonym(), false, ElIds.synonym));
            units.add(new newCardElementUnit(getString(R.string.antonym), initCard.getAntonym(), false, ElIds.antonym));
            units.add(new newCardElementUnit(getString(R.string.help), initCard.getHelp(), false, ElIds.help));
            units.add(new newCardElementUnit(getString(R.string.transcription), initCard.getTranscription(), false, ElIds.transcript));
            units.add(new newCardElementUnit(getString(R.string.mem), initCard.getMem(), false, ElIds.mem));
            for (newCardElementUnit n: units){
                n.setValue(n.getPrevValue());
            }
        }
        else{
            units.add(new newCardElementUnit(getString(R.string.word), getString(R.string.wordPrev), true, ElIds.word));
            units.add(new newCardElementUnit(getString(R.string.translate), getString(R.string.translatePrev), true, ElIds.transl));
            units.add(new newCardElementUnit(getString(R.string.meaningLearn), getString(R.string.meaningLearnPrev), true, ElIds.meaning));
            units.add(new newCardElementUnit(getString(R.string.meaningNative), getString(R.string.meaningNativePrev), true, ElIds.meaningNative));
            units.add(new newCardElementUnit(getString(R.string.exampleLearn), getString(R.string.exampleLearnPrev), false, ElIds.example));
            units.add(new newCardElementUnit(getString(R.string.exampleNative), getString(R.string.exampleNativePrev), false, ElIds.exampleNative));
            units.add(new newCardElementUnit(getString(R.string.exampleTrainLearn), getString(R.string.exampleTrainLearnPrev), true, ElIds.train));
            units.add(new newCardElementUnit(getString(R.string.exampleTrainNative), getString(R.string.exampleTrainNativePrev), true, ElIds.trainNative));
            units.add(new newCardElementUnit(getString(R.string.part), getString(R.string.partPrev), false, ElIds.part));
            units.add(new newCardElementUnit(getString(R.string.group), getString(R.string.groupPrev), false, ElIds.group));
            units.add(new newCardElementUnit(getString(R.string.synonym), getString(R.string.synonymPrev), false, ElIds.synonym));
            units.add(new newCardElementUnit(getString(R.string.antonym), getString(R.string.antonymPrev), false, ElIds.antonym));
            units.add(new newCardElementUnit(getString(R.string.help), getString(R.string.helpPrev), false, ElIds.help));
            units.add(new newCardElementUnit(getString(R.string.transcription), getString(R.string.transcriptionPrev), false, ElIds.transcript));
            units.add(new newCardElementUnit(getString(R.string.mem), getString(R.string.memPrev), false, ElIds.mem));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        adapter.notifyDataSetChanged();
        final int AvailableId = transportPreferences.getAvailableId(context, ApproachManager.VOCABULARY_INDEX);
        final VocabularyCard card;
        if (!isChange)
            card = new VocabularyCard("M" + AvailableId);
        else
            card = new VocabularyCard(initCard.getId());
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSomething = false;
                isRight = true;
                boolean isWriteLearn = false;
                for (int i = 0; i < units.size(); i++){
                    newCardElementUnit n = units.get(i);
                    if (i == units.size()-1)
                    {
                        if (!isSomething){
                            createWarningWindow(getString(R.string.translate) + "'" + getString(R.string.or)
                                    + "'" + getString(R.string.meaning) + "'" + getString(R.string.or) + "'"+
                                    getString(R.string.meaningNative));
                            isRight = false;
                            break;}
                    }
                    if (n.getId() == ElIds.word && n.getValue() != null){
                            card.setWord(n.getValue());
                            continue;
                    }
                    else if (n.getId() == ElIds.train && n.getValue() != null){
                        if (card.getTrain() == null) {
                            card.setTrain(n.getValue());
                        }
                        else
                            card.setTrain(card.getTrain() +
                                    "|" + n.getValue());
                        isWriteLearn = true;
                        continue;
                    }
                    else if (n.getId() == ElIds.train && isWriteLearn)
                        continue;
                    else if (n.getId() == ElIds.trainNative && n.getValue() != null){
                        card.setTrainNative(n.getValue());
                        continue;
                    }
                    else if (n.getId() == ElIds.transl){
                        if (n.getValue() != null){
                            isSomething = true;
                            card.setTranslate(n.getValue());
                        }
                        continue;
                    }
                    else if (n.getId() == ElIds.meaning){
                        if (n.getValue() != null){
                        isSomething = true;
                        card.setMeaning(n.getValue());
                        }
                        continue;
                    }
                    else if (n.getId() == ElIds.meaningNative){
                        if (n.getValue() != null){
                            isSomething = true;
                            card.setMeaningNative(n.getValue());}
                        continue;
                    }
                    else if(n.getId() == ElIds.example){
                        card.setExample(n.getValue());
                        continue;
                    }
                    else if(n.getId() == ElIds.exampleNative){
                        card.setExampleNative(n.getValue());
                        continue;
                    }
                    else if(n.getId() == ElIds.mem){
                        card.setMem(n.getValue());
                        continue;
                    }
                    else if(n.getId() == ElIds.part){
                        card.setPart(n.getValue());
                        continue;
                    } else if(n.getId() == ElIds.group){
                        card.setGroup(n.getValue());
                        continue;
                    }
                    else if(n.getId() == ElIds.transcript){
                        card.setTranscription(n.getValue());
                        continue;
                    }
                    else if(n.getId() == ElIds.help){
                        card.setHelp(n.getValue());
                        continue;
                    }
                    else if(n.getId() == ElIds.synonym){
                        card.setSynonym(n.getValue());
                        continue;
                    }
                    else if(n.getId() == ElIds.antonym){
                        card.setAntonym(n.getValue());
                        continue;
                    }
                    Log.i("main", n.getName());
                    createWarningWindow(n.getName());
                    isRight = false;
                    break;
            }

                if (isRight){
                    TransportSQLInterface transportSql =
                            MainTransportSQL.getTransport(ApproachManager.VOCABULARY_INDEX, context);
                    if (isChange)
                        transportSql.deleteStringCommon(card.getId());
                    transportSql.addString(card);
                    transportSql.closeDatabases();
                    transportPreferences.setAvailableId(context,
                            ApproachManager.VOCABULARY_INDEX, AvailableId+1);
                    ((mainPlain) getActivity()).slideFragment(new LibraryFragment());

                }
        }});
        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((mainPlain) getActivity()).slideFragment(new LibraryFragment());
            }
        });
    }

    private void createWarningWindow(String message){

        final AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View my_custom_view = getLayoutInflater().inflate(R.layout.add_card_wrong, null);
        adb.setView(my_custom_view);

        TextView actionMessage = my_custom_view.findViewById(R.id.what_is_wrong);
        actionMessage.setText(getString(R.string.you_ought_to_fill) + "'" + message + "'");

        final AlertDialog ad = adb.create();

        Button cancel = my_custom_view.findViewById(R.id.next);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.cancel();
            }
        });
        if (mainPlain.sizeRatio < mainPlain.triggerRatio){
            cancel.setTextSize(mainPlain.sizeHeight/(40*mainPlain.multiple));
            actionMessage.setTextSize(mainPlain.sizeHeight/(25*mainPlain.multiple));
            ((TextView)my_custom_view.findViewById(R.id.errorName)).
                    setTextSize(mainPlain.sizeHeight/(40*mainPlain.multiple));
        }

        ad.show();
    }
}

