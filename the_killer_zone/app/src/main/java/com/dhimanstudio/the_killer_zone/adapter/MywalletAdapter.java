package com.dhimanstudio.the_killer_zone.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dhimanstudio.pubg_multiplayer.R;

import com.dhimanstudio.the_killer_zone.model.Wallet_history;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MywalletAdapter extends RecyclerView.Adapter<MywalletAdapter.MyViewHolder> {


    Context context;
    ArrayList<Wallet_history> wallet;


    public MywalletAdapter(Context c , ArrayList<Wallet_history> w)
    {
        context = c;
        wallet = w;
    }

    @NonNull
    @Override
    public MywalletAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MywalletAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.wallet_single_history,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MywalletAdapter.MyViewHolder holder, final int position) {

        holder.p_name.setText(wallet.get(position).getProductinfo());
        holder.taxid.setText(wallet.get(position).getTxnid());
        holder.status.setText(wallet.get(position).getPayment());
        holder.status2.setText(wallet.get(position).getAmount());

    }

    @Override
    public int getItemCount() {
        return wallet.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView p_name,status,taxid,status2;




        public MyViewHolder(View itemView) {
            super(itemView);
            p_name = (TextView) itemView.findViewById(R.id.productinfo);
            status = (TextView) itemView.findViewById(R.id.status);
            taxid = (TextView) itemView.findViewById(R.id.taxid);
            status2 = (TextView) itemView.findViewById(R.id.status2);

        }



    }

}

