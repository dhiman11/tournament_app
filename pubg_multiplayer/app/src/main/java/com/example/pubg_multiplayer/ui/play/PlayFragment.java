package com.example.pubg_multiplayer.ui.play;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.pubg_multiplayer.R;
import com.example.pubg_multiplayer.adapter.MygamesAdapter;
import com.example.pubg_multiplayer.adapter.MygamesListAdapter;
import com.example.pubg_multiplayer.model.Games;
import com.example.pubg_multiplayer.model.Tournament_list;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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

        db.collection("Tournaments")
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


        return playrootView;
    }
}