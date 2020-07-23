package com.dhimanstudio.the_killer_zone;

import android.content.Intent;
import android.os.Bundle;

import com.dhimanstudio.pubg_multiplayer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WalletDetail extends AppCompatActivity {

    private String wallet_amount;
    private String user_id,phone,email,username;
    private TextView wallet_current;
    private ConstraintLayout add_money_in_wallet;
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


    }
}