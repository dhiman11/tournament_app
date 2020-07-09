package com.example.pubg_multiplayer.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pubg_multiplayer.R;
import com.example.pubg_multiplayer.adapter.MygamesAdapter;
import com.example.pubg_multiplayer.model.Games;
import com.example.pubg_multiplayer.model.Profile;
import com.example.pubg_multiplayer.ui.notifications.NotificationsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private FirebaseFirestore db;
    private View profile_view;
    private String user_id;

 
    ////VEIW ELEMENTS
    private EditText r_username;
    private EditText r_email;
    private EditText r_phone;
    private EditText r_name;
    private EditText r_pubg_id;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

       if (profile_view == null) {
            profile_view = (View) inflater.inflate(R.layout.fragment_profile, container, false);
        }else {
           ((ViewGroup) profile_view.getParent()).removeView(profile_view);
       }
        return profile_view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        load_profile_fragment_data();

    }

    private void load_profile_fragment_data() {

        mAuth = FirebaseAuth.getInstance();

        r_username = profile_view.findViewById(R.id.user_username);
        r_pubg_id = profile_view.findViewById(R.id.user_pubg_id);
        r_email = profile_view.findViewById(R.id.user_email);
        r_name = profile_view.findViewById(R.id.user_name);
        r_phone = profile_view.findViewById(R.id.user_phone);

        r_username.setEnabled(false);
        r_pubg_id.setEnabled(false);
        r_email.setEnabled(false);
        r_name.setEnabled(false);
        r_phone.setEnabled(false);

        user_id = mAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(user_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {


                        String user_name = (String) document.getString("name");
                        String user_username = (String)  document.getString("username");
                        String user_phone = (String)  document.getString("phone");
                        String UserEmailString = (String)  document.getString("email");
                        String user_pubg_id = (String)  document.getString("pubgid");




                        /////LOAD DATA inside form
                        r_name.setText(user_name);
                        r_email.setText(UserEmailString);
                        r_username.setText(user_username);
                        r_pubg_id.setText(user_pubg_id);
                        r_phone.setText(user_phone);
                        /////////////////////////////////
                        r_name.setEnabled(true);
                        r_email.setEnabled(true);
                        r_username.setEnabled(true);
                        r_pubg_id.setEnabled(true);
                        r_phone.setEnabled(true);
                        ////////////////////////////////
                        if(UserEmailString != null &&  !UserEmailString.isEmpty()) {r_email.setEnabled(false); }
                        if(user_phone != null &&  !user_phone.isEmpty()) {r_phone.setEnabled(false); }
                        if(user_username != null &&  !user_username.isEmpty()) {r_username.setEnabled(false); }


                    } else {
                        Log.d("SUCCESSLOL", "No such document");
                    }
                } else {
                    Log.d("FAILEDLOL", "get failed with ", task.getException());
                }
            }
        });



    }


}