package com.dhimanstudio.the_killer_zone.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.dhimanstudio.the_killer_zone.GameDetail;
import com.dhimanstudio.the_killer_zone.model.Tournament_list;
import com.dhimanstudio.pubg_multiplayer.R;
import com.google.firebase.Timestamp;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MygamesListAdapter extends RecyclerView.Adapter<MygamesListAdapter.MyViewHolder> {

    Context context;
    ArrayList<com.dhimanstudio.the_killer_zone.model.Tournament_list> Tournament_list;
    private SimpleDateFormat sdf;
    private String tournament_date;
    private CountDownTimer mCountDownTimer;
    public MygamesListAdapter(Context c , ArrayList<Tournament_list> g)
    {
        context = c;
        Tournament_list = g;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.game_card_detail_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        Timestamp date =  Tournament_list.get(position).getTournament_start_date();



        tournament_date = get_date_oftournament(date.toDate());


        holder.sub_detail.setText("On "+tournament_date+" - ENTRY: ₹"+ Tournament_list.get(position).getEntry_fee()+" - "+Tournament_list.get(position).getTeam()+" - "+Tournament_list.get(position).getGame_map());
        holder.name.setText(Tournament_list.get(position).getName());
        Picasso.get().load(Tournament_list.get(position).getImage()).into(holder.game_image) ;


        date_count_down(holder,date.toDate());

        holder.game_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ////LOAD A NEW FRAGMENT?//////////
                try {
                    Intent i = new Intent(context, GameDetail.class);
                    i.putExtra("doc", Tournament_list.get(position).getUid());
                    context.startActivity(i);
                } catch (ClassCastException e) {

                }

            }
        });
    }



    private String get_date_oftournament(Date date) {
        String formattedDate;
        try{
                SimpleDateFormat sdf = new SimpleDateFormat("d,MMMM h:mm,a", Locale.ENGLISH);
                sdf.setTimeZone(TimeZone.getTimeZone("IST"));
                formattedDate = sdf.format(date);
        }catch (Exception e){
            SimpleDateFormat sdf = new SimpleDateFormat("d,MMMM h:mm,a", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("IST"));
                formattedDate = "";
        }

    return formattedDate;
    }


    @Override
    public int getItemCount() {
        return Tournament_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,sub_detail,dayscount,hrscount,minscount,secscount,matchin;

        ImageView game_image;
        Button join_button;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.game_name);
            game_image = (ImageView) itemView.findViewById(R.id.game_image);
            sub_detail = (TextView) itemView.findViewById(R.id.sub_detail);
            dayscount = (TextView) itemView.findViewById(R.id.dayscount);
            hrscount = (TextView) itemView.findViewById(R.id.hrscount);
            minscount = (TextView) itemView.findViewById(R.id.minscount);
            secscount = (TextView) itemView.findViewById(R.id.secscount);
            matchin = (TextView) itemView.findViewById(R.id.matchin);

//            join_button = (Button) itemView.findViewById(R.id.join_now);


        }

    }


    /*TIMER*/
    private void date_count_down(final MyViewHolder holder,Date date) {


        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("IST"));
        formatter.setLenient(false);

        String current_time = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss").format(date);
        String endTime = current_time;

        Date endDate;
        long milliseconds = 0;

        try {
            endDate = formatter.parse(endTime);
            milliseconds = endDate.getTime();

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final long[] startTime = {System.currentTimeMillis()};

        long diff = milliseconds - startTime[0];


        mCountDownTimer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TextView timeleft;
                startTime[0] = startTime[0] -1;
                Long serverUptimeSeconds =
                        (millisUntilFinished - startTime[0]) / 1000;

                String daysLeft = String.format("%d", serverUptimeSeconds / 86400);
                holder.dayscount.setText(daysLeft);
                String hoursLeft = String.format("%d", (serverUptimeSeconds % 86400) / 3600);
                holder.hrscount.setText(hoursLeft);
                String minutesLeft = String.format("%d", ((serverUptimeSeconds % 86400) % 3600) / 60);
                holder.minscount.setText(minutesLeft);
                String secondsLeft = String.format("%d", ((serverUptimeSeconds % 86400) % 3600) % 60);
                holder.secscount.setText(secondsLeft);


            }

            @Override
            public void onFinish() {
                holder.dayscount.setText("0");
                holder.hrscount.setText("0");
                holder.minscount.setText("0");
                holder.secscount.setText("0");
                holder.matchin.setText("MATCH STARTED");
                holder.matchin.setTextColor(Color.parseColor("#b2e281"));
            }
        }.start();

    }
}
