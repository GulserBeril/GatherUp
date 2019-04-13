package com.gatherup.gahterup;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Search extends AppCompatActivity {
    BottomNavigationView search_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        search_navigation = findViewById(R.id.search_navigation);

        search_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(Search.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(Search.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(Search.this, MessageInbox.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(Search.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Search.this, Profile_Edit.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }
}
