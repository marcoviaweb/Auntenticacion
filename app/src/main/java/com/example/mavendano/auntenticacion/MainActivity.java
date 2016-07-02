package com.example.mavendano.auntenticacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        // Primero, verificamos la existencia de una sesión.
        if(auth.getCurrentUser() != null){
            FirebaseUser firebaseUser = auth.getCurrentUser();
            Toast.makeText(
                    this,
                    "Usuario logueado:" + firebaseUser.getDisplayName(),
                    Toast.LENGTH_SHORT)
                    .show();
            startActivity(new Intent(this, WelcomeActivity.class));
        } else{
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(
                                    AuthUI.EMAIL_PROVIDER,
                                    AuthUI.GOOGLE_PROVIDER)
                            .setTheme(R.style.AppTheme)
                            .build(),
                    RC_SIGN_IN);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                startActivity(new Intent(this, WelcomeActivity.class));
                finish();
            }
            else{
                Toast.makeText(this, "Se produjo un error al momento del logueo", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
