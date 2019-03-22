package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MessageInbox extends AppCompatActivity {
    BottomNavigationView message_inbox_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_inbox);
        message_inbox_navigation = findViewById(R.id.message_inbox_navigation);

        message_inbox_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(MessageInbox.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(MessageInbox.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(MessageInbox.this, MessageInbox.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(MessageInbox.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(MessageInbox.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }
}
