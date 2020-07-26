package com.dhimanstudio.the_killer_zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhimanstudio.pubg_multiplayer.R;
import com.dhimanstudio.the_killer_zone.model.Profile;
import com.dhimanstudio.the_killer_zone.model.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ConfirmTournamentJoin extends AppCompatActivity {

    private Button pay_now_button;
    private ImageView match_image;
    private String name,wallet_amount,user_name;
    private Number total_amount;
    private Number amount_left;
    private String entry_fee;
    private String tournament_date_m;
    private String image;
    private String sessionusername;

    private String tournament_id;
    private TextView product_name,match_date,pay_amount,service_charge,total_charge,payamount_confirm,mywallet,warning;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Session session;//global variable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_tournament_join);

        /*declare imageview*/
        match_image = (ImageView) findViewById(R.id.match_image);
        /*declare imageview*/

        /*declare textview*/
            product_name = (TextView) findViewById(R.id.product_name);
            match_date = (TextView) findViewById(R.id.match_date);
            pay_amount = (TextView) findViewById(R.id.pay_amount);
            payamount_confirm = (TextView) findViewById(R.id.payamount_confirm);
            service_charge = (TextView) findViewById(R.id.service_charge);
            total_charge = (TextView) findViewById(R.id.total_charge);
            mywallet = (TextView) findViewById(R.id.mywallet);
            warning = (TextView) findViewById(R.id.warning);


        pay_now_button = (Button) findViewById(R.id.pay_now_button);

        /*declare textview*/

        Bundle b = getIntent().getExtras();
        if(b != null)
            name = b.getString("name");
            entry_fee = b.getString("entry_fee");
            tournament_date_m = b.getString("tournament_date_m");
            image = b.getString("image");
            tournament_id = b.getString("tournament_id");


        product_name.setText(name);
        match_date.setText(tournament_date_m);
        pay_amount.setText("₹"+entry_fee);
        payamount_confirm.setText("₹"+entry_fee);
        Picasso.get().load(image).into(match_image);


        //// FIND SERVICE TAX
//        Number wallet  = Integer.valueOf(wallet_amount);
        Number paid_amount  = Integer.valueOf(entry_fee);
        Number service_tax = paid_amount.intValue() * 20/100;
        service_charge.setText("₹"+service_tax.toString());
        total_amount  = paid_amount.intValue()+service_tax.byteValue();
        total_charge.setText("₹"+total_amount.toString());
        /*Amount left*/

        /*Total amount*/

        load_wallet_data();


        /* Pay now*/
        pay_now_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay_now_button.setEnabled(false);
                Number wallet_total =  Integer.valueOf(wallet_amount);
                Number entry_fee_here =  Integer.valueOf(entry_fee);

                amount_left = wallet_total.intValue()  - entry_fee_here.intValue();

                Map<String, Object> user_data = new HashMap<>();
                user_data.put("wallet_amount", amount_left);

                DocumentReference update_user = db.collection("Users").document(user_id);
                update_user.update(user_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            recordjoinedtournament();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ConfirmTournamentJoin.this);
                            builder1.setMessage("You joined match successfully .");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            Intent paymentintent = new Intent(ConfirmTournamentJoin.this, HomeActivity.class);
                                            startActivity(paymentintent);
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }else{
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ConfirmTournamentJoin.this);
                            builder1.setMessage("Something wrong . Try again");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            Intent paymentintent = new Intent(ConfirmTournamentJoin.this, HomeActivity.class);
                                            startActivity(paymentintent);
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        }



                    }


                });
            }
        });
    }

    private void recordjoinedtournament() {

//
//        try {
//
//        }catch (Exception e){
//              user_name  = "-";
//        }

        try {
            session = new Session(getApplicationContext()); //in oncreate
            sessionusername = session.getusename();
        }catch (Exception e){
            sessionusername  = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        }

        Map<String, Object> tournamament_joined_data = new HashMap<>();
        tournamament_joined_data.put("joined", true);
        tournamament_joined_data.put("entry_fee_paid", entry_fee);
        tournamament_joined_data.put("tournament_id", tournament_id);
        tournamament_joined_data.put("user_id", user_id);
        tournamament_joined_data.put("username", sessionusername);
        CollectionReference addtournament = db.collection("Tournament_joined");
        addtournament
                .add(tournamament_joined_data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

            }

        });
    }


    /* Load wallet data */
    private void load_wallet_data() {

        DocumentReference docRef = db.collection("Users").document(user_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Profile profile_data =  document.toObject(Profile.class);
                        wallet_amount = profile_data.getWallet_amount().toString();
                        mywallet.setText("₹"+wallet_amount.toString());

                        Number wallet_total = (Integer) profile_data.getWallet_amount();
                        Log.d("condition",total_amount.toString()+wallet_total.toString());


                        ///WORKING HERE //////////////////SECURITY IMPORTANT
                        if(total_amount.intValue() > wallet_total.intValue()){
                            warning.setText("Out of cash . Please recharge your wallet");
                            pay_now_button.setEnabled(false);
                        }else{
                            warning.setText("Good to go");
                            pay_now_button.setEnabled(true);
                        }
                    }


                }else {
                    Log.d("SUCCESSLOL", "No such document");
                }
            }
        });

    }








}
