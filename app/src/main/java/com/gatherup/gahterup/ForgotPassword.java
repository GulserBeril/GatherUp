package com.gatherup.gahterup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity {
    EditText forgotpassword_emailaddress, forgotpassword_phonenumber, forgotpassword_entercode;
    Button forgotpassword_send_email, forgotpassword_send_phonenumber, forgotpassword_ok;

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
    }
    public void forgotpassword_ok_click(View view){
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}
