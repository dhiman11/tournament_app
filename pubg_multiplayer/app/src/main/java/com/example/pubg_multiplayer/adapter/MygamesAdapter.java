package com.example.pubg_multiplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pubg_multiplayer.GamesSubList;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.name.setText(games.get(position).getName());
        Picasso.get().load(games.get(position).getImage()).into(holder.game_image) ;



        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ////LOAD A NEW FRAGMENT?//////////
                try {
                    Intent i = new Intent(context, GamesSubList.class);
                    i.putExtra("doc", games.get(position).getUid());
                    context.startActivity(i);
                } catch (ClassCastException e) {

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,email;
        ImageView game_image;
        Button button;



        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.game_name);
            game_image = (ImageView) itemView.findViewById(R.id.game_image);
            button = (Button) itemView.findViewById(R.id.join_now);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clieckd", Toast.LENGTH_SHORT).show();
                    Log.d("king11", String.valueOf(v.getId()));
                }
            });


        }



    }
}
