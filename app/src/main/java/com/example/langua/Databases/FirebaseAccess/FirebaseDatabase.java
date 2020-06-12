package com.example.langua.Databases.FirebaseAccess;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.langua.Databases.FirebaseAccess.pojo.Course;
import com.example.langua.Databases.Union.FirebaseTransporter;
import com.example.langua.cards.VocabularyCard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class FirebaseDatabase {
    private final String COURCES_COLLECTION_NAME = "colodes";
    private final String DB_TAG = "FDatabase";
    private final FirebaseTransporter transporter;

    public FirebaseDatabase(FirebaseTransporter transporter) {
        this.transporter = transporter;
    }

    public void getAllCources(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COURCES_COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot snapshot = task.getResult();
                            if (snapshot == null) {
                                Log.d(DB_TAG, "nothing");
                                transporter.onNoCourcesGet();
                                return;
                            }
                            transporter.onAllCourcesGet((ArrayList<Course>) snapshot.toObjects(Course.class));
                        }
                    }
                });
    }

    public ArrayList<VocabularyCard> getAllVocCardsFromCourse(String id) {
        return null;
    }
}


/*

условно -

I have a rabbit

подл: I -> сказуемое (have)
сказ - have -> дополнение
допол - rabbit


слова, такие как  have|has
 */