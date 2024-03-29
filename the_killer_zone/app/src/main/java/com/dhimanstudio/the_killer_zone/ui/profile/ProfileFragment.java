package com.dhimanstudio.the_killer_zone.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dhimanstudio.the_killer_zone.LoginActivity;
import com.dhimanstudio.pubg_multiplayer.R;
import com.dhimanstudio.the_killer_zone.WalletDetail;
import com.dhimanstudio.the_killer_zone.model.Profile;
import com.dhimanstudio.the_killer_zone.model.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import static android.text.TextUtils.isEmpty;

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
    private ImageButton wallet_image_click;
    private FirebaseUser userauth = FirebaseAuth.getInstance().getCurrentUser();
//    private TextView username_top;
    private TextView wallet_amount;
    private ProgressBar progressBar_profile;
    private  String wallet_money_string;
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

    private Session session;
    private Button save_profile;


    /////*Define stings variable*/
    private String user_name,user_username,user_phone,UserEmailString,user_pubg_id;



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


        wallet_image_click = (ImageButton) profile_view.findViewById(R.id.wallet_image_click);
        wallet_amount = (TextView) profile_view.findViewById(R.id.wallet_amount);
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



        profile_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

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
//                            username_top = profile_view.findViewById(R.id.username_top);


                              user_name = (String) documentSnapshot.getString("name");
                              user_username = (String)  documentSnapshot.getString("username");
                              user_phone = (String)  documentSnapshot.getString("phone");
                              UserEmailString = (String)  documentSnapshot.getString("email");
                              user_pubg_id = (String)  documentSnapshot.getString("pubgid");

                            Profile p = documentSnapshot.toObject(Profile.class);

                            Integer wallet_money = p.getWallet_amount();

                            wallet_money_string  = wallet_money.toString();
                            wallet_amount.setText(" ₹"+wallet_money_string);

                            /////LOAD DATA inside form
                            r_name.setText(user_name);
                            update_display_name(user_username);
//                            username_top.setText(user_name);
                            r_email.setText(UserEmailString);
                            r_username.setText(user_username);

                            r_pubg_id.setText(user_pubg_id);
                            r_phone.setText(user_phone);

                        }
                    });



        /* Wallet Redirect start */
        wallet_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_wallet_page(UserEmailString,user_username,user_phone);
            }
        });

        wallet_image_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_wallet_page(UserEmailString,user_username,user_phone);
            }
        });
        /* Wallet Redirect end */


        /* Save profile */
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

    private void update_display_name(String user_name) {

            session = new Session(getContext().getApplicationContext()); //in oncreate
            session.setusename(user_name);
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
            .setDisplayName(user_name).build();
            userauth.updateProfile(profileUpdates);


    }

    /* Wallet redirect */
    private boolean go_to_wallet_page(String UserEmailString, String user_username, String user_phone) {

        ///Check if we have required fields
        if(isEmpty(UserEmailString) || isEmpty(user_username) || isEmpty(user_phone)){
            Toast.makeText(getContext(), "Please fill the user detail . Eg Username , Email , Phone. ", Toast.LENGTH_SHORT).show();
            return false;
        }


        Intent paymentintent = new Intent(getActivity(), WalletDetail.class);
        paymentintent.putExtra("wallet_amount",wallet_money_string.toString());
        paymentintent.putExtra("username",user_username);
        paymentintent.putExtra("email",UserEmailString);
        paymentintent.putExtra("phone",user_phone);
        paymentintent.putExtra("user_id",mAuth.getCurrentUser().getUid());
        startActivity(paymentintent);
        return true;
    }



    /* Load profile data */
    private void load_profile_fragment_data() {
        save_profile = (Button) profile_view.findViewById(R.id.update_profile);
        r_username = profile_view.findViewById(R.id.user_username);
        r_pubg_id = profile_view.findViewById(R.id.user_pubg_id);
        r_email = profile_view.findViewById(R.id.user_email);
        r_name = profile_view.findViewById(R.id.user_name);
        r_phone = profile_view.findViewById(R.id.user_phone);

        ///GEt user_avatar
        user_avtar = profile_view.findViewById(R.id.user_avtar);
//        username_top = profile_view.findViewById(R.id.username_top);

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

                        Profile profile_data = document.toObject(Profile.class);

                        Integer wallet_mone = profile_data.getWallet_amount();
                        wallet_money_string  = wallet_mone.toString();

                        wallet_amount.setText(" ₹"+wallet_money_string);

                        String user_name = (String) document.getString("name");
                        String user_username = (String)  document.getString("username");
                        String user_phone = (String)  document.getString("phone");
                        String UserEmailString = (String)  document.getString("email");
                        String user_pubg_id = (String)  document.getString("pubgid");

                        /////LOAD DATA inside form
                        r_name.setText(user_name);

//                        username_top.setText(user_name);
                        r_email.setText(UserEmailString);
                        r_username.setText(user_username);
                       /*set display name*/
                        update_display_name(user_username);
                        /*set display name*/
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
                        save_profile.setBackgroundColor(Color.parseColor("#FF03DAC5"));

                    } else {
                        Log.d("SUCCESSLOL", "No such document");
                        save_profile.setEnabled(true);
                        save_profile.setBackgroundColor(Color.parseColor("#FF03DAC5"));

                    }
                } else {
                    save_profile.setEnabled(true);
                    save_profile.setBackgroundColor(Color.parseColor("#FF03DAC5"));
                    Log.d("FAILEDLOL", "get failed with ", task.getException());
                }
            }
        });



    }


}