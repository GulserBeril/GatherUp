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

public class Project extends AppCompatActivity {
    BottomNavigationView project_navigation;
    EditText project_search;
    Button project_createprojectgroup, project_projectname1, project_projectname2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project);
        project_navigation = findViewById(R.id.project_navigation);
        project_search = findViewById(R.id.project_search);
        project_createprojectgroup = findViewById(R.id.project_createprojectgroup);
        project_projectname1 = findViewById(R.id.project_projectname1);
        project_projectname2 = findViewById(R.id.project_projectname2);

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
                        startActivity(new Intent(Project.this, MessageInbox.class));
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
        Intent intent = new Intent(this, CreateProject.class);
        startActivity(intent);
    }
}
