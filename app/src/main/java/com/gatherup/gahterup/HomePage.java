package com.gatherup.gahterup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.gatherup.gahterup.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        homepage_profilepicture = findViewById(R.id.homepage_profilepicture);
        homepage_search_et = findViewById(R.id.homepage_search_et);
        homepage_date_et = findViewById(R.id.homepage_date_et);
        homepage_search = findViewById(R.id.homepage_search);
        homepage_navigation = findViewById(R.id.homepage_navigation);
        homepage_listview = findViewById(R.id.homepage_listview);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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
                String user_id = userid_list.get(position);
                Intent intent = new Intent(HomePage.this, Profile.class);
                intent.putExtra("userid", user_id);
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
