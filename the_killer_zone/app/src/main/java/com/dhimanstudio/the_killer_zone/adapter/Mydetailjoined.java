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

public class Mydetailjoined extends RecyclerView.Adapter<Mydetailjoined.MyViewHolder> {

    Context context;
    ArrayList<Tournament_joined> joined;


    public Mydetailjoined(Context c , ArrayList<Tournament_joined> j)
    {
        context = c;
        joined = j;
    }

    @NonNull
    @Override
    public Mydetailjoined.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Mydetailjoined.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.joined_detail_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Mydetailjoined.MyViewHolder holder, final int position) {

        Log.d("DATA checking",joined.get(position).getUser_name());

        holder.name.setText(joined.get(position).getUser_name());
//        Picasso.get().load(joined.get(position).getImage()).into(holder.game_image) ;




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
        TextView name;
//        ImageView game_image;
//        Button button;



        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.username_here);


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
