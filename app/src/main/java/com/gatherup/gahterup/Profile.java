package com.gatherup.gahterup;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gatherup.gahterup.Model.ProjectModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    BottomNavigationView profile_navigation;
    CircleImageView profile_profilepicture;
    Button profile_invite;
    TextView profile_name, profile_surname, profile_email, profile_birthdate, profile_universityname, profile_entranceyear, profile_year, profile_duty, profile_position, profile_projectname, profile_description, profile_abilities_list;
    Spinner profile_projectnames;

    FirebaseAuth auth;
    FirebaseFirestore db;
    private FirebaseStorage storageReference;
    String userid = null;
    String currentuserid;
    String currentusername;
    List<String> project_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, HomePage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });


        userid = getIntent().getStringExtra("userid");

        profile_navigation = findViewById(R.id.profile_navigation);

        profile_profilepicture = findViewById(R.id.profile_profilepicture);
        profile_name = findViewById(R.id.profile_name);
        profile_surname = findViewById(R.id.profile_surname);
        profile_email = findViewById(R.id.profile_email);
        profile_birthdate = findViewById(R.id.profile_birthdate);
        profile_universityname = findViewById(R.id.profile_universityname);
        profile_entranceyear = findViewById(R.id.profile_entranceyear);
        profile_year = findViewById(R.id.profile_year);
        profile_duty = findViewById(R.id.profile_duty);
        profile_position = findViewById(R.id.profile_position);
        profile_projectname = findViewById(R.id.profile_projectname);
        profile_description = findViewById(R.id.profile_description);
        profile_abilities_list = findViewById(R.id.profile_abilities_list);
        profile_invite = findViewById(R.id.profile_invite);
        profile_projectnames = findViewById(R.id.profile_projectnames);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance();

        currentuserid = auth.getCurrentUser().getUid().toString();
        currentusername = auth.getCurrentUser().getEmail().toString();
        //userid kullanıcı arama sayfasındaki seçili kişinin idsidir
        if (userid == null) {
            userid = currentuserid;
            profile_invite.setVisibility(View.GONE);
            profile_projectnames.setVisibility(View.GONE);
        } else {
            fillProjects();
        }

        DocumentReference ref = db.collection("users").document(userid);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String name = task.getResult().getData().get("name").toString();
                        String surname = task.getResult().getData().get("surname").toString();
                        String email = task.getResult().getData().get("email").toString();
                        String birthdate = task.getResult().getData().get("birthdate").toString();
                        String universityname = task.getResult().getData().get("universityname").toString();
                        String entranceyear = task.getResult().getData().get("entranceyear").toString();
                        ArrayList<String> abilities_list = (ArrayList<String>) document.get("abilities");
                        String year = task.getResult().getData().get("year").toString();
                        String duty = task.getResult().getData().get("duty").toString();
                        String position = task.getResult().getData().get("position").toString();
                        String projectname = task.getResult().getData().get("projectname").toString();
                        String description = task.getResult().getData().get("projectdescription").toString();
                        profile_name.setText(name);
                        profile_surname.setText(surname);
                        profile_email.setText(email);
                        profile_birthdate.setText(birthdate);
                        profile_universityname.setText(universityname);
                        profile_entranceyear.setText(entranceyear);
                        //profile_abilities_list.setText(abilities_list);
                        profile_year.setText(year);
                        profile_duty.setText(duty);
                        profile_position.setText(position);
                        profile_projectname.setText(projectname);
                        profile_description.setText(description);

                        for (int i = 0; i < abilities_list.size(); i++) {
                            profile_abilities_list.setText(profile_abilities_list.getText() + "\n" + abilities_list.get(i) + "\n");
                        }

                    } else {
                        Toast.makeText(Profile.this, getApplicationContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(Profile.this, getApplicationContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
        profile_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Invite();
            }
        });

        String pp = auth.getCurrentUser().getUid().toString();
        StorageReference storageReference1 = storageReference.getReference().child(pp);

        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Profile.this).load(uri).into(profile_profilepicture);
            }
        });
/*
        profile_profilepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        profile_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(Profile.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(Profile.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(Profile.this, Message.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(Profile.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Profile.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }

    private void Invite() {
        String projectid = project_list.get(profile_projectnames.getSelectedItemPosition());
        Map<String, Object> map = new HashMap<>();

        map.put("user_id", userid);
        map.put("project_id", projectid);
        map.put("inviter", currentuserid);
        map.put("inviter_name", currentusername);
        map.put("state", false);
        map.put("project_name", profile_projectnames.getSelectedItem().toString());
        map.put("notification_type", "invite");
        db.collection("notifications").document().set(map);
    }

    private void fillProjects() {

        CollectionReference projects = db.collection("projects");


        projects.whereEqualTo("created_userid", currentuserid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            System.err.println(getApplicationContext().getString(R.string.failed) + e);
                            return;
                        }
                        //  List<UserModel> listUsers = new ArrayList<UserModel>();
                        List<String> namelist = new ArrayList<>();
                        project_list = new ArrayList<>();
                        for (DocumentSnapshot doc : snapshots) {
                            ProjectModel user = doc.toObject(ProjectModel.class);
                            namelist.add(user.getProjectname());
                            project_list.add(doc.getId());
                            // verileri bu liste aldık artık ekranda gösterebiliriz
                            //listUsers.add(userModel);
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Profile.this, android.R.layout.simple_spinner_item, namelist);
                        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        // homepage_listview.setVisibility(View.VISIBLE);
                        profile_projectnames.setAdapter(arrayAdapter);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, Login.class));
                return true;
            case R.id.edit:
                Intent intent = new Intent(Profile.this, Profile_Edit.class);
                startActivity(intent);
                return true;
        }
        return false;
    }
}
