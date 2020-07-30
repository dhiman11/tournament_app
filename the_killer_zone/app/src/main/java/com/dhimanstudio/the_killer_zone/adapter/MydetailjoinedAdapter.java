package com.dhimanstudio.the_killer_zone.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.dhimanstudio.pubg_multiplayer.R;

import com.dhimanstudio.the_killer_zone.model.Tournament_joined;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MydetailjoinedAdapter extends RecyclerView.Adapter<MydetailjoinedAdapter.MyViewHolder> {

    Context context;
    ArrayList<Tournament_joined> joined;


    public MydetailjoinedAdapter(Context c , ArrayList<Tournament_joined> j)
    {
        context = c;
        joined = j;
    }

    @NonNull
    @Override
    public MydetailjoinedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MydetailjoinedAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.joined_detail_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MydetailjoinedAdapter.MyViewHolder holder, final int position) {


        holder.name.setText(joined.get(position).getUsername());
//      Picasso.get().load(joined.get(position).getImage()).into(holder.game_image) ;

    }

    @Override
    public int getItemCount() {
        return joined.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.username_here);
            Log.d("my message","lol");
        }
    }
}
