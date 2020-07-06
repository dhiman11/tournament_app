package com.example.pubg_multiplayer;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.pubg_multiplayer.adapter.MygamesAdapter;
import com.example.pubg_multiplayer.model.Games;
import com.example.pubg_multiplayer.ui.dashboard.DashboardFragment;
import com.example.pubg_multiplayer.ui.home.HomeFragment;
import com.example.pubg_multiplayer.ui.notifications.NotificationsFragment;
import com.example.pubg_multiplayer.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private NotificationsFragment notifragment;
    private DashboardFragment dashboardFragment;
    private ProfileFragment profileFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final BottomNavigationView navView = findViewById(R.id.nav_view);

        homeFragment  = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        notifragment  = new NotificationsFragment();
        profileFragment = new ProfileFragment();

        setFragment(homeFragment);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home :
                        setFragment(homeFragment);
                         return true;
                    case R.id.navigation_dashboard:
                        setFragment(dashboardFragment);
                        return true;
                    case R.id.navigation_notifications:
                        setFragment(notifragment);
                        return true;

                    case R.id.profile_detail:
                        setFragment(profileFragment);
                        return true;




                    default:
                        return false;


                }
            }
        });
    }

    private void setFragment(Fragment fragment) {


        try {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment,fragment);
            fragmentTransaction.commit();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            Toast.makeText("Hmm..",e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}