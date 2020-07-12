package com.example.pubg_multiplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GameDetail extends AppCompatActivity {


    private String document_key;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private ImageView image_view;
    private TextView game_name,game_map_val,game_val,team_val,server_name,entry_fees,particpant,per_kill_str,winning_price,match_date_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        Bundle b = getIntent().getExtras();
        if(b != null)
            document_key = b.getString("doc");



        DocumentReference docRef = db.collection("Tournaments").document(document_key);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Integer per_kill =  document.getDI("per_kill");
//                        document.getString("per_kill");


                        String entry_fee = (String) document.getString("entry_fee");
                        String game = (String)  document.getString("game");
                        String game_id = (String)  document.getString("game_id");
                        String game_map = (String)  document.getString("game_map");
                        String image = (String)  document.getString("image");
                        String name = (String)  document.getString("name");
                        String participants = (String)  document.getString("participants");

                        String winning_price_val = (String)   document.getString("winning_price");
                        String room_detail = (String)  document.getString("room_detail");
                        String server = (String)  document.getString("server");
                        String team = (String)  document.getString("team");
                        Timestamp tournament_start_date = (Timestamp)  document.getTimestamp("tournament_start_date");


                        /////////////
                        game_name = findViewById(R.id.game_title);
                        game_map_val = findViewById(R.id.game_map_val);
                        game_val = findViewById(R.id.game_val_textview);
                        team_val = findViewById(R.id.team_val);
                        server_name = findViewById(R.id.server_name);
                        entry_fees = findViewById(R.id.entry_fees);
                        particpant = findViewById(R.id.particpant);
                        per_kill_str = findViewById(R.id.per_kill_val);
                        winning_price = findViewById(R.id.winning_price_val);


                        match_date_val =  findViewById(R.id.match_date);

                        game_name.setText(name);

                        System.out.println(per_kill);

                        game_map_val.setText(game_map);
                        game_val.setText(game);
                        team_val.setText(team);
                        server_name.setText(server);
                        entry_fees.setText(entry_fee);
                        particpant.setText(participants);
                        per_kill_str.setText(per_kill);
                        winning_price.setText(winning_price_val);

                        /////SET UPCOMING DATAE
                        Date date = new Date(tournament_start_date.getSeconds());

                        match_date_val.setText(get_date_oftournament(date));


                        image_view = findViewById(R.id.image_view);
                        Picasso.get().load(image).into(image_view);

                    }
                }else {
                    Log.d("SUCCESSLOL", "No such document");
                }
            }
        });



//        Toast.makeText(this, document_key, Toast.LENGTH_SHORT).show();



    }





    private String get_date_oftournament(Date date) {
        String formattedDate;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("d,MMMM h:mm,a", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            formattedDate = sdf.format(date);
        }catch (Exception e){
            SimpleDateFormat sdf = new SimpleDateFormat("d,MMMM h:mm,a", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            formattedDate = "";
        }

        return formattedDate;
    }






}