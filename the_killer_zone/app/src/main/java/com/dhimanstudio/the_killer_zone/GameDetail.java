package com.dhimanstudio.the_killer_zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dhimanstudio.pubg_multiplayer.R;
import com.dhimanstudio.the_killer_zone.adapter.Mydetailjoined;
import com.dhimanstudio.the_killer_zone.adapter.MygamesListAdapter;
import com.dhimanstudio.the_killer_zone.adapter.MyjoinedAdapter;
import com.dhimanstudio.the_killer_zone.model.Session;
import com.dhimanstudio.the_killer_zone.model.Tournament_detail;
import com.dhimanstudio.the_killer_zone.model.Tournament_joined;
import com.dhimanstudio.the_killer_zone.model.Tournament_list;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GameDetail extends AppCompatActivity {


    private String document_key,entry_fee,participants,user_id_string;
    private Button join_button;
    private Boolean joinedornot;
    private ProgressBar progressBar;
    ArrayList<Tournament_joined> list;

    private RecyclerView mRecyclerPlayView;
    Mydetailjoined adapter;

    private ObjectAnimator progressAnimator;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private ImageView image_view,game;
    private TextView game_name,game_map_val,game_val,team_val,server_name,entry_fees,particpant,per_kill_str,winning_price,match_date_val,progresstext;

    private String name,image,tournament_date_m;
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

                        Tournament_detail Tournament_list =  document.toObject(Tournament_detail.class);

                        String per_kill_val = Tournament_list.getPer_kill().toString();
                        participants = Tournament_list.getParticipants().toString();
                        String winning_price_val =  Tournament_list.getWinning_price().toString();

                        final String entry_fee = (String) document.getString("entry_fee");
                        String game = (String)  document.getString("game");
                        String game_id = (String)  document.getString("game_id");
                        String game_map = (String)  document.getString("game_map");
                          image = (String)  document.getString("image");
                          name = (String)  document.getString("name");



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

//                        System.out.println(per_kill);

                        game_map_val.setText(game_map);
                        game_val.setText(game);
                        team_val.setText(team);
                        server_name.setText(server);
                        entry_fees.setText(entry_fee);
//                        particpant.setText(participants);
                        per_kill_str.setText(per_kill_val);
                        winning_price.setText(winning_price_val);

                        /////SET UPCOMING DATAE
                        Date date = new Date(tournament_start_date.getSeconds());

                        match_date_val.setText(get_date_oftournament(date));
                        tournament_date_m = (String) get_date_oftournament(date).toString();

                        image_view = findViewById(R.id.image_view);
                        Picasso.get().load(image).into(image_view);



                        check_if_already_joined();


                        join_button = (Button) findViewById(R.id.join_button);
                        join_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                join_button.setEnabled(false);
                                Intent paymentintent = new Intent(GameDetail.this, ConfirmTournamentJoin.class);
                                paymentintent.putExtra("tournament_id",document_key);
                                paymentintent.putExtra("entry_fee",entry_fee);
                                paymentintent.putExtra("tournament_date_m", tournament_date_m);
                                paymentintent.putExtra("name", name);
                                paymentintent.putExtra("image", image);
                                startActivity(paymentintent);

                            }
                        });



                    }
                }else {
                    Log.d("SUCCESSLOL", "No such document");
                }
            }
        });




    }






    /* GET DATE */
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




    /* check if already joined */
    private void check_if_already_joined() {
        final CollectionReference Tournament_joined = db.collection("Tournament_joined");
        Tournament_joined
                .whereEqualTo("tournament_id",document_key)
//                .whereEqualTo("user_id",user_id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int count = 0;
                    int exist_or_not = 0;

                    list = new ArrayList<Tournament_joined>();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Tournament_joined join = document.toObject(Tournament_joined.class);
                        list.add(join);

                        user_id_string = join.getUser_id().trim();

                        if(user_id_string.equals(user_id)){
                            exist_or_not++;
                            Log.d("the user in",join.getUser_id());

                        }
                        count++;
                    }


                    mRecyclerPlayView = (RecyclerView) findViewById(R.id.playerjoinedrecycle);
                    adapter = new Mydetailjoined(GameDetail.this,list);
                    mRecyclerPlayView.setAdapter(adapter);



                    Integer participants_int = Integer.valueOf(participants);


                    /*MANAGE PROGRESS BAR */
                    progressBar = (ProgressBar) findViewById(R.id.progressBarshow);
                    progresstext = (TextView) findViewById(R.id.progresstext);
                    progressBar.setMax(participants_int);
                    progressBar.setProgress(count);
                    progressBar.setScaleY(3f);

                    /*end*/
                    particpant.setText(count+"/"+participants);
                    progresstext.setText(count+"/"+participants);


                    disable_button_if_false(count,participants_int,exist_or_not);




                }
            }
        });


    }

    private boolean disable_button_if_false(int count, Integer participants_int, int exist_or_not) {


        /* IF I ALREADY JOINED */

        if(exist_or_not > 0){
            join_button.setEnabled(false);
            join_button.setText("Already joined");
            join_button.setBackgroundColor(Color.parseColor("#476282"));
            return false;
        }else{
            join_button.setEnabled(true);
        }

        if(count>=participants_int){
            join_button.setEnabled(false);
            join_button.setText("Match Full");
            join_button.setBackgroundColor(Color.parseColor("#476282"));
            return false;
        }else{
            join_button.setEnabled(true);
        }




        return true;
    }


}