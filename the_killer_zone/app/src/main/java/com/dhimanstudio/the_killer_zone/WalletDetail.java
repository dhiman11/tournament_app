package com.dhimanstudio.the_killer_zone;

import android.content.Intent;
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

public class WalletDetail extends AppCompatActivity {

    private String wallet_amount;
    private String user_id,phone,email,username;
    private TextView wallet_current;
    private ConstraintLayout add_money_in_wallet;
    ArrayList<Wallet_history> wallet_list;
//
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView mRecyclerwalletView;
    MywalletAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle b = getIntent().getExtras();
        if(b != null){
            wallet_amount = b.getString("wallet_amount");
            user_id = b.getString("user_id");
            phone = b.getString("phone");
            email = b.getString("email");
            username = b.getString("username");
        }

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
}