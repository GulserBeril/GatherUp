package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gatherup.gahterup.Model.NotificationModel;
import com.gatherup.gahterup.Model.ProjectModel;
import com.gatherup.gahterup.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class Project extends AppCompatActivity {
    BottomNavigationView project_navigation;
    //EditText project_search;
    Button project_createprojectgroup;
    ListView project_listview;
    List<String> project_list;
    String currentuserid;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        project_navigation = findViewById(R.id.project_navigation);
        // project_search = findViewById(R.id.project_search);
        project_createprojectgroup = findViewById(R.id.project_createprojectgroup);
        project_listview = findViewById(R.id.project_listview);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        currentuserid = auth.getCurrentUser().getUid().toString();
        getProjects();

        project_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Project.this, CreateProject.class);
                String projectid = project_list.get(position);
                intent.putExtra("projectid",projectid);
                startActivity(intent);
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

    private void getProjects() {

        db.collection("projects").whereEqualTo("created_userid", currentuserid).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {

                        List<String> projectlist = new ArrayList<>();
                        project_list = new ArrayList<>();
                        for (DocumentSnapshot doc : snapshots) {
                            ProjectModel projectModel = doc.toObject(ProjectModel.class);
                            projectlist.add(projectModel.getProjectname());
                            project_list.add(doc.getId());
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Project.this, android.R.layout.simple_list_item_1, projectlist);
                        project_listview.setAdapter(arrayAdapter);
                    }
                });
    }
}
