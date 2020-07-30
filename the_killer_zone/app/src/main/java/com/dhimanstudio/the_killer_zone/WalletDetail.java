package com.dhimanstudio.the_killer_zone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.dhimanstudio.pubg_multiplayer.R;
import com.dhimanstudio.the_killer_zone.adapter.MygamesListAdapter;
import com.dhimanstudio.the_killer_zone.adapter.MywalletAdapter;
import com.dhimanstudio.the_killer_zone.model.Tournament_list;
import com.dhimanstudio.the_killer_zone.model.Wallet_history;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.parseColor;

public class WalletDetail extends AppCompatActivity {

    private String wallet_amount;
    private Integer walletint;
    private String user_id,phone,email,username;
    private TextView wallet_current;
    private ConstraintLayout add_money_in_wallet;
    ArrayList<Wallet_history> wallet_list;
    private ConstraintLayout walletwithdraw;
    private String u_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String txnId;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView mRecyclerwalletView;
    MywalletAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        walletwithdraw = findViewById(R.id.walletwithdraw);



        Bundle b = getIntent().getExtras();
        if(b != null){
            wallet_amount = b.getString("wallet_amount");
            user_id = b.getString("user_id");
            phone = b.getString("phone");
            email = b.getString("email");
            username = b.getString("username");
        }

        check_if_reached_threashold(wallet_amount);

        wallet_current  = (TextView) findViewById(R.id.wallet_current);

        wallet_current.setText("₹"+wallet_amount);

        add_money_in_wallet = (ConstraintLayout) findViewById(R.id.add_money_in_wallet);


        add_money_in_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentintent = new Intent(WalletDetail.this, PaymentActivity.class);
                paymentintent.putExtra("payment",wallet_amount);
                paymentintent.putExtra("username",username);
                paymentintent.putExtra("email",email);
                paymentintent.putExtra("phone",phone);
                startActivity(paymentintent);
            }
        });




        mRecyclerwalletView = (RecyclerView)  findViewById(R.id.wallet_recycle);
        mRecyclerwalletView.setLayoutManager( new LinearLayoutManager(this));
//


        load_wallet_detail();


        /*Withdraw buttom*/
        walletwithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                walletwithdraw.setClickable(false);
                walletwithdraw.setEnabled(false);
                txnId =  "TXN"+System.currentTimeMillis() + "";
                  walletint = Integer.valueOf(wallet_amount);

                    if(walletint >= 20){
                        Log.d("withdraw","You can withdraw money ");
                        walletwithdraw.setBackgroundColor(Color.parseColor("#6177FC"));

                        Map<String, Object> user_add_data = new HashMap<>();
                        user_add_data.put("amount", wallet_amount);
                        user_add_data.put("payment", "Requested");
                        user_add_data.put("paymentId", "-");
                        user_add_data.put("productinfo", "Payment will be sent to your paytm.");
                        user_add_data.put("txnid", txnId);

                        CollectionReference addwallethistory =  db.collection("Users").document(u_id).collection("UserPayments");
                        addwallethistory.add(user_add_data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Map<String, Object> user_update_data = new HashMap<>();

                                user_update_data.put("wallet_amount", 0);

                                DocumentReference updatewallethistory =  db.collection("Users").document(u_id);
                                updatewallethistory.update(user_update_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Map<String, Object> payment_request = new HashMap<>();
                                        payment_request.put("user_id", u_id);
                                        payment_request.put("user_name", username);
                                        payment_request.put("payment_requested", wallet_amount);
                                        payment_request.put("txnid", txnId);

                                        CollectionReference payment_request_required =  db.collection("Payment_request");
                                        payment_request_required.add(payment_request).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                wallet_amount = "0";
                                                walletwithdraw.setBackgroundColor(Color.parseColor("#FF999999"));
                                                wallet_current.setText("₹0");
                                                walletint = 0;
                                                load_wallet_detail();
                                            }
                                        });


                                    }
                                });
                            }
                        });
                    }else{
                        Log.d("withdraw","You can not withdraw money ");
                        walletwithdraw.setBackgroundColor(Color.parseColor("#FF999999"));
                    }


            }
        });

    }

    private void load_wallet_detail() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        CollectionReference collectionOfuserpayment = db.collection("Users").document(uid).collection("UserPayments");

        collectionOfuserpayment
                .limit(20)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            int count = 0;
                            wallet_list = new ArrayList<Wallet_history>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Wallet_history p = document.toObject(Wallet_history.class);
                                wallet_list.add(p);
                                count++;
                            }
                            adapter = new MywalletAdapter(WalletDetail.this,wallet_list);
                            mRecyclerwalletView.setAdapter(adapter);

                            /* count the data */
                            if(count ==0){
                                Toast.makeText(WalletDetail.this, "No wallet history avaialble", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                        }
                    }
                });
    }

    private void check_if_reached_threashold(String wallet_amount) {
        Integer intwallet = Integer.valueOf(wallet_amount);
        if(intwallet >= 20) {
            Log.d("withdraw", "You can withdraw money ");
            walletwithdraw.setBackgroundColor(Color.parseColor("#6177FC"));
        }else{
            walletwithdraw.setBackgroundColor(Color.parseColor("#FF999999"));
        }

    }
}