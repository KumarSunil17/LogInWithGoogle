package com.example.kumarsunil.loginwithgoogle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private TextView userName, userEmail, userUserID;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private ImageView userDP;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.main_user_name);
        userUserID = findViewById(R.id.main_userID);
        userEmail = findViewById(R.id.main_user_emailID);
        userDP = findViewById(R.id.main_user_dp);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("44600699291-mb99p7klgn7a2supcbcu1vb6ulfl964i.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);

//        Picasso.with(MainActivity.this).load(mUser.getPhotoUrl()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.no_profile)
//                .into(userDP, new Callback() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//                    @Override
//                    public void onError() {
//                        Picasso.with(MainActivity.this).load(mUser.getPhotoUrl()).placeholder(R.drawable.no_profile).into(userDP);
//                    }
//                });

        Picasso.get()
                .load(mUser.getPhotoUrl())
                .placeholder(R.drawable.no_profile)
                .error(R.drawable.no_profile)
                .into(userDP);

        userName.setText(mUser.getDisplayName());
        userEmail.setText(mUser.getEmail());
        userUserID.setText(mUser.getUid());
    }

    public void doLogOut(View view) {
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mAuth.signOut();
                    LoginManager.getInstance().logOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
