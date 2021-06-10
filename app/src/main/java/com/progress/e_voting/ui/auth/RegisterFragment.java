package com.progress.e_voting.ui.auth;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.AsyncTaskLoader;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.progress.e_voting.AuthActivity;
import com.progress.e_voting.R;
import com.progress.e_voting.models.Voter;
import com.progress.e_voting.utils.Util;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;


public class RegisterFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "RegisterFragment";

    private String mParam1;
    private String mParam2;

    private TextView mAlreadyAccount;
    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mDOBField;
    private Button mRegisterButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;

    public RegisterFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mUsernameField = view.findViewById(R.id.id_register_username);
        mPasswordField = view.findViewById(R.id.id_register_password);
        mDOBField = view.findViewById(R.id.id_register_dob);

        mAlreadyAccount = view.findViewById(R.id.id_create_account);
        mRegisterButton = view.findViewById(R.id.id_register_button);

        mRegisterButton.setOnClickListener(this);
        mAlreadyAccount.setOnClickListener(this::onClickAlreadyAccount);

        return view;
    }

    private void onClickAlreadyAccount(View view) {
        AuthActivity.SwitchFragment(getParentFragmentManager(),
                LoginFragment.newInstance("Login", "Fragment"), R.id.auth_fragment_container);
    }

    @Override
    public void onClick(View v) {
        String email = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();
        String dob = mDOBField.getText().toString();

        Log.d(TAG, String.format("onClick: EMAIL: %s | PASSWORD: %s | DOB: %s", email, password, dob));
        Util.ShowProgress.showProgress(getContext(), "Registration in Progress");

        createAccount(email, password, dob);

//        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void unused) {
//                onClickAlreadyAccount(getView());
//                Util.ShowProgress.dismissProgress();
//                Util.Alert.getInstance().showAlert(getContext(), "Hello, You are successfully Registered", R.layout.alert_dialog);
//            }
//        };
//        asyncTask.execute();
    }

    private void createAccount(String uEmail, String uPassword, String uDOB) {
        mStore = FirebaseFirestore.getInstance();
        mAuth.createUserWithEmailAndPassword(uEmail, uPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // TODO alert user of success
                Toast.makeText(getContext(), "Your Registration was Successful", Toast.LENGTH_SHORT).show();

                // TODO add voter to collections
                String uid = NanoIdUtils.randomNanoId(new SecureRandom(), "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray(), 10);
                addVoterToFirestore(uid, uEmail, uDOB);

            } else if (task.isCanceled()) {
                // TODO alert user of failed registration
            }

            // TODO clear form
            mUsernameField.setText("");
            mPasswordField.setText("");
            mDOBField.setText("");

        });
    }

    private void addVoterToFirestore(String uid, String uEmail, String uDOB) {
        mStore.collection("/voters").document(mAuth.getCurrentUser().getUid()).set(new Voter(uid, uEmail, uDOB))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // TODO dismiss progress bar
                        Util.ShowProgress.dismissProgress();

                        // TODO redirect to login page
                        AuthActivity.SwitchFragment(getParentFragmentManager(),
                                LoginFragment.newInstance("Login", "Fragment"), R.id.auth_fragment_container);

                        Toast.makeText(getContext(), "Voter Added to Firestore Voters Collection",
                                Toast.LENGTH_SHORT).show();
                    } else if (task.isCanceled()) {
                        Toast.makeText(getContext(), "Voter Could not be added", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}