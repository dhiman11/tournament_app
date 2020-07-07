package com.example.pubg_multiplayer.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pubg_multiplayer.LoginActivity;
import com.example.pubg_multiplayer.MainActivity;
import com.example.pubg_multiplayer.R;
import com.example.pubg_multiplayer.adapter.MygamesAdapter;
import com.example.pubg_multiplayer.model.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    FirebaseFirestore db;
    RecyclerView mRecyclerView;
    ArrayList<Games> list;
    MygamesAdapter adapter;
    private ImageButton image_log_out;
    private HomeViewModel homeViewModel;
    private LinearLayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_main, container, false);
//        rootView.setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.gamerecycleview);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        image_log_out = (ImageButton) rootView.findViewById(R.id.image_log_out);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        image_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                sendUserToLogin();
            }
        });

        db.collection("Games")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list = new ArrayList<Games>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Games p = document.toObject(Games.class);
                                list.add(p);

                            }

                            adapter = new MygamesAdapter(getActivity(),list);
                            Toast.makeText(getActivity(), "Loading recycle view", Toast.LENGTH_SHORT).show();
                            mRecyclerView.setAdapter(adapter);


                        } else {
                            Log.d("MSGGGG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return rootView;



    }



    @Override
    public void onStart() {
        super.onStart();
        if(mCurrentUser == null){
            sendUserToLogin();
        }
    }

    private void sendUserToLogin() {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }


}