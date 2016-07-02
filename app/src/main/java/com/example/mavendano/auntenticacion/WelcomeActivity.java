package com.example.mavendano.auntenticacion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = WelcomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {

            TextView txtDisplayName = (TextView) findViewById(R.id.txtUsername);
            txtDisplayName.setText(firebaseUser.getDisplayName());

            TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
            txtEmail.setText(firebaseUser.getEmail());

            Uri imageUri = firebaseUser.getPhotoUrl();
            ImageView imageAvatar = (ImageView) findViewById(R.id.imgAvatar);
            Glide.with(this).load(imageUri).into(imageAvatar);

            Toast.makeText(this, "Provider ID:" + firebaseUser.getProviderId(), Toast.LENGTH_SHORT).show();
        }

        Button buttonSignOut = (Button) findViewById(R.id.signOutButton);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void signOut() {
        AuthUI.getInstance().signOut(WelcomeActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
