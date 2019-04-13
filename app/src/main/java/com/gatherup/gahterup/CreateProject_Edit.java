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

public class CreateProject_Edit extends AppCompatActivity implements View.OnTouchListener, AdapterView.OnItemClickListener {
    BottomNavigationView create_project_edit_navigation;
    EditText create_project_edit_projectname, create_project_edit_howmany, create_project_edit_projectdescription, create_project_edit_projectneeds;
    Switch create_project_edit_onlymanager_switch, create_project_edit_everymember_switch;
    Button create_project_edit_create;
    ListPopupWindow create_project_edit_lpw;
    String[] create_project_edit_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project_edit);
        create_project_edit_navigation = findViewById(R.id.create_project_edit_navigation);
        create_project_edit_projectname = findViewById(R.id.create_project_edit_projectname);
        create_project_edit_howmany = findViewById(R.id.create_project_edit_howmany);
        create_project_edit_projectdescription = findViewById(R.id.create_project_edit_projectdescription);
        create_project_edit_projectneeds = findViewById(R.id.create_project_edit_projectneeds);
        create_project_edit_onlymanager_switch = findViewById(R.id.create_project_edit_onlymanager_switch);
        create_project_edit_everymember_switch = findViewById(R.id.create_project_edit_everymember_switch);
        create_project_edit_create = findViewById(R.id.create_project_edit_create);

        create_project_edit_howmany.setOnTouchListener(this);

        create_project_edit_list = new String[]{"1", "2", "3", "4"};
        create_project_edit_lpw = new ListPopupWindow(this);
        create_project_edit_lpw.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, create_project_edit_list));
        create_project_edit_lpw.setAnchorView(create_project_edit_howmany);
        create_project_edit_lpw.setModal(true);
        create_project_edit_lpw.setOnItemClickListener(this);


        create_project_edit_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(CreateProject_Edit.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(CreateProject_Edit.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(CreateProject_Edit.this, Message.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(CreateProject_Edit.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(CreateProject_Edit.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }

    public void create_project_edit_howmany_click(View view) {

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() >= (v.getWidth() - ((EditText) v)
                    .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                create_project_edit_lpw.show();
                return true;
            }
        }
        return false;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = create_project_edit_list[position];
        create_project_edit_howmany.setText(item);
        create_project_edit_lpw.dismiss();
    }

    public void create_project_edit_save_click(View view) {
        Intent intent = new Intent(CreateProject_Edit.this, CreateProject.class);
        startActivity(intent);

    }
}