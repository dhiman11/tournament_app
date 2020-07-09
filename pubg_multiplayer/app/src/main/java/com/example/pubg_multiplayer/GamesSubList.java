package com.example.pubg_multiplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class GamesSubList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_sub_list);

        Bundle b = getIntent().getExtras();
        ;
        Log.d("id", String.valueOf(b.getString("doc")));
    }
}