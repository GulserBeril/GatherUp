package com.gatherup.gahterup;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    TextView login_tr, login_en, login_forgotpassword;
    EditText login_email_et, login_password_et;
    Button login_login, login_register;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login_tr = findViewById(R.id.login_tr);
        login_en = findViewById(R.id.login_en);
        login_forgotpassword = findViewById(R.id.login_forgotpassword);
        login_email_et = findViewById(R.id.login_email_et);
        login_password_et = findViewById(R.id.login_password_et);
        login_login = findViewById(R.id.login_login);
        login_register = findViewById(R.id.login_register);

        auth = FirebaseAuth.getInstance();

    }

    public void login_login_click(View view) {
        String email = login_email_et.getText().toString();
        String password = login_password_et.getText().toString();

        if (TextUtils.isEmpty(login_email_et.getText().toString()) || TextUtils.isEmpty(login_password_et.getText().toString())) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(Login.this, HomePage.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void login_register_click(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void login_forgotpassword_click(View view) {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    public void login_en(View view) {

    }

    public void login_tr(View view) {

    }

}
