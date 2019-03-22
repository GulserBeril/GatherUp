package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class Message extends AppCompatActivity {
    BottomNavigationView message_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        message_navigation = findViewById(R.id.message_navigation);

        message_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(Message.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(Message.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(Message.this, MessageInbox.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(Message.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Message.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }
    public  void  message_back_click(View view){
        super.onBackPressed();
    }
}
