package com.dhimanstudio.the_killer_zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dhimanstudio.pubg_multiplayer.R;
import com.dhimanstudio.the_killer_zone.adapter.MygamesListAdapter;
import com.dhimanstudio.the_killer_zone.model.Tournament_list;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GamesSubList extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView mRecyclerPlayView;
    MygamesListAdapter adapter;
    ArrayList<Tournament_list> list;
    private View gamerootView;
    private String doc_id;
    private ProgressBar mProgressBar;
    private Toolbar toolbar_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_games_sub_list);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar_games_list2);
        mProgressBar.setVisibility(View.VISIBLE);


        mRecyclerPlayView = (RecyclerView)  findViewById(R.id.recyle_games);

        mRecyclerPlayView.setLayoutManager( new LinearLayoutManager(this));
        toolbar_menu = (Toolbar)  findViewById(R.id.toolbar_menu);


        Bundle b = getIntent().getExtras();
        doc_id =  b.getString("doc");



        Timestamp currentDate = Timestamp.now();

        CollectionReference collectionOfProducts = db.collection("Tournaments");

        collectionOfProducts

                .whereEqualTo("game_id",doc_id)
                .whereGreaterThan("tournament_start_date",currentDate.toDate())
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            int count = 0;
                            list = new ArrayList<Tournament_list>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Tournament_list p = document.toObject(Tournament_list.class);
                                list.add(p);
                                count++;
                            }
                            adapter = new MygamesListAdapter(GamesSubList.this,list);
                            mRecyclerPlayView.setAdapter(adapter);
                            mProgressBar.setVisibility(View.INVISIBLE);
                            /* count the data */
                            if(count ==0){
                                Toast.makeText(GamesSubList.this, "Sorry No tournament available", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("MSGGGG", "Error getting documents: ", task.getException());
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });


                /* toolbar_menu */

        toolbar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GamesSubList.super.onBackPressed();
            }
        });

    }








}