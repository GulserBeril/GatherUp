package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.gatherup.gahterup.Enums.Enums;
import com.gatherup.gahterup.Helper.UserHelper;
import com.gatherup.gahterup.Model.ProjectModel;
import com.gatherup.gahterup.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class HomePage extends AppCompatActivity {
    ImageView homepage_profilepicture;
    EditText homepage_search_et, homepage_date_et;
    Button homepage_search;
    BottomNavigationView homepage_navigation;
    ListView homepage_listview;
    List<String> userid_list;

    FirebaseAuth auth;
    FirebaseFirestore db;
    UserModel userModel;
    List<ProjectModel> project_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        homepage_profilepicture = findViewById(R.id.homepage_profilepicture);
        homepage_search_et = findViewById(R.id.homepage_search_et);
        //homepage_date_et = findViewById(R.id.homepage_date_et);
        homepage_search = findViewById(R.id.homepage_search);
        homepage_navigation = findViewById(R.id.homepage_navigation);
        homepage_listview = findViewById(R.id.project_listview);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userModel = new UserModel();

        homepage_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchValue = homepage_search_et.getText().toString();
                getUsers(searchValue);
            }
        });
        homepage_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectModel projectModel = project_list.get(position);
                Intent intent = new Intent(getApplicationContext(), CreateProject.class);
                intent.putExtra("projectid", projectModel.getProjectID());
                intent.putExtra("isapply", true);
                startActivity(intent);
            }
        });

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


        DocumentReference ref = db.collection("users").document(auth.getCurrentUser().getUid().toString());
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
                        userModel.setName(name);
                        userModel.setSurname(surname);
                        userModel.setEmail(email);
                        userModel.setBirthdate(birthdate);
                        userModel.setUniversityname(universityname);
                        userModel.setEntranceyear(entranceyear);
                        userModel.setYear(year);
                        userModel.setDuty(duty);
                        userModel.setPosition(position);
                        userModel.setProjectname(projectname);
                        userModel.setProjectdescription(description);
                        userModel.setAbilities(abilities_list);
                        getProjects();
                    }
                }
            }
        });

    }

    private void getProjects() {

        final List<String> abilities = userModel.getAbilities();

        db.collection("projects").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {

                        List<String> projectNamelist = new ArrayList<>();
                        project_list = new ArrayList<>();
                        for (DocumentSnapshot doc : snapshots) {
                            ProjectModel projectModel = doc.toObject(ProjectModel.class);

                            List<String> projectNeeds = projectModel.getProjectneeds();

                            boolean isCheckValue = isCheck(abilities, projectNeeds);

                            if (isCheckValue && !auth.getCurrentUser().getUid().equals(projectModel.getCreated_userid())) {
                                projectModel.setProjectID(doc.getId());
                                project_list.add(projectModel);
                                projectNamelist.add(projectModel.getProjectname());
                            }
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomePage.this, android.R.layout.simple_list_item_1, projectNamelist);
                        homepage_listview.setAdapter(arrayAdapter);
                    }
                });


    }


    private boolean isCheck(List<String> abilities, List<String> projectNeeds) {
        for (String item : abilities) {
            if (projectNeeds.contains(item)) {
                return true;
            }
        }
        return false;
    }

    private void getUsers(String searchValue) {

        db.collection("users").whereGreaterThanOrEqualTo("name", searchValue)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            System.err.println("Hata oluştu:" + e);
                            return;
                        }
                        //  List<UserModel> listUsers = new ArrayList<UserModel>();
                        List<String> namelist = new ArrayList<>();
                        userid_list = new ArrayList<>();
                        for (DocumentSnapshot doc : snapshots) {
                            UserModel userModel = doc.toObject(UserModel.class);
                            namelist.add(userModel.getName());
                            userid_list.add(doc.getId());
                            // verileri bu liste aldık artık ekranda gösterebiliriz
                            //listUsers.add(userModel);
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomePage.this, android.R.layout.simple_list_item_1, namelist);
                        homepage_listview.setVisibility(View.VISIBLE);
                        homepage_listview.setAdapter(arrayAdapter);

                    }
                });
    }
}
