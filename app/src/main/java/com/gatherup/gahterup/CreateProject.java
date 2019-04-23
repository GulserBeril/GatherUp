package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Switch;
import android.widget.TextView;

public class CreateProject extends AppCompatActivity implements View.OnTouchListener, AdapterView.OnItemClickListener {
    BottomNavigationView create_project_navigation;
    TextView create_project_projectname, create_project_howmany, create_project_projectdescription, create_project_projectneeds;
    Switch create_project_onlymanager_switch, create_project_everymember_switch;
    ListPopupWindow create_project_lpw;
    String[] create_project_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project);
        create_project_navigation = findViewById(R.id.create_project_navigation);
        create_project_projectname = findViewById(R.id.create_project_projectname);
        create_project_howmany = findViewById(R.id.create_project_howmany);
        create_project_projectdescription = findViewById(R.id.create_project_projectdescription);
        create_project_projectneeds = findViewById(R.id.create_project_projectneeds);
        create_project_onlymanager_switch = findViewById(R.id.create_project_onlymanager_switch);
        create_project_everymember_switch = findViewById(R.id.create_project_everymember_switch);

        create_project_howmany.setOnTouchListener(this);

        create_project_list = new String[]{"1", "2", "3", "4"};
        create_project_lpw = new ListPopupWindow(this);
        create_project_lpw.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, create_project_list));
        create_project_lpw.setAnchorView(create_project_howmany);
        create_project_lpw.setModal(true);
        create_project_lpw.setOnItemClickListener(this);


        create_project_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(CreateProject.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(CreateProject.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(CreateProject.this, Message.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(CreateProject.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(CreateProject.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() >= (v.getWidth() - ((EditText) v)
                    .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                create_project_lpw.show();
                return true;
            }
        }
        return false;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = create_project_list[position];
        create_project_howmany.setText(item);
        create_project_lpw.dismiss();

    }

    public void create_project_edit_click(View view) {
        Intent intent = new Intent(CreateProject.this, CreateProject_Edit.class);
        startActivity(intent);
    }
}