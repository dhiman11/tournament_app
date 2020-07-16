package com.dhimanstudio.the_killer_zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.dhimanstudio.the_killer_zone.adapter.MygamesAdapter;
import com.dhimanstudio.the_killer_zone.model.Games;
import com.dhimanstudio.pubg_multiplayer.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<Games> list;
    MygamesAdapter adapter;

//    private ImageButton image_log_out;


    public void onfregment_callback(){
        Log.d("calling_fregment","Function");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.gamerecycleview);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
//        image_log_out = (ImageButton) findViewById(R.id.image_log_out);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();


        FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                            adapter = new MygamesAdapter(MainActivity.this,list);
                            Toast.makeText(MainActivity.this, "Loading recycle view", Toast.LENGTH_SHORT).show();
                            recyclerView.setAdapter(adapter);


                        } else {
                            Log.d("MSGGGG", "Error getting documents: ", task.getException());
                        }
                    }
                });




    }



    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser == null){
            sendUserToLogin();
        }
    }

    private void sendUserToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }
}
