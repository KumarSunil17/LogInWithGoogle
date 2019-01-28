package com.example.kumarsunil.loginwithgoogle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.main_user_name);
        userUserID  =findViewById(R.id.main_userID);
        userEmail = findViewById(R.id.main_user_emailID);
        userDP = findViewById(R.id.main_user_dp);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Picasso.with(MainActivity.this).load(mUser.getPhotoUrl()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.no_profile)
                .into(userDP, new Callback() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onError() {
                        Picasso.with(MainActivity.this).load(mUser.getPhotoUrl()).placeholder(R.drawable.no_profile).into(userDP);
                    }
                });
        userName.setText(mUser.getDisplayName());
        userEmail.setText(mUser.getEmail());
        userUserID.setText(mUser.getUid());
    }

    public void doLogOut(View view) {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        LoginManager.getInstance().logOut();
        finish();
    }
}
