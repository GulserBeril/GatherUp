package com.gatherup.gahterup;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.gatherup.gahterup.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    Button register_back, register_register;
    EditText register_name, register_surname, register_email, register_password, register_passwordagain;
    CheckBox register_policy_checkbox;

    private FirebaseAuth auth;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        register_back = findViewById(R.id.register_back);
        register_register = findViewById(R.id.register_register);
        register_name = findViewById(R.id.register_name);
        register_surname = findViewById(R.id.register_surname);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_passwordagain = findViewById(R.id.register_passwordagain);
        register_policy_checkbox = findViewById(R.id.register_policy_checkbox);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        auth = FirebaseAuth.getInstance();
    }

    public void register_back_click(View view) {
        super.onBackPressed();
    }

    public void register_register_click(View view) {

        final User user = new User(register_name.getText().toString(), register_surname.getText().toString(), register_email.getText().toString(), register_password.getText().toString());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getName()).exists()) {
                    Toast.makeText(Register.this, "account already exist", Toast.LENGTH_SHORT).show();
                } else {
                    reference.child(user.getName()).setValue(user);
                    Toast.makeText(Register.this, "success register", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, HomePage.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        auth.createUserWithEmailAndPassword(register_email.getText().toString(), register_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Register.this, "Yetkilendirme Hatası",
                            Toast.LENGTH_SHORT).show();
                }

                //İşlem başarılı olduğu takdir de giriş yapılıp MainActivity e yönlendiriyoruz.
                else {
                    startActivity(new Intent(Register.this, HomePage.class));
                    finish();
                }
            }
        });

    }
}