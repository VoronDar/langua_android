package com.example.langua.Databases.Union;

import com.example.langua.Databases.FirebaseAccess.FirebaseDatabase;
import com.example.langua.Databases.FirebaseAccess.pojo.Course;
import com.example.langua.Databases.transportSQL.TransportSQLCourse;
import com.example.langua.Databases.transportSQL.TransportSQLVocabulary;
import com.example.langua.R;
import com.example.langua.Reader.FileReadable;
import com.example.langua.cards.Card;
import com.example.langua.cards.VocabularyCard;
import com.example.langua.ruler.Ruler;

import java.util.ArrayList;

/*

отвечает за передачу между fb sql и view

для чего используются базы данных


выполнять в другом потоке


подгрузка курсов:

1. при инициализации
если пользователь зашел в приложение в первый раз, то загружается информация о всех курсах
(зы - поставить другой preference на это)
, а именно - имя(id), текущая версия,


не чаще, чем раз в день при входе в программу идет проверка обновлений
с фб загружается информация о курсах и их версиях - эта ин





 */
public class DBTransporter implements FirebaseTransporter, SqlTransporter, ViewTransporter {


    /*

    как работает - getAllCourses -> onNo/AllCourcesGet

     */

    private final TransportRunnable view;
    private final TransportSQLCourse transportSql;
    private final TransportSQLVocabulary transportSQLVocabulary;
    private final FirebaseDatabase db;

    private final String REFRESH_DAY_PREFERENCE = "refresh_day";
    private final String PREFERENCE_NAME = "transporter";


    public DBTransporter(TransportRunnable view){
        this.view = view;
        this.transportSql = new TransportSQLCourse(view.getContext());
        this.db = new FirebaseDatabase(this);
        this.transportSQLVocabulary = new TransportSQLVocabulary(view.getContext());
    }

    public void geAllCourses(){

        /*

        важно - максимально курсы обновляются раз в день

         */

        if (view.getContext().getSharedPreferences(PREFERENCE_NAME, 0).getInt
                (REFRESH_DAY_PREFERENCE, 0) < Ruler.getAbsoluteDay()) {

            view.getContext().getSharedPreferences(PREFERENCE_NAME, 0).edit().
                    putInt(REFRESH_DAY_PREFERENCE, Ruler.getAbsoluteDay()).apply();


            db.getAllCources();
        }
    }

    @Override
    public void onNoCourcesGet() {
        view.onSomethingGoesWrong(R.string.cources_get_failed);
    }

    @Override
    // здесь нужно обновить курсы, если обновление было, или загрузить их с начала
    // загружаются по факту только изображения для курсов и определяется, нужно ли обновить
    // активные курсы. Активные - в смысле те, которые у пользователя есть в загруженных
    public void onAllCourcesGet(ArrayList<Course> courses) {
        ArrayList<Course> lastupdated = transportSql.getAllCources();
        for (Course n: courses){
            for (Course j: lastupdated){

                if (j.getId().equals(n.getId())){

                    // если курс был обновлен, обновляем все карты - картинку заново никогда не подгружаем,
                    // если только таковая не отсутствует вообще
                    //if (j.getVersion() < n.getVersion()){
                    //    ArrayList<VocabularyCard> cards = db.getAllVocCardsFromCourse(j.getId());
                    //    //transportSQLVocabulary.getAllCardsFromCourse(cards.get(0).getId());
                    //    transportSQLVocabulary.getCard
                    }
                }
            }
        }

    }
