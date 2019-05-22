package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateProject_Save extends AppCompatActivity  {

    BottomNavigationView create_project_save_navigation;
    EditText create_project_save_projectname, create_project_save_howmany, create_project_save_projectdescription, create_project_save_projectneeds;
    Button create_project_save_create;
    TextView create_project_save_abilities_list;

    FirebaseAuth auth;
    String currentuserid;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project__save);
        create_project_save_navigation = findViewById(R.id.create_project_save_navigation);
        create_project_save_projectname = findViewById(R.id.create_project_save_projectname);
        create_project_save_howmany = findViewById(R.id.create_project_save_howmany);
        create_project_save_projectdescription = findViewById(R.id.create_project_save_projectdescription);
        create_project_save_projectneeds = findViewById(R.id.create_project_save_projectneeds);
        create_project_save_create = findViewById(R.id.create_project_save_create);
        create_project_save_abilities_list = findViewById(R.id.create_project_save_abilities_list);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        create_project_save_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(CreateProject_Save.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(CreateProject_Save.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(CreateProject_Save.this, Message.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(CreateProject_Save.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(CreateProject_Save.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }

    public void create_project_save_howmany_click(View view) {


    }



    public void create_project_save_save_click(View view) {

        final String projectname = create_project_save_projectname.getText().toString();
        final String numberofparticipant = create_project_save_howmany.getText().toString();
        final String projectdescription = create_project_save_projectdescription.getText().toString();
        final String projectneeds = create_project_save_projectneeds.getText().toString();

        if (TextUtils.isEmpty(projectname)) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.enterprojectname), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(numberofparticipant)) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.enterparticipant), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(projectdescription)) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.enterprojectdescription), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(projectneeds)) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.enterprojectneeds), Toast.LENGTH_SHORT).show();
            return;
        }
        currentuserid = auth.getCurrentUser().getUid().toString();
        DocumentReference ref = db.collection("projects").document(currentuserid);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> map = new HashMap<>();

                    map.put("created_userid", currentuserid);
                    map.put("numberofparticipant", numberofparticipant);
                    map.put("projectdescription", projectdescription);
                    map.put("projectname", projectname);
                    map.put("projectneeds", FieldValue.arrayUnion(projectneeds));
                    map.put("projectusers", FieldValue.arrayUnion());

                    db.collection("projects").document().set(map);

                }
            }
        });
        Intent intent = new Intent(getApplicationContext(), CreateProject.class);
        startActivity(intent);
    }

    public void create_project_add_click(View view) {

        String abilities = create_project_save_projectneeds.getText().toString();
        String text = create_project_save_abilities_list.getText().toString();
        text = text + "\n" + abilities;
        create_project_save_abilities_list.setText(text);

        db.collection("projects").document(auth.getCurrentUser().getUid().toString()).update("projectneeds", FieldValue.arrayUnion(abilities));
    }
}
