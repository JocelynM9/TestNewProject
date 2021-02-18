package com.example.testnewproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public EditText emailId, password;
    Button btnSignUp;
    TextView goToSignIn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email);
        password = findViewById(R.id.password);
        goToSignIn = findViewById(R.id.goToSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        goToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = emailId.getText().toString().trim();
                String getPassword = password.getText().toString().trim();
                if(getEmail.isEmpty()){
                    emailId.setError("Please enter email");
                    emailId.requestFocus();
                }else if(getPassword.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }else if(getEmail.isEmpty() && getPassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields Are Empty!!", Toast.LENGTH_SHORT).show();

                }else if(!getEmail.isEmpty() && !getPassword.isEmpty()){
                    firebaseAuth.createUserWithEmailAndPassword(getEmail, getPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Sign Up Unsuccessfull", Toast.LENGTH_SHORT).show();

                            }else{
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));

                            }
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "Error Occured!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
