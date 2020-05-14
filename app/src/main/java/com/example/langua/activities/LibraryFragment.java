package com.example.langua.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.mbms.MbmsErrors;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.langua.R;
import com.example.langua.Reader.ColodeImporter;
import com.example.langua.Reader.FileReadable;
import com.example.langua.adapters.VocabularyCardLibAdapter;
import com.example.langua.cards.Card;
import com.example.langua.cards.PhraseologyCard;
import com.example.langua.cards.VocabularyCard;
import com.example.langua.declaration.consts;
import com.example.langua.transportSQL.TransportSQLInterface;
import com.example.langua.transportSQL.TransportSQLPhraseology;
import com.example.langua.units.VocabularyCardLibUnit;
import com.example.langua.transportSQL.MainTransportSQL;
import com.example.langua.transportSQL.TransportSQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;

import static com.example.langua.ApproachManager.ApproachManager.PHRASEOLOGY_INDEX;
import static com.example.langua.ApproachManager.ApproachManager.VOCABULARY_INDEX;
import static com.example.langua.activities.mainPlain.smallWidth;

public class LibraryFragment extends Fragment implements FileReadable {

    private ArrayList<VocabularyCardLibUnit> units;
    private ColodeImporter importer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library, container, false);

    }



    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TransportSQLInterface transportSQL = MainTransportSQL.getTransport(VOCABULARY_INDEX, view.getContext());
        importer = new ColodeImporter(this);


        //// SET WIDGET SIZE////////////////////////////////////////////////////////////////////////
        if (mainPlain.sizeRatio < mainPlain.triggerRatio)
            ((TextView) view.findViewById(R.id.cards)).setTextSize(mainPlain.sizeHeight / 45f);
        ////////////////////////////////////////////////////////////////////////////////////////////




        //// FILL VOCABULARY CARDS /////////////////////////////////////////////////////////////////
        ArrayList<Card> getCards = transportSQL.getAllCardsFromCommon();
        transportSQL.closeDatabases();
        units = new ArrayList<>();
        for (Card n : getCards) {
            units.add(new VocabularyCardLibUnit(((VocabularyCard) n).getWord(),
                    ((VocabularyCard) n).getTranslate(), n.getId(), n.getRepeatlevel()));
        }
        ////////////////////////////////////////////////////////////////////////////////////////////




        /* - ОТМЕНЕНО ПОКА ЧТО
        //// FILL PHRASEOLOGY CARDS ////////////////////////////////////////////////////////////////
        getCards.clear();
        transportSQL = MainTransportSQL.getTransport(PHRASEOLOGY_INDEX, view.getContext());
        getCards.addAll(transportSQL.getAllCardsFromCommon());
        transportSQL.closeDatabases();
        for (Card n : getCards) {
            units.add(new VocabularyCardLibUnit(((PhraseologyCard) n).getWord(),
                    ((PhraseologyCard) n).getTranslate(), n.getId(), n.getRepeatlevel()));
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
         */


        //// PREPARE VIEW //////////////////////////////////////////////////////////////////////////
        VocabularyCardLibAdapter adapter = new VocabularyCardLibAdapter(view.getContext(), units);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        if ((mainPlain.sizeRatio >= mainPlain.triggerRatio) || mainPlain.sizeWidth < smallWidth)
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        else
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        adapter.notifyDataSetChanged();
        ////////////////////////////////////////////////////////////////////////////////////////////



        //// SET ACTIONS ///////////////////////////////////////////////////////////////////////////
        adapter.setBlockListener(new VocabularyCardLibAdapter.BlockListener() {
            @Override
            public void onClick(int position) {
                ((mainPlain) getActivity()).slideFragment(new NewCardFragment(true,
                        units.get(position).getID()));
            }
        });

        view.findViewById(R.id.addNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((mainPlain) getActivity()).slideFragment(new NewCardFragment());
            }
        });

        view.findViewById(R.id.addColode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importer.importFile();
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ColodeImporter.REQUEST_CODE){
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    importer.showFileChooser();
                }else {
                    Toast.makeText(getContext(),getString(R.string.permission_blocked), Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ColodeImporter.FILE_SELECT_CODE){
            if (resultCode == mainPlain.RESULT_OK) {
                importer.openFile(data.getData());
            }
        }
    }

    @Override
    public void showError(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update() {
        ((mainPlain) getActivity()).slideFragment(new LibraryFragment());
    }
}
