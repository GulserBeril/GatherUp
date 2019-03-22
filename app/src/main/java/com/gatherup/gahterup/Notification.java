package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

public class Notification extends AppCompatActivity {
    BottomNavigationView notification_navigation;
    EditText navigation_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        navigation_search = findViewById(R.id.notification_search);
        notification_navigation = findViewById(R.id.notification_navigation);

        notification_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(Notification.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(Notification.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(Notification.this, MessageInbox.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(Notification.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Notification.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }
}
