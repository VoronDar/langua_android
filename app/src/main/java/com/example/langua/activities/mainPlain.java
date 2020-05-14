
/*

    Перая запускаемая активность
    Главное меню - содержит фрагменты для плана дня, библиотеки, свободной практики, настроек.

    Иерархия:
    Ruler - определяет что делать дальше и направляет к новой активности
    CardRepeatManager - управляет показом кард (конкретно - repeatDay repeatLevel и practiceLevel)
    TransportSQL - отвечает только за транспортировку данных из баз данных в Ruler и обратно
        TransportSql            -   абстрактный класс родитель
        MainTransportSql        -   фабрика
        TransportSqlInterface   -   интерфейс
        TransportSqlVocabulary  -   конкретный класс для доступа к лексическим картам
        TransportSqlPhraseology -   конкретный класс для доступа к фразеологии

    База данных:

    Contract - обозначения для базы данных
        VocabularyContract
        PhraseologyContract
    DBHelper (_|todayRepeatVocabulary|today(study)Vocabulary) - сами базы данных

    активности:

    MainActivity    - карточка
    CardPhraseology - карточка для фразеологии
    testSentence    - тесты
    testSound
    testTranslate
    testTranslateNative
    testWriting
    mainPlain       - главное меню

    фрагменты этой активности содержат постфикс    Fragment
    DayPlanFragment         - фрагмент плана дня
    FreePoolFragment        - фрагмент свободной практики
    LibraryFragment         - фрагмент библиотеки карточек
    SettingsFragment        - фрагмент настройки

    TrasportActivities - содержит статические функции перемещения между активностями
    ActivitiesUtils - общие функции для активностей, которые никак не касаются перемещения между
        активностями

    вспомогательные классы:
    VocabularyCard - карточка карты слова

    классы для передачи данных в адаптеры
    toDoUnit            - в фрагменте DayPlanFragment
    TextBlock           - в активности MainActivity
    ButtonLetterUnit    - для активностей написания слов - для кнопочек
    Им же соотвествуют адаптеры

    transportPreferences - статические функции транспортировки преференсов
    consts - список обозначений


 */




package com.example.langua.activities;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.langua.R;
import com.example.langua.activities.statistics.statistics;
import com.example.langua.ruler.Ruler;
import com.example.langua.transportPreferences.transportPreferences;


import java.util.Locale;

import static com.example.langua.activities.utilities.ActivitiesUtils.removeTitleBar;
import static com.example.langua.activities.utilities.ActivitiesUtils.setOrientation;


public class mainPlain extends AppCompatActivity implements TextToSpeech.OnInitListener {


    //static{
    //    System.loadLibrary("base_algorythm_lib");
    //}
    //public native String getCPPString();


    private FragmentManager fragmentManager;
    private Fragment fragment;
    public static int sizeWidth;
    public static int sizeHeight;
    public static float sizeRatio;
    public static final float triggerRatio = 1.65f;
    public static final int smallWidth = 500;
    public FragmentTransaction ft;
    public static TextToSpeech repeatTTS;
    public static mainPlain activity;
    public static int multiple;

    private NotificationManager manager;
    private final int NotifyId = -2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.i("cpp", getCPPString());

