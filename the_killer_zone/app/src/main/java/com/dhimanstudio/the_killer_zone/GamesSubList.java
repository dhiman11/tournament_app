package com.dhimanstudio.the_killer_zone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.dhimanstudio.pubg_multiplayer.R;

public class GamesSubList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_sub_list);

        Bundle b = getIntent().getExtras();
        Log.d("id", String.valueOf(b.getString("doc")));
    }
}