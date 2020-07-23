package com.dhimanstudio.the_killer_zone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dhimanstudio.pubg_multiplayer.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.text.TextUtils.isEmpty;

public class LoginActivity extends AppCompatActivity {

    private  int RC_SIGN_IN = 1;
    /////Google singn IN///////////
    private SignInButton googleSignInbutton;
    private GoogleSignInClient mGooleSigninclient;
    private String UserEmail;
    private String UserPhone;
    private String Userid;
    private String mobile_number;
    ////////////////////////////
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText mCountryCode;
    private EditText mPhoneNumber;
    private String complete_phone_number;
    private Button mGenerateBtn;
    private ProgressBar mLoginProgress;
    private Map<String, Object> user_data = new HashMap<>();

    private TextView mLoginFeedbackText;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mCountryCode = findViewById(R.id.country_code_text);
        mPhoneNumber = findViewById(R.id.phone_number_text);
        mGenerateBtn = findViewById(R.id.generate_btn);
        mLoginProgress = findViewById(R.id.login_progress_bar);
        mLoginFeedbackText = findViewById(R.id.login_form_feedback);

        mGenerateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String country_code = mCountryCode.getText().toString();
                String phone_number = mPhoneNumber.getText().toString();
                mobile_number = phone_number;
                String complete_phone_number = "+" + country_code + phone_number;

                if(country_code.isEmpty() || phone_number.isEmpty()){
                    mLoginFeedbackText.setText("Please fill in the form to continue.");
                    mLoginFeedbackText.setVisibility(View.VISIBLE);
                } else {
                    mLoginProgress.setVisibility(View.VISIBLE);
                    mGenerateBtn.setEnabled(false);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            complete_phone_number,
                            60,
                            TimeUnit.SECONDS,
                            LoginActivity.this,
                            mCallbacks
                    );

                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
//                System.out.print(e);
                mLoginFeedbackText.setText("Verification Failed, please try again.");
                mLoginFeedbackText.setVisibility(View.VISIBLE);
                mLoginProgress.setVisibility(View.INVISIBLE);
                mGenerateBtn.setEnabled(true);
            }

            @Override
            public void onCodeSent(final String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent otpIntent = new Intent(LoginActivity.this, OtpActivity.class);
                            otpIntent.putExtra("AuthCredentials", s);
                            startActivity(otpIntent);
                        }
                    },
                10000);
            }
        };


            //////////Google sign in ////////////////////
            ////////////////////////////////////////////
            googleSignInbutton = findViewById(R.id.google_sign_in);
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGooleSigninclient = GoogleSignIn.getClient(this,gso);

            googleSignInbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signin_google();
                }
            });

        }

    private void signin_google() {
    Intent signinIntent = mGooleSigninclient.getSignInIntent();
    startActivityForResult(signinIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> googletask = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSigninResult(googletask);
        }
    }

    private void handleGoogleSigninResult(Task<GoogleSignInAccount> googletask) {
        try {
            GoogleSignInAccount gacc =  googletask.getResult(ApiException.class);
//            Toast.makeText(this, "Google sign in successfull", Toast.LENGTH_SHORT).show();
            FirebaseGoogleatuth(gacc);
        }catch (ApiException e){
                Log.d("Error",e.getMessage().toString());
//            Toast.makeText(this, "Error Message:-"+e.getCause().toString(), Toast.LENGTH_SHORT).show();

        }
    }

    private void FirebaseGoogleatuth(GoogleSignInAccount gacc) {
        AuthCredential  authCredential = GoogleAuthProvider.getCredential(gacc.getIdToken(),null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        /////ADD USER DATA INSIDE FIREBASE ///////////
                        UserEmail = (String) FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        UserPhone = (String) FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                        Userid = (String) FirebaseAuth.getInstance().getCurrentUser().getUid();

                        storedata_of_user(UserEmail,UserPhone,Userid);


                    }else{
                        Toast.makeText(LoginActivity.this, "Failed final", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }




    private void storedata_of_user(String userEmail, String userPhone, final String userid) {

        ///////////////////////////////////////////////////
        final Map<String, Object> user_to_add = new HashMap<>();

        if(!isEmpty(userEmail)){
            user_to_add.put("email",userEmail);
        }
        if(!isEmpty(userEmail)){
            user_to_add.put("phone",userPhone);
        }


        ///////////////////////////////////////////////////
        DocumentReference userid_esist = db.collection("Users").document(userid);

        userid_esist.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ////////////////////Update
                        db.collection("Users")
                                .document(userid)
                                .update(user_to_add)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                      //Toast.makeText(LoginActivity.this, "User is added successfully", Toast.LENGTH_SHORT).show();
                                        sendUserToHome();
                                    }
                                });

                    }else{
                        //////////Add this data
                        user_to_add.put("wallet_amount",0);
                        db.collection("Users")
                            .document(userid)
                            .set(user_to_add)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(LoginActivity.this, "User is added successfully", Toast.LENGTH_SHORT).show();
                                    sendUserToHome();
                                }
                            });
                    }
                }else{
                    Log.d("Failed","failed to save data");
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser != null){
            sendUserToHome();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            /////ADD USER DATA INSIDE FIREBASE ///////////
                            UserPhone = mobile_number;
                            Userid = (String) FirebaseAuth.getInstance().getCurrentUser().getUid();
                            storedata_of_user("",UserPhone,Userid);
                            //////ADD USER END HERE ///////////////////////
//                            sendUserToHome();
                            // ...
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                mLoginFeedbackText.setVisibility(View.VISIBLE);
                                mLoginFeedbackText.setText("There was an error verifying OTP");
                            }
                        }
                        mLoginProgress.setVisibility(View.INVISIBLE);
                        mGenerateBtn.setEnabled(true);
                    }
                });
    }


    private void sendUserToHome() {

        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }


}
