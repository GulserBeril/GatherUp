package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.gatherup.gahterup.Fragments.MessageInbox;
import com.gatherup.gahterup.Fragments.Users;

import java.util.ArrayList;

public class Message extends AppCompatActivity {
    BottomNavigationView message_navigation;
    TabLayout message_tab_layout;
    ViewPager message_view_pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        message_navigation = findViewById(R.id.message_navigation);
        message_tab_layout = findViewById(R.id.message_tab_layout);
        message_view_pager = findViewById(R.id.message_view_pager);

        Toolbar message_toolbar = findViewById(R.id.message_toolbar);
        setSupportActionBar(message_toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new MessageInbox(), this.getString(R.string.inbox));
        viewPagerAdapter.addFragment(new Users(), this.getString(R.string.users));

        message_view_pager.setAdapter(viewPagerAdapter);
        message_tab_layout.setupWithViewPager(message_view_pager);

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
                        startActivity(new Intent(Message.this, Message.class));
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

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    public void message_back_click(View view) {
        super.onBackPressed();
    }
}
