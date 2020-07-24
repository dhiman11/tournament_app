package com.dhimanstudio.the_killer_zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhimanstudio.pubg_multiplayer.R;
import com.dhimanstudio.the_killer_zone.model.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ConfirmTournamentJoin extends AppCompatActivity {

    private Button pay_now_button;
    private ImageView match_image;
    private String name,wallet_amount;
    private Number total_amount;
    private String entry_fee;
    private String tournament_date_m;
    private String image;
    private TextView product_name,match_date,pay_amount,service_charge,total_charge,payamount_confirm,mywallet,warning;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
        /*Total amount*/




        DocumentReference docRef = db.collection("Tournaments").document(user_id);
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


                        /////WORKING HERE //////////////////SECURITY IMPORTANT
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
