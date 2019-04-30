package com.gatherup.gahterup;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    Button register_back, register_register;
    EditText register_name, register_surname, register_email, register_password, register_passwordagain;
    CheckBox register_policy_checkbox;

    FirebaseAuth auth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> abilitieslist = new ArrayList<String>();

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

        auth = FirebaseAuth.getInstance();
    }

    public void register_back_click(View view) {
        super.onBackPressed();
    }

    public void register_register_click(View view) {

        final String name = register_name.getText().toString();
        final String surname = register_surname.getText().toString();
        final String email = register_email.getText().toString();
        final String password = register_password.getText().toString();
        final String passwordagain = register_passwordagain.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), this.getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(surname)) {
            Toast.makeText(getApplicationContext(), this.getString(R.string.enter_surname), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), this.getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), this.getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(passwordagain)) {
            Toast.makeText(getApplicationContext(), this.getString(R.string.password_match), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!register_policy_checkbox.isChecked()) {
            register_register.setClickable(false);
            Toast.makeText(this, this.getString(R.string.accept_policy), Toast.LENGTH_SHORT).show();
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Map<String, Object> map = new HashMap<>();

                map.put("name", "" + name);
                map.put("surname", "" + surname);
                map.put("email", "" + email);
                map.put("password", "" + password);
                map.put("birthdate", "");
                map.put("universityname", "");
                map.put("entranceyear", "");
                map.put("abilities", "");
                map.put("year", "");
                map.put("duty", "");
                map.put("position", "");
                map.put("projectname", "");
                map.put("projectdescription", "");

                db.collection("users").document(auth.getCurrentUser().getUid().toString()).set(map);

                Map<String, String> map2 = new HashMap<>();
                map2.put("project name", "");
                map2.put("number of people", "");
                map2.put("description","");
                map2.put("project needs","");

                db.collection("projects").document(auth.getCurrentUser().getUid().toString()).set(map2);

                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, getApplicationContext().getString(R.string.failed_registration), Toast.LENGTH_SHORT).show();
            }
        });

    }
}





