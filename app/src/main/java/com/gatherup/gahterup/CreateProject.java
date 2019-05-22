package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CreateProject extends AppCompatActivity {
    BottomNavigationView create_project_navigation;
    TextView create_project_projectname, create_project_howmany, create_project_projectdescription, create_project_projectneeds, create_project_projectusers;
    Switch create_project_onlymanager_switch, create_project_everymember_switch;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project);
        create_project_navigation = findViewById(R.id.create_project_navigation);
        create_project_projectname = findViewById(R.id.create_project_projectname);
        create_project_howmany = findViewById(R.id.create_project_howmany);
        create_project_projectdescription = findViewById(R.id.create_project_projectdescription);
        create_project_projectneeds = findViewById(R.id.create_project_projectneeds);
        create_project_projectusers = findViewById(R.id.create_project_projectusers);
        /*create_project_onlymanager_switch = findViewById(R.id.create_project_onlymanager_switch);
        create_project_everymember_switch = findViewById(R.id.create_project_everymember_switch);*/

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();





        //create_userid ile current idyi eşleştiremediğim için proje bilgilerine ulaşamıyorum
        DocumentReference ref = db.collection("projects").document();
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String created_userid = task.getResult().getData().get("created_userid").toString();
                        String projectname = task.getResult().getData().get("projectname").toString();
                        String numberofparticipant = task.getResult().getData().get("numberofparticipant").toString();
                        String projectdescription = task.getResult().getData().get("projectdescription").toString();

                        ArrayList<String> projectusers = (ArrayList<String>) document.get("projectusers");
                        ArrayList<String> projectneeds = (ArrayList<String>) document.get("projectneeds");

                        create_project_projectname.setText(projectname);
                        create_project_howmany.setText(numberofparticipant);
                        create_project_projectdescription.setText(projectdescription);

                        for (int i = 0; i < projectneeds.size(); i++) {
                            create_project_projectneeds.setText(create_project_projectneeds.getText() + "\n" + projectneeds.get(i) + "\n");
                        }

                        for (int i = 0; i < projectusers.size(); i++) {
                            create_project_projectusers.setText(create_project_projectusers.getText() + "\n" + projectusers.get(i) + "\n");
                        }

                    } else {
                        Toast.makeText(CreateProject.this, getApplicationContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(CreateProject.this, getApplicationContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        create_project_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(CreateProject.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(CreateProject.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(CreateProject.this, Message.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(CreateProject.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(CreateProject.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }

    public void create_project_edit_click(View view) {
        Intent intent = new Intent(CreateProject.this, CreateProject_Edit.class);
        startActivity(intent);
    }
}