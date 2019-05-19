package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Project extends AppCompatActivity {
    BottomNavigationView project_navigation;
    EditText project_search;
    Button project_createprojectgroup, project_projectname1, project_projectname2;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project);
        project_navigation = findViewById(R.id.project_navigation);
        project_search = findViewById(R.id.project_search);
        project_createprojectgroup = findViewById(R.id.project_createprojectgroup);
        project_projectname1 = findViewById(R.id.project_projectname1);
        project_projectname2 = findViewById(R.id.project_projectname2);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        DocumentReference ref = db.collection("projects").document(auth.getCurrentUser().getUid().toString());
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        project_projectname1.setVisibility(View.VISIBLE);
                        String projectname = task.getResult().getData().get("projectname").toString();
                        project_projectname1.setText(projectname);
                    }
                    else {
                        Toast.makeText(Project.this, getApplicationContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(Project.this, getApplicationContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        project_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(Project.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(Project.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(Project.this, Message.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(Project.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Project.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }

    public void project_createprojectgroup_click(View view) {
        Intent intent = new Intent(this, CreateProject_Save.class);
        startActivity(intent);
    }

    public void project_project_click(View view) {

        Intent intent = new Intent(this, InsideProject.class);
        startActivity(intent);
    }
}
