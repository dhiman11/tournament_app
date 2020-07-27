package com.dhimanstudio.the_killer_zone.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhimanstudio.pubg_multiplayer.R;
import com.dhimanstudio.the_killer_zone.adapter.MygamesAdapter;
import com.dhimanstudio.the_killer_zone.adapter.MyjoinedAdapter;
import com.dhimanstudio.the_killer_zone.model.Games;
import com.dhimanstudio.the_killer_zone.model.Tournament_joined;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private View dashboard_view;
    private RecyclerView recycleviewMyUpcomingmatches;
    ArrayList<Tournament_joined> list;
    MyjoinedAdapter adapter;
    private String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {


        View dashboard_view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        recycleviewMyUpcomingmatches = (RecyclerView) dashboard_view.findViewById(R.id.recycleviewMyUpcomingmatches);
        recycleviewMyUpcomingmatches.setLayoutManager( new LinearLayoutManager(getActivity()));




        /*Get data */
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Tournament_joined")
                .whereEqualTo("user_id",user_id)
                .whereEqualTo("status",1)
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list = new ArrayList<Tournament_joined>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Tournament_joined p = document.toObject(Tournament_joined.class);
                                list.add(p);

                            }

                            adapter = new MyjoinedAdapter(getActivity(),list);
//                            Toast.makeText(getActivity(), "Loading recycle view", Toast.LENGTH_SHORT).show();
                            recycleviewMyUpcomingmatches.setAdapter(adapter);

//                            gamelist_progress_bar = (ProgressBar) rootView.findViewById(R.id.progressBar_games_list);
//                            gamelist_progress_bar.setVisibility(View.INVISIBLE);
                        } else {
                            Log.d("MSGGGG", "Error getting documents: ", task.getException());

                        }
                    }
                });


        /*end*/
        dashboard_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        return dashboard_view;
    }
}