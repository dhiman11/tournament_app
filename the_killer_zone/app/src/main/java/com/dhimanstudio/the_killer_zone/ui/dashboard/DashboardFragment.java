package com.dhimanstudio.the_killer_zone.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.dhimanstudio.pubg_multiplayer.R;

public class DashboardFragment extends Fragment {

    private View dashboard_view;


    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {


        View dashboard_view = inflater.inflate(R.layout.fragment_dashboard, container, false);


        dashboard_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        return dashboard_view;
    }
}