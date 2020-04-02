package com.example.langua.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langua.ApproachManager.ApproachManager;
import com.example.langua.R;
import com.example.langua.adapters.SettingsAdapter;
import com.example.langua.adapters.VocabularyCardLibAdapter;
import com.example.langua.cards.Card;
import com.example.langua.cards.PhraseologyCard;
import com.example.langua.cards.VocabularyCard;
import com.example.langua.declaration.consts;
import com.example.langua.transportPreferences.transportPreferences;
import com.example.langua.transportSQL.MainTransportSQL;
import com.example.langua.transportSQL.TransportSQLInterface;
import com.example.langua.units.SettingUnit;
import com.example.langua.units.VocabularyCardLibUnit;
import com.google.android.material.internal.VisibilityAwareImageButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class SettingsFragment extends Fragment {

    public enum SettingsIndex{
        common,                                                                                     // the main page
        VocabularyPace,
        PhraseologyPace,
        VocabularyApproach,                                                                         // audio or read
        VocabularyViewApproach,                                                                     // image-meaning or translate
        MeaningLanguage,
        ExampleLanguage,
        NewWordsCount
    }


    private SettingsAdapter adapter;
    private Context context;
    private ArrayList<SettingUnit> units;
    private SettingsIndex settingsIndex = SettingsIndex.common;

    public SettingsFragment(){
    }
    public SettingsFragment(SettingsIndex index){
        settingsIndex = index;
    }

        @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (settingsIndex == SettingsIndex.common)
            return inflater.inflate(R.layout.fragment_settings, container, false);
        else if (settingsIndex == SettingsIndex.NewWordsCount)
            return inflater.inflate(R.layout.new_cards_count, container, false);
        else
            return inflater.inflate(R.layout.select_setting, container, false);

    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();

        if (mainPlain.sizeRatio < mainPlain.triggerRatio){
            if ((view.findViewById(R.id.settingsText)) != null)
                ((TextView)view.findViewById(R.id.settingsText)).setTextSize(mainPlain.sizeHeight/45f);
            else{
                ((TextView)view.findViewById(R.id.settingsName)).setTextSize(mainPlain.sizeHeight/(35f*mainPlain.multiple));
                ((TextView)view.findViewById(R.id.buttonAccept)).setTextSize(mainPlain.sizeHeight/(35f*mainPlain.multiple));
                ((TextView)view.findViewById(R.id.buttonCancel)).setTextSize(mainPlain.sizeHeight/(35f*mainPlain.multiple));
                ((Button)view.findViewById(R.id.buttonAccept)).setHeight(mainPlain.sizeHeight/8);
                ((Button)view.findViewById(R.id.buttonCancel)).setHeight(mainPlain.sizeHeight/8);
                ((TextView)view.findViewById(R.id.spacer)).setHeight(mainPlain.sizeHeight/8);
                if (view.findViewById(R.id.command) != null){
                    ((TextView)view.findViewById(R.id.command)).setTextSize(mainPlain.sizeHeight/(25f*mainPlain.multiple));
                    ((TextView)view.findViewById(R.id.command2)).setTextSize(mainPlain.sizeHeight/(25f*mainPlain.multiple));
                    ((TextView)view.findViewById(R.id.description)).setTextSize(mainPlain.sizeHeight/(30f*mainPlain.multiple));
                }
                else{
                    ((TextView)view.findViewById(R.id.description)).setTextSize(mainPlain.sizeHeight/(33f*mainPlain.multiple));
                    ((TextView)view.findViewById(R.id.warning)).setTextSize(mainPlain.sizeHeight/(33f*mainPlain.multiple));
                    ((TextView)view.findViewById(R.id.editText)).setTextSize(mainPlain.sizeHeight/(10f*mainPlain.multiple));
                    ((TextView)view.findViewById(R.id.newWords)).setTextSize(mainPlain.sizeHeight/(35f*mainPlain.multiple));
                }

            }
        }

        boolean isHorisontal = false;                                                               // if select blocks are horisontal and lie on other blocks
        boolean isManySelected = false;                                                             // if the user can select several blocks

        SettingUnit.DoSomehtingInteface doSomehtingIntefaceBoth = null;
        SettingUnit.DoSomehtingInteface doSomehtingIntefaceNone = null;
        String BothDescription = null;
        String NoneDescription = "";

        final Button accept = view.findViewById(R.id.buttonAccept);
        final TextView settingsName = view.findViewById(R.id.settingsName);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        if (recyclerView != null)
            recyclerView.setHasFixedSize(true);
        units = new ArrayList<>();
        adapter = new SettingsAdapter(view.getContext(), units);


        if (settingsIndex == SettingsIndex.common){

            int imageId;
            if (transportPreferences.getVocabularyApproach(context) == ApproachManager.VOC_AUDIO_APPROACH)
                imageId = R.drawable.icon_vocabular_audio;
            else
                imageId = R.drawable.icon_vocabular_read;

            units.add(new SettingUnit(imageId, getResources().getString(R.string.VocabularyApproach), new SettingUnit.OpenAlertInterface() {
                @Override
                public Fragment openAlert() {
                    return new SettingsFragment(settingsIndex.VocabularyApproach);
                }
            }));

            if (transportPreferences.getMeaningPriority(context) == consts.PRIORITY_NEVER)
                imageId = R.drawable.icon_vocabular_view_translate;
            else if (transportPreferences.getTranslatePriority(context) == consts.PRIORITY_NEVER)
                imageId = R.drawable.icon_vocabular_view_meaning;
            else
                imageId = R.drawable.icon_vocabular_view;


            units.add(new SettingUnit(imageId, getResources().getString(R.string.VocabularyViewApproach), new SettingUnit.OpenAlertInterface() {
                @Override
                public Fragment openAlert() {
                    return new SettingsFragment(settingsIndex.VocabularyViewApproach);
                }
            }));

            if (transportPreferences.getMeaningLanguage(context) == consts.LANGUAGE_NEW)
                imageId = R.drawable.icon_description_new;
            else if (transportPreferences.getMeaningLanguage(context) == consts.LANGUAGE_NATIVE)
                imageId = R.drawable.icon_description_native;
            else
                imageId = R.drawable.icon_description;

            units.add(new SettingUnit(imageId, getResources().getString(R.string.DefenitionLanguage), new SettingUnit.OpenAlertInterface() {
                @Override
                public Fragment openAlert() {
                    return new SettingsFragment(settingsIndex.MeaningLanguage);
                }
            }));


            if (transportPreferences.getExampleAvailability(context) == consts.AVAILABILITY_OFF)
                imageId = R.drawable.icon_examples;
            else if (transportPreferences.getExampleLanguage(context) == consts.LANGUAGE_NEW)
                imageId = R.drawable.icon_examples_new;
            else if (transportPreferences.getExampleLanguage(context) == consts.LANGUAGE_NATIVE)
                imageId = R.drawable.icon_examples_native;
            else
                imageId = R.drawable.icon_examples;


            units.add(new SettingUnit(imageId, getResources().getString(R.string.ExampleLanguage), new SettingUnit.OpenAlertInterface() {
                @Override
                public Fragment openAlert() {
                    return new SettingsFragment(settingsIndex.ExampleLanguage);
                }
            }));


            units.add(new SettingUnit(R.drawable.icon_velocity, getResources().getString(R.string.VocabularyCardCountPerDay), new SettingUnit.OpenAlertInterface() {
                @Override
                public Fragment openAlert() {
                    return new SettingsFragment(settingsIndex.NewWordsCount);
                }
            }));



            adapter.setBlockListener(new SettingsAdapter.BlockListener() {
                @Override
                public void onClick(int position) {
                    ((mainPlain) getActivity()).goToTheSpecialSettings(units.get(position).openAlert());
                }
            });

        }
        else if (settingsIndex == SettingsIndex.NewWordsCount){
            isHorisontal = true;
            final EditText count = view.findViewById(R.id.editText);
            count.setText(Integer.toString(transportPreferences.getPaceVocabulary(getContext())));
            onSetPaceAction(Integer.parseInt(count.getText().toString()));
            count.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    Log.i("main", "KLICK!!");
                    if (!count.getText().toString().equals(""))
                        onSetPaceAction(Integer.parseInt(count.getText().toString()));
                    else
                        onSetPaceAction(0);
                    return false;
                }
            });
            view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((mainPlain) getActivity()).goToTheSpecialSettings(new SettingsFragment(settingsIndex.common));
                }
            });
            view.findViewById(R.id.buttonAccept).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  transportPreferences.setPaceVocabulary(getContext(), Integer.parseInt(count.getText().toString()));
                    transportPreferences.setCriticalValue(getContext(), ApproachManager.VOCABULARY_INDEX, Integer.parseInt(count.getText().toString())*3);
                    ((mainPlain) getActivity()).goToTheSpecialSettings(new SettingsFragment(settingsIndex.common));
                }
            });
        }
        else{


            if (settingsIndex == SettingsIndex.VocabularyApproach) {
                settingsName.setText(getString(R.string.VocabularyApproach));


                boolean isAudio = (transportPreferences.getVocabularyApproach(context) == ApproachManager.VOC_AUDIO_APPROACH);

                units.add(new SettingUnit(R.drawable.icon_vocabular_audio, getResources().getString(R.string.VocabularyAudioAp), isAudio, getString(R.string.VocabularyAudioApDescript), new SettingUnit.DoSomehtingInteface() {
                    @Override
                    public void doSomething() {
                        transportPreferences.setVocabularyApproach(context, ApproachManager.VOC_AUDIO_APPROACH);
                    }
                }));
                units.add(new SettingUnit(R.drawable.icon_vocabular_read, getResources().getString(R.string.VocabularyTraditionalAp), !(isAudio), getString(R.string.VocabularyTraditionalApDescript), new SettingUnit.DoSomehtingInteface() {
                    @Override
                    public void doSomething() {
                        transportPreferences.setVocabularyApproach(context, ApproachManager.VOC_READ_APPROACH);
                    }
                }));


            }
            else if (settingsIndex == SettingsIndex.VocabularyViewApproach) {
                settingsName.setText(getString(R.string.VocabularyViewApproach));

                isManySelected = true;

                units.add(new SettingUnit(R.drawable.icon_vocabular_view_translate, getResources().getString(R.string.VocabularyTranslateViewAp), false, getString(R.string.VocabularyTranslateViewApDescript), new SettingUnit.DoSomehtingInteface() {
                    @Override
                    public void doSomething() {
                        transportPreferences.setImagePriority(context, consts.PRIORITY_NEVER);
                        transportPreferences.setMeaningPriority(context, consts.PRIORITY_NEVER);
                        transportPreferences.setTranslatePriority(context, consts.PRIORITY_MAX);
                    }
                }));

                units.add(new SettingUnit(R.drawable.icon_vocabular_view_meaning, getResources().getString(R.string.VocabularyMeaningViewAp), false, getString(R.string.VocabularyMeaningViewApDescript), new SettingUnit.DoSomehtingInteface() {
                    @Override
                    public void doSomething() {
                        transportPreferences.setImagePriority(context, consts.PRIORITY_MAX);
                        transportPreferences.setMeaningPriority(context, consts.PRIORITY_MAX);
                        transportPreferences.setTranslatePriority(context, consts.PRIORITY_NEVER);
                    }
                }));

                doSomehtingIntefaceBoth = new SettingUnit.DoSomehtingInteface() {
                    @Override
                    public void doSomething() {
                        transportPreferences.setImagePriority(context, consts.PRIORITY_MAX);
                        transportPreferences.setMeaningPriority(context, consts.PRIORITY_MAX);
                        transportPreferences.setTranslatePriority(context, consts.PRIORITY_MAX);
                    }
                };
                BothDescription = getString(R.string.VocabularyBothViewApDescript);
            }
            else if (settingsIndex == SettingsIndex.MeaningLanguage) {
                settingsName.setText(getString(R.string.DefenitionLanguage));

                isManySelected = true;

                units.add(new SettingUnit(R.drawable.icon_description_new, getResources().getString(R.string.LanguageNew), false, getString(R.string.DefenitionLanguageNewDescript), new SettingUnit.DoSomehtingInteface() {
                    @Override
                    public void doSomething() {
                        transportPreferences.setMeaningLanguage(context, consts.LANGUAGE_NEW);
                    }
                }));
                units.add(new SettingUnit(R.drawable.icon_description_native, getResources().getString(R.string.LanguageNative), false, getString(R.string.DefenitionLanguageNativeDescript), new SettingUnit.DoSomehtingInteface() {
                    @Override
                    public void doSomething() {
                        transportPreferences.setMeaningLanguage(context, consts.LANGUAGE_NATIVE);
                    }
                }));

                doSomehtingIntefaceBoth = new SettingUnit.DoSomehtingInteface() {
                    @Override
                    public void doSomething() {
                        transportPreferences.setMeaningLanguage(context, consts.LANGUAGE_BOTH);
                    }
                };
                BothDescription = getString(R.string.DefenitionLanguageBothDescript);
            }

            else if (settingsIndex == SettingsIndex.ExampleLanguage) {

                settingsName.setText(getString(R.string.ExampleLanguage));

                isManySelected = true;

                units.add(new SettingUnit(R.drawable.icon_examples_new, getResources().getString(R.string.LanguageNew), false, getString(R.string.ExampleLanguageNewDescript), new SettingUnit.DoSomehtingInteface() {
                    @Override
                    public void doSomething() {
                        transportPreferences.setExampleLanguage(context, consts.LANGUAGE_NEW);
                        transportPreferences.setExampleAvailability(context, consts.AVAILABILITY_ON);
                        Log.i("main", "new");
                    }
                }));
                units.add(new SettingUnit(R.drawable.icon_examples_native, getResources().getString(R.string.LanguageNative), false, getString(R.string.ExampleLanguageNativeDescript), new SettingUnit.DoSomehtingInteface() {
                    @Override
                    public void doSomething() {
                        transportPreferences.setExampleLanguage(context, consts.LANGUAGE_NATIVE);
                        transportPreferences.setExampleAvailability(context, consts.AVAILABILITY_ON);
                        Log.i("main", "native");
                    }
                }));

                doSomehtingIntefaceBoth = new SettingUnit.DoSomehtingInteface() {
                    @Override
                    public void doSomething() {
                        transportPreferences.setExampleLanguage(context, consts.LANGUAGE_BOTH);
                        transportPreferences.setExampleAvailability(context, consts.AVAILABILITY_ON);
                        Log.i("main", "both");
                    }
                };
                doSomehtingIntefaceNone = new SettingUnit.DoSomehtingInteface() {
                    @Override
                    public void doSomething() {
                        transportPreferences.setExampleAvailability(context, consts.AVAILABILITY_OFF);
                        Log.i("main", "none");
                    }
                };

                BothDescription = getString(R.string.DefenitionLanguageBothDescript);
                NoneDescription = getString(R.string.ExampleLanguageNoneDescript);
            }



            if (units.size()==2) {
                recyclerView.setVisibility(View.GONE);
                view.findViewById(R.id.linearLayout).setVisibility(View.VISIBLE);
                isHorisontal = true;

                final TextView description = view.findViewById(R.id.description);
                final TextView command1 = view.findViewById(R.id.command);
                final TextView command2 = view.findViewById(R.id.command2);
                final ImageView indicator = view.findViewById(R.id.indicator);
                final ImageView indicator2 = view.findViewById(R.id.indicator2);
                final ImageView image1 = view.findViewById(R.id.image);
                final ImageView image2 = view.findViewById(R.id.image2);

                command1.setText(units.get(0).getCommand());
                command2.setText(units.get(1).getCommand());
                image1.setImageResource(units.get(0).getImageID());
                image2.setImageResource(units.get(1).getImageID());

                final boolean finalIsManySelected = isManySelected;
                final String finalBothDescription = BothDescription;
                final String finalNoneDescription = NoneDescription;
                view.findViewById(R.id.setting_block_one).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!finalIsManySelected) {
                            description.setText(units.get(0).getDescription());
                            units.get(0).setChecked(true);
                            units.get(1).setChecked(false);
                            command2.setTextColor(context.getResources().getColor(R.color.colorWhiteSynonym));
                            command1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                            indicator.setVisibility(View.VISIBLE);
                            indicator2.setVisibility(View.GONE);
                        } else {
                            if (units.get(0).isChecked()) {
                                description.setText(units.get(1).getDescription());
                                units.get(0).setChecked(false);
                                indicator.setVisibility(View.GONE);
                                command1.setTextColor(context.getResources().getColor(R.color.colorWhiteSynonym));
                            } else {
                                description.setText(units.get(0).getDescription());
                                units.get(0).setChecked(true);
                                indicator.setVisibility(View.VISIBLE);
                                command1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                            }
                        }
                        if (units.get(0).isChecked() && units.get(1).isChecked()) {
                            description.setText(finalBothDescription);
                        } else if (!units.get(0).isChecked() && !units.get(1).isChecked()) {
                            if (finalNoneDescription.equals(""))
                                accept.setVisibility(View.GONE);
                            description.setText(finalNoneDescription);
                        } else
                            accept.setVisibility(View.VISIBLE);
                    }
                });

                view.findViewById(R.id.setting_block_two).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!finalIsManySelected) {
                            description.setText(units.get(1).getDescription());
                            units.get(1).setChecked(true);
                            units.get(0).setChecked(false);
                            command2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                            command1.setTextColor(context.getResources().getColor(R.color.colorWhiteSynonym));
                            indicator2.setVisibility(View.VISIBLE);
                            indicator.setVisibility(View.GONE);
                        } else {
                            if (units.get(1).isChecked()) {
                                description.setText(units.get(0).getDescription());
                                units.get(1).setChecked(false);
                                indicator2.setVisibility(View.GONE);
                                command2.setTextColor(context.getResources().getColor(R.color.colorWhiteSynonym));
                            } else {
                                description.setText(units.get(1).getDescription());
                                units.get(1).setChecked(true);
                                indicator2.setVisibility(View.VISIBLE);
                                command2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                            }
                        }
                        if (units.get(0).isChecked() && units.get(1).isChecked()) {
                            description.setText(finalBothDescription);
                        } else if (!units.get(0).isChecked() && !units.get(1).isChecked()) {
                            if (finalNoneDescription.equals(""))
                                accept.setVisibility(View.GONE);
                            description.setText(finalNoneDescription);
                        } else
                            accept.setVisibility(View.VISIBLE);


                    }
                });

                if (!isManySelected || (isManySelected && doSomehtingIntefaceNone== null)) {
                    if (units.get(0).isChecked())
                        view.findViewById(R.id.setting_block_one).callOnClick();
                    else
                        view.findViewById(R.id.setting_block_two).callOnClick();
                }
                else{
                    description.setText(NoneDescription);
                }

            }
            else{
                adapter.setBlockListener(new SettingsAdapter.BlockListener() {
                    @Override
                    public void onClick(int position) {
                        ((TextView)view.findViewById(R.id.description)).setText(units.get(position).getDescription());
                        for (int i = 0; i < units.size(); i++){
                            if (units.get(i).isChecked())
                                units.get(i).setChecked(false);
                        }
                        units.get(position).setChecked(true);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            if (transportPreferences.isStarted(context)){
                Button cancel = view.findViewById(R.id.buttonCancel);
                cancel.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((mainPlain) getActivity()).goToTheSpecialSettings(new SettingsFragment(settingsIndex.common));
                    }
                });
            }

            final SettingUnit.DoSomehtingInteface finalDoSomehtingInteface = doSomehtingIntefaceBoth;

            final boolean finalIsManySelected1 = isManySelected;
            final SettingUnit.DoSomehtingInteface finalDoSomehtingIntefaceNone = doSomehtingIntefaceNone;
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalIsManySelected1 && units.get(0).isChecked() && units.get(1).isChecked())
                        finalDoSomehtingInteface.doSomething();
                    else if(finalDoSomehtingIntefaceNone!= null && !units.get(0).isChecked() && !units.get(1).isChecked())
                        finalDoSomehtingIntefaceNone.doSomething();
                    else{
                        for (int i = 0; i < units.size(); i++) {
                            if (units.get(i).isChecked() == true) {
                                units.get(i).doSomething();
                                break;
                            }
                        }
                    }

                    if(transportPreferences.isStarted(context)){
                        ((mainPlain) getActivity()).goToTheSpecialSettings(new SettingsFragment(settingsIndex.common));
                    }
                    else{
                        // change it!
                    }
                }
            });


        }



        if (!isHorisontal) {
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(adapter);
            recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
            adapter.notifyDataSetChanged();
            }
        }

        private void onSetPaceAction(int pace){
            final int lowerStopTrigger = 0;
            final int lowerNormalTrigger = 7;
            final int upperNormalTrigger = 50;
            final int upperWarningTrigger = 200;
            final int upperStopTrigger = 500;
            TextView description = getView().findViewById(R.id.description);
            TextView warning = getView().findViewById(R.id.warning);
            Button accept = getView().findViewById(R.id.buttonAccept);
            accept.setVisibility(View.VISIBLE);
            description.setText(getString(R.string.paceYouCanLearn) + (pace*7) + " " +
                    getWordEnd(pace*7) + getString(R.string.pacePerWeek) + (pace*30) +
                    getString(R.string.pacePerMonth) + pace*365 + getString(R.string.pacePerYear));

            if (pace <= lowerStopTrigger || pace >= upperStopTrigger){
                warning.setText(getString(R.string.paceNever));
                description.setText(getString(R.string.paceNever));
                warning.setTextColor(getResources().getColor(R.color.colorAccent));
                accept.setVisibility(View.GONE);
            }
            else if (pace <= lowerNormalTrigger){
                warning.setText(getString(R.string.paceToSmall));
            warning.setTextColor(getResources().getColor(R.color.colorOrange));}
            else if (pace <= upperNormalTrigger){
                warning.setText(getString(R.string.paceNormal));
            warning.setTextColor(getResources().getColor(R.color.colorPrimary));}
            else if (pace <= upperWarningTrigger){
                warning.setText(getString(R.string.paceHigh));
                warning.setTextColor(getResources().getColor(R.color.colorOrange));
                }
            else{
                warning.setText(getString(R.string.paceReallyHigh));
            warning.setTextColor(getResources().getColor(R.color.colorOrange));}
        }

    public String getWordEnd(int number) {
        String currentLanguage = Locale.getDefault().getDisplayLanguage();
        if (currentLanguage.toLowerCase().contains("en")) {
            if (number == 1)
                return getString(R.string.paceWord);
            else
                return getString(R.string.paceWords);
        }
        if (number % 10 == 0 || number % 10 >= 5 || ((number + 100) % 100 >= 10                     // говнокод для определения окончания слов после числительных
                && (number + 100) % 100 <= 20))
            return getString(R.string.paceWordsRuMuch);
        else if (number % 10 == 1)
            return getString(R.string.paceWordsRuOne);
        else
            return getString(R.string.paceWordsRuSeveral);

    }

    }

