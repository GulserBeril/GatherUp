package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateProject_Save extends AppCompatActivity implements View.OnTouchListener, AdapterView.OnItemClickListener {

    BottomNavigationView create_project_save_navigation;
    EditText create_project_save_projectname, create_project_save_howmany, create_project_save_projectdescription, create_project_save_projectneeds;
    Button create_project_save_create;
    ListPopupWindow create_project_save_lpw;
    String[] create_project_save_list;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project__save);
        create_project_save_navigation = findViewById(R.id.create_project_save_navigation);
        create_project_save_projectname = findViewById(R.id.create_project_save_projectname);
        create_project_save_howmany = findViewById(R.id.create_project_save_howmany);
        create_project_save_projectdescription = findViewById(R.id.create_project_save_projectdescription);
        create_project_save_projectneeds = findViewById(R.id.create_project_save_projectneeds);
        create_project_save_create = findViewById(R.id.create_project_save_create);
        create_project_save_howmany.setOnTouchListener(this);

        create_project_save_list = new String[]{"1", "2", "3", "4"};
        create_project_save_lpw = new ListPopupWindow(this);
        create_project_save_lpw.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, create_project_save_list));
        create_project_save_lpw.setAnchorView(create_project_save_howmany);
        create_project_save_lpw.setModal(true);
        create_project_save_lpw.setOnItemClickListener(this);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        create_project_save_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(CreateProject_Save.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(CreateProject_Save.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(CreateProject_Save.this, Message.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(CreateProject_Save.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(CreateProject_Save.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }

    public void create_project_save_howmany_click(View view) {


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() >= (v.getWidth() - ((EditText) v)
                    .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                create_project_save_lpw.show();
                return true;
            }
        }
        return false;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = create_project_save_list[position];
        create_project_save_howmany.setText(item);
        create_project_save_lpw.dismiss();
    }

    public void create_project_save_save_click(View view) {
        final String projectname = create_project_save_projectname.getText().toString();
        final String numberofparticipant = create_project_save_howmany.getText().toString();
        final String projectdescription = create_project_save_projectdescription.getText().toString();
        final String projectneeds = create_project_save_projectneeds.getText().toString();

        if (TextUtils.isEmpty(projectname)) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.enterprojectname), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(numberofparticipant)) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.enterparticipant), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(projectdescription)) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.enterprojectdescription), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(projectneeds)) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.enterprojectneeds), Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map2 = new HashMap<>();
        map2.put("projectname", projectname);
        map2.put("numberofparticipant", numberofparticipant);
        map2.put("projectdescription", projectdescription);
        map2.put("projectneeds", projectneeds);

        db.collection("projects").document(auth.getCurrentUser().getUid().toString()).set(map2);

        Intent intent = new Intent(getApplicationContext(), CreateProject.class);
        startActivity(intent);
    }
}
