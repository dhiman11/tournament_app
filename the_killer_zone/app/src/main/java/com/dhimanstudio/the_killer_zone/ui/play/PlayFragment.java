package com.dhimanstudio.the_killer_zone.ui.play;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.dhimanstudio.the_killer_zone.model.Tournament_list;
import com.dhimanstudio.pubg_multiplayer.R;
import com.dhimanstudio.the_killer_zone.adapter.MygamesListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlayFragment extends Fragment {


    private View playrootView;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView mRecyclerPlayView;
    MygamesListAdapter adapter;

    ArrayList<Tournament_list> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        playrootView = (View)  inflater.inflate(R.layout.fragment_play, container, false);
        mRecyclerPlayView = (RecyclerView) playrootView.findViewById(R.id.upcoming_games_recycleview);
        mRecyclerPlayView.setLayoutManager( new LinearLayoutManager(getActivity()));

        /*AUTH INITIALISE */
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        /*DATA BASE INITIALIZE*/


        /*GET Tournament list*/
        Timestamp currentDate = Timestamp.now();
        db.collection("Tournaments")
                .orderBy("tournament_start_date")
                .whereGreaterThan("tournament_start_date",currentDate.toDate())
                .get()

                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list = new ArrayList<Tournament_list>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Tournament_list p = document.toObject(Tournament_list.class);
                                list.add(p);
                            }
                            adapter = new MygamesListAdapter(getActivity(),list);
                            mRecyclerPlayView.setAdapter(adapter);

                        } else {
                            Log.d("MSGGGG", "Error getting documents: ", task.getException());

                        }
                    }
                });



        playrootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });


        return playrootView;
    }




}