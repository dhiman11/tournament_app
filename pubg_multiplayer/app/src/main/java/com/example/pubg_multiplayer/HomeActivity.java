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
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {

    private Fragment homeFragment  = new HomeFragment();
    private Fragment dashboardFragment = new DashboardFragment();
    private Fragment notifragment  = new NotificationsFragment();
    private Fragment profileFragment = new ProfileFragment();


    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.nav_view);

        ////My adapter /////////



        fm.beginTransaction().add(R.id.nav_host_fragment, profileFragment, "4").hide(profileFragment).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment, notifragment, "3").hide(notifragment).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment, dashboardFragment, "2").hide(dashboardFragment).commit();
        fm.beginTransaction().add(R.id.nav_host_fragment,homeFragment, "1").commit();


        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fm.beginTransaction().hide(active).show(homeFragment).commit();
                        active = homeFragment;
                    return true;

                    case R.id.navigation_dashboard:
                        fm.beginTransaction().hide(active).show(dashboardFragment).commit();
                        active = dashboardFragment;
                        return true;

                    case R.id.navigation_notifications:
                        fm.beginTransaction().hide(active).show(notifragment).commit();
                        active = notifragment;
                        return true;

                    case R.id.profile_detail:
                        fm.beginTransaction().hide(active).show(profileFragment).commit();
                        active = profileFragment;
                        return true;
                }


                return false;
            }
        });


    }






}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//
//        homeFragment  = new HomeFragment();
//        dashboardFragment = new DashboardFragment();
//        notifragment  = new NotificationsFragment();
//        profileFragment = new ProfileFragment();
//
//        setFragment(profileFragment);
//
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.profile_detail)
//                .build();
//
//
//
//        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected (@NonNull MenuItem item) {
//                Fragment fragment = null;
//                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_view);
//                switch (item.getItemId()){
//                    case R.id.navigation_home :
//                        if (!(currentFragment instanceof HomeFragment)) {
//                            fragment = new HomeFragment();
//                        }
//                        break;
//                    case R.id.navigation_dashboard:
//                        if (!(currentFragment instanceof DashboardFragment)) {
//                            fragment = new DashboardFragment();
//                        }
//                        break;
//                    case R.id.navigation_notifications:
//                        if (!(currentFragment instanceof NotificationsFragment)) {
//                            fragment = new NotificationsFragment();
//                        }
//                        break;
//                    case R.id.profile_detail:
//                        if (!(currentFragment instanceof ProfileFragment)) {
//                            fragment = new ProfileFragment();
//                        }
//                        break;
//
//                }
//                getSupportFragmentManager()
//                        .beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
//                return true;
//            }
//        });
//    }
//
//    private void setFragment(Fragment fragment) {
//
//        try {
//            if (!fragment.isAdded()) {
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        }catch (Exception e){
//            Toast.makeText(this, "Error Message : - "+e.getMessage(), Toast.LENGTH_SHORT).show();
////            Toast.makeText("Hmm..",e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//    }

//}