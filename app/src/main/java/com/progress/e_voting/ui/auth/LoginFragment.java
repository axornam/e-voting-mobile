package com.progress.e_voting.ui.auth;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.progress.e_voting.AuthActivity;
import com.progress.e_voting.MainActivity;
import com.progress.e_voting.R;
import com.progress.e_voting.utils.Util;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private Button mLoginButton;
    private EditText mUsernameField, mPasswordField;
    private TextView mNoAccount;

    private static final String TAG = "LoginFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private EditText mVoterIdField;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login,
                container, false);

        // ID input fields and Login Button
        mLoginButton = view.findViewById(R.id.id_login_button);
        mVoterIdField = view.findViewById(R.id.id_login_voter_id);
        mUsernameField = view.findViewById(R.id.id_login_username);
        mPasswordField = view.findViewById(R.id.id_login_password);
        mNoAccount = view.findViewById(R.id.id_create_account);

        mNoAccount.setOnClickListener(this::onClickNoAccount);
        mLoginButton.setOnClickListener(this);
        return view;
    }

    private void onClickNoAccount(View view) {
        AuthActivity.SwitchFragment(getParentFragmentManager(),
                RegisterFragment.newInstance("Registration", "Fragment"), R.id.auth_fragment_container);
    }

    @Override
    public void onClick(View v) {
        String username = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();
        String voterId = mVoterIdField.getText().toString();
        voterId = voterId.toUpperCase();

        Log.d(TAG, String.format("onClick: Username: %s | Password: %s | VoterID: %s",
                username, password, voterId));

        Util.ShowProgress.showProgress(getContext(), "Logging In, Please wait...");

        // todo perform firebase login
        mAuth = FirebaseAuth.getInstance();
        String finalVoterId = voterId;
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, String.format("onClick: Currently Logged In User: %s", mAuth.getCurrentUser().getUid()));
                // todo check fire_store for existing voter with same voter_id
                checkExisting(finalVoterId);

            } else if (!task.isSuccessful()) {
                // todo alert user of failed login
                Toast.makeText(getActivity(), "Login Failed Please Try Again", Toast.LENGTH_SHORT).show();
            }

            // todo clear fields
            mUsernameField.setText("");
            mPasswordField.setText("");
            mVoterIdField.setText("");
        });

    }

    private void checkExisting(String voterId) {
        FirebaseFirestore store = FirebaseFirestore.getInstance();
        final String[] id = new String[1];
        store.collection("/voters").document(mAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot != null) {
                        id[0] = (String) snapshot.get("voter_id");

                        if (voterId.equals(id[0])) {
                            // todo alert user of success
                            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                            // todo redirect to main activity
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();

                            Util.ShowProgress.dismissProgress();

                        } else {
                            // todo alert user of failed login
                            Toast.makeText(getActivity(), "Login Failed Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }
}