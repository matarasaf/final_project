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

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextRePassword;
    private Button btnRegister;
    private ProgressBar progressBar;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        editTextFullName = (EditText) view.findViewById(R.id.et_name);
        editTextEmail = (EditText) view.findViewById(R.id.et_email);
        editTextPassword = (EditText) view.findViewById(R.id.et_password);
        editTextRePassword = (EditText) view.findViewById(R.id.et_repassword);

        btnRegister = (Button) view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_register){
            registerUser();
        }
    }

    private void registerUser() {
        WeekFragment frag;
        String fullName = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String rePassword = editTextRePassword.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }

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

        if(rePassword.isEmpty()){
            editTextRePassword.setError("RePassword is required!");
            editTextRePassword.requestFocus();
            return;
        }

        if( !rePassword.equals(password)){
            editTextRePassword.setError("RePassword and password must be the same!");
            editTextRePassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "User has been registered successfully!", Toast.LENGTH_LONG).show();
                            //Moving to Home Activity
                            Intent intent = new Intent (getContext(), HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}