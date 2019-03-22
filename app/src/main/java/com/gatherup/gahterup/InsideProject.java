package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class InsideProject extends AppCompatActivity {
    BottomNavigationView inside_project_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_project);
        inside_project_navigation = findViewById(R.id.inside_project_navigation);

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
