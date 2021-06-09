package com.progress.e_voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

import org.jetbrains.annotations.NotNull;

public class AuthActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Get Instance of Firebase auth
        mAuth = FirebaseAuth.getInstance();

//        signInUser(uEmail, uPassword);
    }

    void signInUser(String uEmail, String uPassword) {
        mAuth.createUserWithEmailAndPassword(uEmail, uPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // TODO create user inside the firestore collection
                    // TODO move to the login screen
                }
            }
        });
    }
}