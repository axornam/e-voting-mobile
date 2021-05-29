package com.progress.e_voting.ui.auth;

import android.content.Intent;
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

import com.progress.e_voting.MainActivity;
import com.progress.e_voting.R;

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

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
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
        mUsernameField = view.findViewById(R.id.id_login_username);
        mPasswordField = view.findViewById(R.id.id_login_password);
        mNoAccount = view.findViewById(R.id.id_create_account);

        mNoAccount.setOnClickListener(this::onClickNoAccount);
        mLoginButton.setOnClickListener(this);
        return view;
    }

    private void onClickNoAccount(View view) {
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(
                android.R.anim.slide_in_left,  // enter
                android.R.anim.fade_out,  // exit
                android.R.anim.fade_in,   // popEnter
                android.R.anim.slide_out_right  // popExit
        );
        ft.replace(R.id.auth_fragment_container,
                RegisterFragment.newInstance("Register", "Fragment"));
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        String username = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();

        Log.d(TAG, String.format("onClick: Username: %s | Password: %s",
                username, password));

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
        getActivity().finish();
    }
}