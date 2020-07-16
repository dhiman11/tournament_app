package com.dhimanstudio.the_killer_zone.ui.profile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dhimanstudio.the_killer_zone.LoginActivity;
import com.dhimanstudio.pubg_multiplayer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    private FirebaseUser mCurrentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private View profile_view;
    private String user_id;
    private String user_image_url;
    private String user_pubg_id;
    private TextView username_top;

    private ProgressBar progressBar_profile;
 
    ////VEIW ELEMENTS
    private EditText r_username;
    private EditText r_email;
    private EditText r_phone;
    private EditText r_name;
    private EditText r_pubg_id;

    ////VEIW ELEMENTS
    private EditText u_username;
    private EditText u_email;
    private EditText u_phone;
    private EditText u_name;
    private EditText u_pubg_id;

    ////Message box
    private TextView message_box_profile;

    ///User avatar
    private ImageView user_avtar;
    private ImageButton logoutbtn;


    private Button save_profile;


    @Override
    public void onStart() {
        super.onStart();
        if(mCurrentUser == null){
//            sendUserToLogin();
        }
    }

//    private void sendUserToLogin() {
//        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
//        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(loginIntent);
//        getActivity().finish();
//    }

    private void sendUserToLogin() {
        Intent loginOut = new Intent(getActivity(),LoginActivity.class);
        startActivity(loginOut);
        getActivity().finish();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


       if (profile_view == null) {
            profile_view = (View) inflater.inflate(R.layout.fragment_profile, container, false);
        }else {
           ((ViewGroup) profile_view.getParent()).removeView(profile_view);
       }

        /*Time to Logout start*/
        logoutbtn = (ImageButton) profile_view.findViewById(R.id.logoutbtn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                sendUserToLogin();
            }
        });
        /*Time to Logout end*/

        ////LOGOUT ///
        save_profile = (Button) profile_view.findViewById(R.id.update_profile);
        save_profile.setEnabled(false);
        save_profile.setBackgroundColor(Color.GRAY);

        return profile_view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        load_profile_fragment_data();



        progressBar_profile = (ProgressBar) profile_view.findViewById(R.id.progressBar_profile);
        progressBar_profile.setVisibility(View.INVISIBLE);

        //////////////ON SERVER  CHANGES

                    user_id = mAuth.getCurrentUser().getUid();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference another_ref = db.collection("Users").document(user_id);
                    another_ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                            r_username = profile_view.findViewById(R.id.user_username);
                            r_pubg_id = profile_view.findViewById(R.id.user_pubg_id);
                            r_email = profile_view.findViewById(R.id.user_email);
                            r_name = profile_view.findViewById(R.id.user_name);
                            r_phone = profile_view.findViewById(R.id.user_phone);
                            username_top = profile_view.findViewById(R.id.username_top);

                            String user_name = (String) documentSnapshot.getString("name");
                            String user_username = (String)  documentSnapshot.getString("username");
                            String user_phone = (String)  documentSnapshot.getString("phone");
                            String UserEmailString = (String)  documentSnapshot.getString("email");
                            String user_pubg_id = (String)  documentSnapshot.getString("pubgid");

            //                            Log.d("Calling",user_name);
                            /////LOAD DATA inside form
                            r_name.setText(user_name);
                            username_top.setText(user_name);
                            r_email.setText(UserEmailString);
                            r_username.setText(user_username);
                            r_pubg_id.setText(user_pubg_id);
                            r_phone.setText(user_phone);

                        }
                    });

        /////////////////


        save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Progressbar
                progressBar_profile.setVisibility(View.VISIBLE);

                ///Message box
                message_box_profile = profile_view.findViewById(R.id.message_box_profile);

                u_username = profile_view.findViewById(R.id.user_username);
                u_pubg_id = profile_view.findViewById(R.id.user_pubg_id);
                u_email = profile_view.findViewById(R.id.user_email);
                u_name = profile_view.findViewById(R.id.user_name);
                u_phone = profile_view.findViewById(R.id.user_phone);




                final  Map<String, Object> user_data = new HashMap<>();

                user_data.put("username", u_username.getText().toString().trim());
                user_data.put("pubgid", u_pubg_id.getText().toString().trim());
                user_data.put("email", u_email.getText().toString().trim());
                user_data.put("name", u_name.getText().toString().trim());
                user_data.put("phone", u_phone.getText().toString().trim());



                //////////////////Load data on server changes
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("Users")
                        .document(mAuth.getCurrentUser().getUid())
                        .update(user_data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                load_profile_fragment_data();
                                Log.d("msg_after_submit","Data is saved");
                                message_box_profile.setText("Saved");
                                message_box_profile.setTextColor(Color.GREEN);
                                progressBar_profile.setVisibility(View.INVISIBLE);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar_profile.setVisibility(View.INVISIBLE);
                                message_box_profile.setText("Failed To save");
                                message_box_profile.setTextColor(Color.RED);
                            }
                        });

            }
        });



    }





    private void load_profile_fragment_data() {


        save_profile = (Button) profile_view.findViewById(R.id.update_profile);
        r_username = profile_view.findViewById(R.id.user_username);
        r_pubg_id = profile_view.findViewById(R.id.user_pubg_id);
        r_email = profile_view.findViewById(R.id.user_email);
        r_name = profile_view.findViewById(R.id.user_name);
        r_phone = profile_view.findViewById(R.id.user_phone);

        ///GEt user_avatar
        user_avtar = profile_view.findViewById(R.id.user_avtar);
        username_top = profile_view.findViewById(R.id.username_top);

        try{
            user_image_url = (String) mAuth.getCurrentUser().getPhotoUrl().toString();
            Picasso.get().load(user_image_url).into(user_avtar);
        }catch (Exception e){
            Picasso.get().load(R.drawable.profile_image).into(user_avtar);
        }


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
                        username_top.setText(user_name);
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



                        save_profile.setEnabled(true);
                        save_profile.setBackgroundColor(Color.BLUE);

                    } else {
                        Log.d("SUCCESSLOL", "No such document");
                        save_profile.setEnabled(true);
                        save_profile.setBackgroundColor(Color.BLUE);

                    }
                } else {
                    save_profile.setEnabled(true);
                    save_profile.setBackgroundColor(Color.BLUE);
                    Log.d("FAILEDLOL", "get failed with ", task.getException());
                }
            }
        });



    }


}