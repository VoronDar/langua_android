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

public class LibraryFragment extends Fragment {


    private VocabularyCardLibAdapter adapter;
    private static Context context;
    private ArrayList<VocabularyCardLibUnit> units;
    private int request_code = 1, FILE_SELECT_CODE =101;
    private TextView textView;
    private String TAG ="mainactivty";
    public String  actualfilepath="";


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
        context = view.getContext();

        if (mainPlain.sizeRatio < mainPlain.triggerRatio)
            ((TextView) view.findViewById(R.id.cards)).setTextSize(mainPlain.sizeHeight / 45f);

        TransportSQLInterface transportSQL = MainTransportSQL.getTransport(VOCABULARY_INDEX, context);


        //// FILL VOCABULARY CARDS /////////////////////////////////////////////////////////////////
        ArrayList<Card> getCards = transportSQL.getAllCardsFromCommon();
        transportSQL.closeDatabases();
        units = new ArrayList<>();
        for (Card n : getCards) {
            units.add(new VocabularyCardLibUnit(((VocabularyCard) n).getWord(),
                    ((VocabularyCard) n).getTranslate(), n.getId(), n.getRepeatlevel()));
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        //// FILL PHRASEOLOGY CARDS ////////////////////////////////////////////////////////////////
        getCards.clear();
        transportSQL = MainTransportSQL.getTransport(PHRASEOLOGY_INDEX, context);
        getCards.addAll(transportSQL.getAllCardsFromCommon());
        transportSQL.closeDatabases();
        for (Card n : getCards) {
            units.add(new VocabularyCardLibUnit(((PhraseologyCard) n).getWord(),
                    ((PhraseologyCard) n).getTranslate(), n.getId(), n.getRepeatlevel()));
        }
        ////////////////////////////////////////////////////////////////////////////////////////////


        //// PREPARE VIEW //////////////////////////////////////////////////////////////////////////
        adapter = new VocabularyCardLibAdapter(view.getContext(), units);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        if ((mainPlain.sizeRatio >= mainPlain.triggerRatio) || mainPlain.sizeWidth < smallWidth)
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        else
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        adapter.notifyDataSetChanged();
        ////////////////////////////////////////////////////////////////////////////////////////////


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
                openUserFile();
                //downloadFile();
                //((mainPlain) getActivity()).slideFragment(new ColodeFragment());
            }
        });
    }

    public void downloadFile() {
        StringBuilder allText = null;
        BufferedReader reader = null;
        try {
            Log.i("main", "prepare");
            reader = new BufferedReader(
                    new InputStreamReader(mainPlain.activity.getAssets().open("import.txt")));

            Log.i("main", "start_read");
            // do reading, usually loop until end of file reading
            String mLine;
            allText = new StringBuilder();
            while ((mLine = reader.readLine()) != null) {
                allText.append(mLine);
            }


        } catch (IOException e) {
            //log the exception
            return;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    if (allText != null)
                        readImportFile(allText);
                    else
                        Log.i("main", "errorFileRead");
                        // do something
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }


        public void readImportFile(StringBuilder allText){
        ArrayList<VocabularyCard> cards = new ArrayList<>();
        String Id = null;
        TransportSQLInterface transportSql = MainTransportSQL.getTransport(VOCABULARY_INDEX, context);
        int availableId = transportSql.getCardCount();
        final String importChange = "c";
        final String importAsk = "a";
        final String importUpdate = "u";
        final String importLeave = "l";
        String importType = importAsk;
        Log.i("main", "startAnalyzeFile");
            try {
                Stack<Character> stack = new Stack<>();
                StringBuilder block = new StringBuilder();
                StringBuilder indicate = new StringBuilder();
                VocabularyCard card = new VocabularyCard();

                final String id = "id";
                final String word = "w";
                final String translate = "t";
                final String meaning = "m";
                final String meanNative = "mn";
                final String transcript = "tr";
                final String exampleNative = "en";
                final String example = "e";
                final String synonym = "s";
                final String antonym = "a";
                final String group = "g";
                final String part = "pa";
                final String mem = "as";
                final String train = "p";
                final String trainNative = "pn";
                final String help = "h";
                final String levelRemember = "l";
                final String levelPractice = "lp";
                final String dayTrain = "d";



                int count = 1;
                Log.i("main", allText.toString());
                for (Character n : allText.toString().toCharArray()) {
                    if (stack.size() > 0 && stack.peek().equals('~')) {
                        if (n.equals('~'))
                            stack.pop();
                        continue;
                    }
                    switch (n) {
                        case '[':
                            //Log.i("main[", "OPEN");
                            if (Id == null && cards.size() == 0)
                                Id = "U";
                            else if (cards.size() == 0)
                                availableId = 1;
                            card = new VocabularyCard();
                        case '{':
                        case '<':
                            stack.add(n);
                            break;
                        case '#':
                            if (stack.size() == 0 || !stack.peek().equals('#'))
                                stack.add(n);
                            else{
                                String bl = block.toString();
                                Log.i("main", "done");
                                    switch (indicate.toString()) {
                                        case "":
                                            Id = block.toString();
                                            break;
                                        case word:
                                            card.setWord(bl);
                                            break;
                                        case translate:
                                            card.setTranslate(bl);
                                            break;
                                        case transcript:
                                            card.setTranscription(bl);
                                            break;
                                        case meaning:
                                            card.setMeaning(bl);
                                            break;
                                        case meanNative:
                                            card.setMeaningNative(bl);
                                            break;
                                        case example:
                                            card.setExampleLearn(bl);
                                            break;
                                        case exampleNative:
                                            card.setExampleTranslate(bl);
                                            break;
                                        case train:
                                            card.setColumnWriteSentence(bl);
                                            break;
                                        case trainNative:
                                            card.setColumnWriteSentenceNative(bl);
                                            break;
                                        case group:
                                            card.setGroup(bl);
                                            break;
                                        case part:
                                            card.setPart(bl);
                                            break;
                                        case help:
                                            card.setHelp(bl);
                                            break;
                                        case mem:
                                            card.setMem(bl);
                                            break;
                                        case antonym:
                                            card.setAntonym(bl);
                                            break;
                                        case synonym:
                                            card.setSynonym(bl);
                                            break;
                                        case levelRemember:
                                            card.setRepeatlevel(Integer.parseInt(bl));
                                            break;
                                        case levelPractice:
                                            card.setPracticeLevel(Integer.parseInt(bl));
                                            break;
                                        case dayTrain:
                                            card.setRepetitionDat(Integer.parseInt(bl));
                                            break;
                                        case id:
                                            card.setId(Id + bl);
                                            break;
                                        default:
                                            throw new NullPointerException("Indefined index - " + indicate.toString());
                                    }
                                block = new StringBuilder();
                                indicate = new StringBuilder();
                                stack.pop();
                            }
                            break;
                        case ']':
                            if (stack.peek().equals('[')) {
                                //Log.i("main", "endWord");
                                stack.pop();
                                if (card.getId() == null) {
                                    card.setId(Id + availableId++);
                                }
                                cards.add(card);
                                //Log.i("mainRead", Integer.toString(count++));
                                Log.i("mainRead", card.getId());
                                card = new VocabularyCard();
                                //Log.i("main-card", "newCard");
                            } else {
                                throw new NullPointerException("[");
                            }
                            break;
                        case '>':
                            if (stack.peek().equals('<')) {
                                //Log.i("main", "endCourse");
                                stack.pop();
                            } else
                                throw new NullPointerException("couldn't find simbol '<'");
                            break;
                        case '~':
                            if (stack.size() == 0)
                                stack.add('~');
                            else
                                throw new NullPointerException("couldn't find simbol '~");
                            break;
                        case '}':
                            if (stack.peek().equals('{')) {
                                    String bl = block.toString();
                                    if (bl.equals(importAsk) || bl.equals(importChange) ||
                                            bl.equals(importLeave))
                                    importType = block.toString();
                                block = new StringBuilder("");
                                stack.pop();
                            } else
                                throw new NullPointerException("{");
                            break;
                        case ' ':
                            if (!stack.peek().equals('#'))
                                break;
                        default:
                            if (stack.peek().equals('#') || stack.peek().equals('{'))
                                block.append(n);
                            else if (!stack.peek().equals('~'))
                                indicate.append(n);
                    }
                }
            } catch (NumberFormatException e){
                Log.i("main", "number analyze - " + e.toString());
                Toast.makeText(getContext(), getString(R.string.read_file_error) + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            } catch (NullPointerException e){
                Log.i("main", "simple exeption - " + e.toString());
                Toast.makeText(getContext(), getString(R.string.read_file_error) + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            } catch (Exception e){
                Log.i("main", "emptyStack");
                Toast.makeText(getContext(), getString(R.string.read_file_error), Toast.LENGTH_SHORT).show();
            }
            finally {
                Log.i("main", "end");
                Log.i("main", cards.toString());

                ArrayList<String> allId = transportSql.getAllCardsId();
                int count = 1;
                for (int i = 0; i < cards.size(); i++){
                    Log.i("mainProgress", Integer.toString(count++));
                    for (String n : allId) {
                        //Log.i("main-this_CARD", cards.get(i).getId());
                        if (n.equals(cards.get(i).getId())) {
                            //Log.i("main", "there is simular Id - " + n);
                            if (importType.equals(importLeave)){
                                cards.remove(i--);
                                break;
                            }
                            else if (importType.equals(importChange)){
                                VocabularyCard card = (VocabularyCard) transportSql.getString(n);
                                if (card.equals(cards.get(i))) {
                                    cards.remove(i--);
                                    Log.i("main", "leave");
                                    break;
                                }
                                else
                                    Log.i("main", "change");
                                if (!card.getWord().equals(cards.get(i).getWord())){
                                    ArrayList<Card> allSim = transportSql.getAllCardsByWord(cards.get(i).getWord());
                                    if (allSim.size() > 0){
                                        //Log.i("main", "there is several words - what's to do");
                                        // do dmt;
                                    }
                                }
                                transportSql.deleteStringCommon(n);
                                transportSql.addString(cards.get(i));
                                //Log.i("main", "change card - " + n);
                                cards.remove(i--);
                                break;
                            }
                            else if (importType.equals(importUpdate)){
                                VocabularyCard card = (VocabularyCard) transportSql.getString(n);
                                if (card.equals(cards.get(i))) {
                                    cards.remove(i--);
                                    break;
                                }
                                if (!card.getWord().equals(cards.get(i).getWord())) {
                                    ArrayList<Card> allSim = transportSql.getAllCardsByWord(cards.get(i).getWord());
                                    if (allSim.size() > 0) {
                                        //Log.i("main", "there is several words - what's to do");
                                        // do dmt;
                                    }
                                }
                                transportSql.deleteStringCommon(n);
                                transportSql.addString(cards.get(i));
                                cards.remove(i--);
                                //Log.i("main", "change card - " + n);
                                break;
                            }
                            else{
                                //Log.i("main", "act - ");
                            }
                        }
                    }
                }

                for (int i = 0; i < cards.size(); i++){
                    ArrayList<Card> allSim = transportSql.getAllCardsByWord(cards.get(i).getWord());
                    if (allSim.size() > 0)
                    {
                        //Log.i("main", "new");
                    }
                    else
                        transportSql.addString(cards.get(i));
                }



                transportSql.closeDatabases();
                Log.i("mainEnd", "END");
                // LATER

                /*
                for (int i = 0; i < cards.size(); i++){
                    for (String n : allWords) {
                        if (n.equals(cards.get(i).getWord())) {
                            Log.i("main", cards.get(i).getWord());
                        }
                    }
                }

                 */
            }
        }


    private void openUserFile(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // start runtime permission
            boolean hasPermission =( ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission){
                Log.e(TAG, "get permision   ");
                ActivityCompat.requestPermissions( mainPlain.activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, request_code);
            }else {
                Log.e(TAG, "get permision-- already granted ");
                showFileChooser();
            }
        }else {
            //readfile();
            showFileChooser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:{
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //readfile();
                    showFileChooser();
                }else {
                    Toast.makeText(getContext(),getString(R.string.permission_blocked), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(  Intent.createChooser(intent, "Select a File to Upload"),  FILE_SELECT_CODE);
        } catch (Exception e) {
            Log.e(TAG, " choose file error "+e.toString());
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e(TAG, " result is "+ data + "  uri  "+ data.getData()+ " auth "+ data.getData().getAuthority()+ " path "+ data.getData().getPath());
        String fullerror ="";
        if (requestCode == FILE_SELECT_CODE){
            if (resultCode == mainPlain.RESULT_OK){
                try {
                    InputStream stream = null;
                    String tempID= "", id ="";
                    Uri uri = data.getData();
                    if (uri == null)
                        throw new Exception(getString(R.string.empty_file));
                    Log.e(TAG, "file auth is "+uri.getAuthority());
                    fullerror = fullerror +"file auth is "+uri.getAuthority();
                    if (uri.getAuthority().equals("media")){
                        tempID =   uri.toString();
                        tempID = tempID.substring(tempID.lastIndexOf("/")+1);
                        id = tempID;
                        Uri contenturi = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        String selector = MediaStore.Images.Media._ID+"=?";
                        actualfilepath = getColunmData( contenturi, selector, new String[]{id}  );
                    }else if (uri.getAuthority().equals("com.android.providers.media.documents")){
                        tempID = DocumentsContract.getDocumentId(uri);
                        String[] split = tempID.split(":");
                        String type = split[0];
                        id = split[1];
                        Uri contenturi = null;
                        if (type.equals("image")){
                            contenturi = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        }else if (type.equals("video")){
                            contenturi = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        }else if (type.equals("audio")){
                            contenturi = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
                        String selector = "_id=?";
                        actualfilepath = getColunmData( contenturi, selector, new String[]{id}  );
                    } else if (uri.getAuthority().equals("com.android.providers.downloads.documents")){
                        tempID =   uri.toString();
                        tempID = tempID.substring(tempID.lastIndexOf("/")+1);
                        id = tempID;
                        Uri contenturi = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                        // String selector = MediaStore.Images.Media._ID+"=?";
                        actualfilepath = getColunmData( contenturi, null, null  );


                    }else if (uri.getAuthority().equals("com.android.externalstorage.documents")){
                        tempID = DocumentsContract.getDocumentId(uri);
                        String[] split = tempID.split(":");
                        String type = split[0];
                        id = split[1];
                        Uri contenturi = null;
                        if (type.equals("primary")){
                            actualfilepath=  Environment.getExternalStorageDirectory()+"/"+id;
                        }
                    }
                    File myFile = new File(actualfilepath);
                    // MessageDialog dialog = new MessageDialog(Home.this, " file details --"+actualfilepath+"\n---"+ uri.getPath() );
                    // dialog.displayMessageShow();
                    String temppath =  uri.getPath();

                    if (temppath == null)
                        throw new Exception(getString(R.string.empty_file));

                    if (temppath.contains("//")){
                        temppath = temppath.substring(temppath.indexOf("//")+1);
                    }
                    Log.e(TAG, " temppath is "+ temppath);
                    fullerror = fullerror +"\n"+" file details -  "+actualfilepath+"\n --"+ uri.getPath()+"\n--"+temppath;
                    if ( actualfilepath.equals("") || actualfilepath.equals(" ")) {
                        myFile = new File(temppath);
                    }else {
                        myFile = new File(actualfilepath);
                    }
                    Log.e(TAG, " myfile is "+ myFile.getAbsolutePath());
                    readfile(myFile);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getColunmData( Uri uri, String selection, String[] selectarg){
        String filepath ="";
        Cursor cursor;
        String colunm = "_data";
        String[] projection = {colunm};
        cursor =  mainPlain.activity.getContentResolver().query( uri, projection, selection, selectarg, null);
        if (cursor!= null){
            cursor.moveToFirst();
            Log.e(TAG, " file path is "+  cursor.getString(cursor.getColumnIndex(colunm)));
            filepath = cursor.getString(cursor.getColumnIndex(colunm));
        }
        if (cursor!= null)
            cursor.close();
        return  filepath;
    }
    private void readfile(File file){
        // File file = new File(Environment.getExternalStorageDirectory(), "mytextfile.txt");
        StringBuilder builder = new StringBuilder();
        Log.e("main", "read start");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine())!=null){
                builder.append(line);
            }
            br.close();

            readImportFile(builder);

        }catch (Exception e){
            Log.e("main", " error is "+e.toString());
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        //Log.e("main", " read text is "+ builder.toString());
    }

}
