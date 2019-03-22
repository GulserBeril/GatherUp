package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Support extends AppCompatActivity {
    BottomNavigationView support_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support);
        support_navigation = findViewById(R.id.support_navigation);

        support_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(Support.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(Support.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(Support.this, MessageInbox.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(Support.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Support.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }
}
