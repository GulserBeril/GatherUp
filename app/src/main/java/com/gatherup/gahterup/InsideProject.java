package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class InsideProject extends AppCompatActivity {
    BottomNavigationView inside_project_navigation;

    TextView inside_project_title, inside_project_projectname, inside_project_howmany, inside_project_projectdescription, inside_project_projectneeds;
    Switch inside_project_onlymanager_switch, inside_project_everymember_switch;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_project);
        inside_project_navigation = findViewById(R.id.inside_project_navigation);
        inside_project_title = findViewById(R.id.inside_project_title);
        inside_project_projectname = findViewById(R.id.inside_project_projectname);
        inside_project_howmany = findViewById(R.id.inside_project_howmany);
        inside_project_projectdescription = findViewById(R.id.inside_project_projectdescription);
        inside_project_projectneeds = findViewById(R.id.inside_project_projectneeds);
        inside_project_onlymanager_switch = findViewById(R.id.inside_project_onlymanager_switch);
        inside_project_everymember_switch = findViewById(R.id.inside_project_everymember_switch);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        DocumentReference ref = db.collection("projects").document(auth.getCurrentUser().getUid().toString());
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String projectname = task.getResult().getData().get("projectname").toString();
                        String numberofparticipant = task.getResult().getData().get("numberofparticipant").toString();
                        String projectdescription = task.getResult().getData().get("projectdescription").toString();
                        String projectneeds = task.getResult().getData().get("projectneeds").toString();

                        inside_project_howmany.setText(numberofparticipant);

                        inside_project_projectdescription.setText(projectdescription);
                        inside_project_projectneeds.setText(projectneeds);

                        inside_project_title.setText(projectname);
                        inside_project_projectname.setText(projectname);

                    } else {
                        Toast.makeText(InsideProject.this, getApplicationContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(InsideProject.this, getApplicationContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        inside_project_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(InsideProject.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(InsideProject.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(InsideProject.this, MessageInbox.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(InsideProject.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(InsideProject.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }
}
