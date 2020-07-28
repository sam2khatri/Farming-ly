package com.example.smartagriculture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference myRef;
    String username;
    String password;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseAuth mAuth;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username_ = (EditText) findViewById(R.id.username);
        final EditText password_ = (EditText) findViewById(R.id.password);
        btn = (Button) findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            // FirebaseUser mfirebaseuser = mAuth.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mfirebaseuser = mAuth.getCurrentUser();
                //FirebaseUser mFirebaseUser = mAuthListener.g
                if (mfirebaseuser != null) {
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "You are not logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =username_.getText().toString();
                String password = password_.getText().toString();
                if (email.isEmpty()) {
                    username_.setError("Please enter email");
                    username_.requestFocus();
                } else if (password.isEmpty()) {
                    password_.setError("Please enter password");
                    password_.requestFocus();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill the fields", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(LoginActivity.this, "Sign in done", Toast.LENGTH_SHORT).show();
                                //Intent i = new Intent(MainActivity.this, home.class);
                                //startActivity(i);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            } else {
                                //Intent i = new Intent(MainActivity.this, home.class);
                                // startActivity(i);
                                Toast.makeText(LoginActivity.this, "Sign in error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}