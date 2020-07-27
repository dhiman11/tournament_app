package com.dhimanstudio.the_killer_zone.adapter;

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

import com.dhimanstudio.pubg_multiplayer.R;
import com.dhimanstudio.the_killer_zone.GameDetail;
import com.dhimanstudio.the_killer_zone.GamesSubList;
import com.dhimanstudio.the_killer_zone.model.Games;
import com.dhimanstudio.the_killer_zone.model.Tournament_joined;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyjoinedAdapter extends RecyclerView.Adapter<MyjoinedAdapter.MyViewHolder> {

    Context context;
    ArrayList<Tournament_joined> joined;


    public MyjoinedAdapter(Context c , ArrayList<Tournament_joined> j)
    {
        context = c;
        joined = j;
    }

    @NonNull
    @Override
    public MyjoinedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyjoinedAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.joined_card_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyjoinedAdapter.MyViewHolder holder, final int position) {

        holder.name.setText(joined.get(position).getName());
        holder.date.setText(joined.get(position).getMatch_date());
        Picasso.get().load(joined.get(position).getImage()).into(holder.game_image) ;


        holder.game_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////LOAD A NEW FRAGMENT?//////////
                try {
                    Intent i = new Intent(context, GameDetail.class);
                    i.putExtra("doc", joined.get(position).getTournament_id());
                    context.startActivity(i);
                } catch (ClassCastException e) {

                }

            }
        });


        ///////////////////////////////////////////////////////////
//        holder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ////LOAD A NEW FRAGMENT?//////////
//                try {
//                    Intent i = new Intent(context, GamesSubList.class);
////                    i.putExtra("doc", joined.get(position).getUid());
//                    context.startActivity(i);
//                } catch (ClassCastException e) {
//
//                }
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return joined.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,date;
        ImageView game_image;
        Button button;



        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tornament_name);
            date = (TextView) itemView.findViewById(R.id.start_date);
            game_image = (ImageView) itemView.findViewById(R.id.tournament_image);


//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "Clieckd", Toast.LENGTH_SHORT).show();
//                    Log.d("king11", String.valueOf(v.getId()));
//                }
//            });


        }



    }
}
