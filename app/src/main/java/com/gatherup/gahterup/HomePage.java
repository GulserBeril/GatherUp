package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class HomePage extends AppCompatActivity {
    ImageView homepage_profilepicture;
    EditText homepage_search_et, homepage_date_et;
    Button homepage_search;
    BottomNavigationView homepage_navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        homepage_profilepicture = findViewById(R.id.homepage_profilepicture);
        homepage_search_et = findViewById(R.id.homepage_search_et);
        homepage_date_et = findViewById(R.id.homepage_date_et);
        homepage_search = findViewById(R.id.homepage_search);
        homepage_navigation = findViewById(R.id.homepage_navigation);

        homepage_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(HomePage.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(HomePage.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(HomePage.this, Message.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(HomePage.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(HomePage.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }
}
