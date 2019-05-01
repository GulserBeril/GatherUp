package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    EditText forgotpassword_emailaddress, forgotpassword_phonenumber, forgotpassword_entercode;
    Button forgotpassword_send_email, forgotpassword_send_phonenumber, forgotpassword_ok;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        forgotpassword_emailaddress = findViewById(R.id.forgotpassword_emailaddress);
        forgotpassword_phonenumber = findViewById(R.id.forgotpassword_phonenumber);
        forgotpassword_entercode = findViewById(R.id.forgotpassword_entercode);
        forgotpassword_send_email = findViewById(R.id.forgotpassword_send_email);
        forgotpassword_send_phonenumber = findViewById(R.id.forgotpassword_send_phonenumber);
        forgotpassword_ok = findViewById(R.id.forgotpassword_ok);

        auth = FirebaseAuth.getInstance();
    }

    public void forgotpassword_ok_click(View view) {
        String email = forgotpassword_emailaddress.getText().toString();
        if (email.equals("")) {
            Toast.makeText(this, getApplicationContext().getString(R.string.requiredfields), Toast.LENGTH_SHORT).show();
        } else {
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPassword.this, getApplicationContext().getString(R.string.checkemail), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPassword.this, Login.class);
                        startActivity(intent);
                    }else {
                        String error = task.getException().getMessage();
                        Toast.makeText(ForgotPassword.this, error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
