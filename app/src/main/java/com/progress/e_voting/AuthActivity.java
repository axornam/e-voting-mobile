package com.progress.e_voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;
import com.progress.e_voting.ui.auth.LoginFragment;

import org.jetbrains.annotations.NotNull;

public class AuthActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    public void SwitchActivity(Class<MainActivity> activityClass) {
        Intent intent = new Intent(AuthActivity.this, activityClass);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Get Instance of Firebase auth
        mAuth = FirebaseAuth.getInstance();

    }

    public static void SwitchFragment(FragmentManager fm, Fragment fragment, int resource) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(
                android.R.anim.slide_in_left,  // enter
                android.R.anim.fade_out,  // exit
                android.R.anim.fade_in,   // popEnter
                android.R.anim.slide_out_right  // popExit
        );
        ft.replace(resource, fragment);
        ft.commit();
    }
}