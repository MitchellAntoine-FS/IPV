package com.fullsail.apolloarchery.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fullsail.apolloarchery.LoginActivity;
import com.fullsail.apolloarchery.R;
import com.fullsail.apolloarchery.object.CreateAccountListener;
import com.fullsail.apolloarchery.object.Person;
import com.fullsail.apolloarchery.util.PersonStorageUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

public class CreateAccountFragment extends Fragment {

    public static final String TAG = "CreateAccountFragment";
    private FirebaseAuth mAuth;
    Button createAccountBtn;
    TextView loginBtn;
    CreateAccountListener mListener;

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CreateAccountListener) {
            mListener = (CreateAccountListener) context;
        }
    }

    public static CreateAccountFragment newInstance() {
        return new CreateAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        loginBtn = view.findViewById(R.id.loginBtn_createAccount);
        loginBtn.setOnClickListener(v -> {
            Intent loginIntent = new Intent(requireContext(), LoginActivity.class);
            startActivity(loginIntent);
        });

        createAccountBtn = view.findViewById(R.id.create_account_button);
        createAccountBtn.setOnClickListener(v -> {

            EditText etFirstNam = view.findViewById(R.id.first_name_entry);
            String firstName = etFirstNam.getText().toString();

            if (TextUtils.isEmpty(firstName)) {
                etFirstNam.setError("Required.");
            }else {
                etFirstNam.setError(null);
            }

            EditText etLastNam = view.findViewById(R.id.last_name_entry);
            String lastName = etLastNam.getText().toString();

            if (TextUtils.isEmpty(lastName)) {
                etLastNam.setError("Required.");
            }else {
                etLastNam.setError(null);
            }

            EditText etEmail = view.findViewById(R.id.email_entry_create_account);
            String email = etEmail.getText().toString();

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Required.");
            }else {
                etEmail.setError(null);
            }

            EditText etPassword = view.findViewById(R.id.password_entry_create_account);
            String pwd = etPassword.getText().toString();

            if (TextUtils.isEmpty(pwd)) {
                etPassword.setError("Required.");
            }else {
                etPassword.setError(null);
            }

            if (!(firstName.trim().length() == 0) || !(lastName.trim().length() == 0)
                    || !(email.trim().length() == 0) || !(pwd.trim().length() == 0)) {

                Person person = new Person(firstName, lastName);
                PersonStorageUtil.savePerson(getContext(), person);

                // Create account
                createFirebaseAccount(email, pwd);

            }
        });

    }

    private void createFirebaseAccount(String email, String pwd) {

        mAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign up success
                        Log.d(TAG, "createUserWithEmail: success");

                        ArrayList<Person> userNam = PersonStorageUtil.loadPeople(getActivity());
                        Toast.makeText(getContext(), "Hi " + userNam.get(0).getFirst_name() , Toast.LENGTH_SHORT).show();

                        // Add user name to Firebase
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        String user_name = userNam.get(0).getFirst_name() + " " + userNam.get(0).getLast_name();

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(user_name)
                                .build();

                        if (user != null) {
                            user.updateProfile(profileUpdate).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Log.i(TAG, "User Updated: ");
                                }
                            });
                        }

                        mListener.closeSignup();

                    } else {
                        // If sign up fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail: failure", task.getException());
                        Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}