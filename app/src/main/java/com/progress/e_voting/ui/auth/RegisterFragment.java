package com.progress.e_voting.ui.auth;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

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

import com.progress.e_voting.R;
import com.progress.e_voting.utils.Util;


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

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mAlreadyAccount = view.findViewById(R.id.id_create_account);
        mUsernameField = view.findViewById(R.id.id_register_username);
        mPasswordField = view.findViewById(R.id.id_register_password);
        mDOBField = view.findViewById(R.id.id_register_dob);
        mRegisterButton = view.findViewById(R.id.id_register_button);

        mRegisterButton.setOnClickListener(this);
        mAlreadyAccount.setOnClickListener(this::onClickAlreadyAccount);

        return view;
    }

    private void onClickAlreadyAccount(View view) {
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(
                android.R.anim.slide_in_left,  // enter
                android.R.anim.fade_out,  // exit
                android.R.anim.fade_in,   // popEnter
                android.R.anim.slide_out_right  // popExit
        );
        ft.replace(R.id.auth_fragment_container, LoginFragment.newInstance("Login", "Fragment"));
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        String username = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();
        String dob = mDOBField.getText().toString();

        Log.d(TAG, String.format("onClick: USERNAME: %s | PASSWORD: %s | DOB: %s", username, password, dob));
        Util.ShowProgress.showProgress(getContext(), "Registration in Progress");

        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                onClickAlreadyAccount(getView());
                Util.ShowProgress.dismissProgress();
                Util.Alert.getInstance().showAlert(getContext(), "Hello, You are successfully Registered", R.layout.alert_dialog);
            }
        };

        asyncTask.execute();
    }
}