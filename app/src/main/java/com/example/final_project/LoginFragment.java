package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editTextPassword, editTextEmail;
    private Button btnLogin;
    private ProgressBar progressBar;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        editTextEmail = (EditText) view.findViewById(R.id.et_email);
        editTextPassword = (EditText) view.findViewById(R.id.et_password);

        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_login){
            loginUserAccount();
        }
    }

    private void loginUserAccount() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if( !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            //Moving to Home Activity
                            Intent intent = new Intent (getContext(), HomeActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), "Login failed!", Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}