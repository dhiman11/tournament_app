package com.example.pubg_multiplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pubg_multiplayer.R;
import com.example.pubg_multiplayer.model.Games;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MygamesAdapter extends RecyclerView.Adapter<MygamesAdapter.MyViewHolder> {

    Context context;
    ArrayList<Games> games;

    public MygamesAdapter(Context c , ArrayList<Games> g)
    {
        context = c;
        games = g;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.games_card_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(games.get(position).getName());
        Picasso.get().load(games.get(position).getImage()).into(holder.game_image);
//        if(games.get(position).getPermission()) {
//            holder.btn.setVisibility(View.VISIBLE);
//            holder.onClick(position);
//        }
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,email;
        ImageView game_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.game_name);
            game_image = (ImageView) itemView.findViewById(R.id.game_image);

        }
//        public void onClick(final int position)
//        {
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }
}
