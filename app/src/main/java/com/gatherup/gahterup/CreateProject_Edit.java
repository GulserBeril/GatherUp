package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateProject_Edit extends AppCompatActivity implements View.OnTouchListener, AdapterView.OnItemClickListener {
    BottomNavigationView create_project_edit_navigation;
    EditText create_project_edit_projectname, create_project_edit_howmany, create_project_edit_projectdescription, create_project_edit_projectneeds;
    Switch create_project_edit_onlymanager_switch, create_project_edit_everymember_switch;
    Button create_project_edit_create;
    ListPopupWindow create_project_edit_lpw;
    String[] create_project_edit_list;

    FirebaseAuth auth;
    FirebaseFirestore db;

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

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


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
        final String projectname = create_project_edit_projectname.getText().toString();
        final String numberofparticipant = create_project_edit_howmany.getText().toString();
        final String projectdescription = create_project_edit_projectdescription.getText().toString();
        final String projectneeds = create_project_edit_projectneeds.getText().toString();

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

        db.collection("projects").document(auth.getCurrentUser().getUid().toString()).update("projectname", projectname);
        db.collection("projects").document(auth.getCurrentUser().getUid().toString()).update("numberofparticipant", numberofparticipant);
        db.collection("projects").document(auth.getCurrentUser().getUid().toString()).update("projectdescription", projectdescription);
        db.collection("projects").document(auth.getCurrentUser().getUid().toString()).update("projectneeds", projectneeds);

        Intent intent = new Intent(getApplicationContext(), CreateProject.class);
        startActivity(intent);

    }
}