        if (activity == null)
            activity = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_plain);
        setOrientation(this);
        removeTitleBar(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        repeatTTS = new TextToSpeech(this, this);


        ////// GET SCREEN SIZE /////////////////////////////////////////////////////////////////////
        Point size =  new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        sizeWidth = size.x;
        sizeHeight = size.y;
        sizeRatio = (float)sizeHeight/(float)sizeWidth;
        Log.i("main-Ratio", Float.toString(sizeRatio));
        if (mainPlain.sizeRatio < mainPlain.triggerRatio) {
            multiple = 2;
            if (mainPlain.sizeHeight < 1300)
                multiple = 1;
        }
        ////////////////////////////////////////////////////////////////////////////////////////////


        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction ft = fragmentManager.beginTransaction();

        fragment = new DayPlanFragment();
        ft.add(R.id.fragment_container, fragment);
        ft.commit();

        ImageView dayPlan =findViewById(R.id.planIcon);
        dayPlan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                slideFragment(new DayPlanFragment());
            }
        });

        ImageView library =findViewById(R.id.libIcon);
        library.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                slideFragment(new LibraryFragment());
            }
        });


        /* для тестирования
        library.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                reload();
                return true;
            }
        });

         */


        ImageView pool =findViewById(R.id.poolIcon);
        pool.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                slideFragment(new PoolFragment());
            }
        });

        ImageView stat =findViewById(R.id.statIcon);
        stat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                slideFragment(new Statistcs());
            }
        });


        ImageView settings =findViewById(R.id.settingsIcon);
        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /* change it!

                 */
                //setNewDay();
                slideFragment(new SettingsFragment());

            }
        });
        /* для тестирования
        settings.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //reload();
                setNewDay();
                putOffAction();
                return true;
            }
        });

         */

        Ruler.setTodayTables(this);
        putOffAction();
    }


    public void slideFragment(Fragment fragment){
        this.fragment = fragment;
        ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_right, R.anim.slide_left);
        ft.replace(R.id.fragment_container, this.fragment);
        ft.commit();

    }

    //////// CHANGE ACTIVITY ///////////////////////////////////////////////////////////////////////
    public void closeAndChangeActivity(Intent intent){
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
    }
    public void goToTheRepeating(int index){
            Ruler ruler = new Ruler(this, index);
            Fragment fragment = ruler.changeActivityWhileStudy(this);
            ruler.closeDatabase();
            slideFragment(fragment);
    }
    public void goTothePractice(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new PracticeSelectPoolFragment();
        fragmentTransaction.setCustomAnimations(R.anim.slide_right, R.anim.slide_left);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void goToTheSpecialSettings(Fragment fragment){
        slideFragment(fragment);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////




    //////// TESTING METHODS ///////////////////////////////////////////////////////////////////////
    private void reload() {
        transportPreferences.setStarted(this, false);
        transportPreferences.setExtraDayCount(this, 0);
        transportPreferences.setLastDayComming(this, 0);
        Ruler.regenerateDatabase(this);
        Ruler.setTodayTables(this);
    }
    private void setNewDay()
    {
        transportPreferences.setExtraDayCount(this, transportPreferences.getExtraDayCount(this) + 1);
        Ruler.setTodayTables(this);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////



    //////// STATICS IMPACT ////////////////////////////////////////////////////////////////////////
    private void putOffAction(){
        final statistics stat = statistics.getInstance(this);
        if (!stat.isEmpty()){
            Log.i("main", "bla!");
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        View my_custom_view = getLayoutInflater().inflate(R.layout.do_stat, null);
        adb.setView(my_custom_view);

        TextView actionMessage = my_custom_view.findViewById(R.id.message);
        actionMessage.setText(stat.getActionName());

            adb.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Log.i("main", "here");
                    if (!stat.isEmpty())
                        putOffAction();
                    else{
                        slideFragment(new DayPlanFragment());
                    }
                }
            });

        final AlertDialog ad = adb.create();

        Button cancel = my_custom_view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stat.cancelComand();
                ad.cancel();
            }
        });
        Button accept = my_custom_view.findViewById(R.id.accept);
            accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stat.executeCommand();
                ad.cancel();
            }
        });

            if (sizeRatio < triggerRatio){
                cancel.setTextSize(sizeHeight/(40*multiple));
                accept.setTextSize(sizeHeight/(40*multiple));
                actionMessage.setTextSize(sizeHeight/(30*multiple));
            }

            ad.show();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onDestroy() {

        if (repeatTTS != null) {
            repeatTTS.stop();
            repeatTTS.shutdown();
        }
        super.onDestroy();
    }


    ////////// TEXT TO SPEECH //////////////////////////////////////////////////////////////////////
    @Override
    public void onInit(int status) {
        Log.i("text-to-speech", "init!");
        if (status == TextToSpeech.SUCCESS) {
            //Locale locale = new Locale("uc");
            int result = repeatTTS.setLanguage(Locale.UK);
            //int result = repeatTTS.setLanguage(locale);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Извините, этот язык не поддерживается");
            }
            Toast.makeText(this, "TTS - completed", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("TTS", "Ошибка!");
            Toast.makeText(this, "TTS - warning", Toast.LENGTH_SHORT).show();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////



}
