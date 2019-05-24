package com.gatherup.gahterup;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ListView;

import com.gatherup.gahterup.Model.NotificationModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Notification extends AppCompatActivity {
    BottomNavigationView notification_navigation;
    ListView notification_listview;
    List<String> userid_list;
    String currentuserid;

    FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        notification_listview = findViewById(R.id.notification_listview);
        notification_navigation = findViewById(R.id.notification_navigation);

        auth = FirebaseAuth.getInstance();
        currentuserid = auth.getCurrentUser().getUid().toString();

        notification_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                yesno();
            }
        });
        getNotification();

        notification_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(Notification.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(Notification.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(Notification.this, Message.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(Notification.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Notification.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }

    private void yesno() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        kabul();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Notification.this);
        builder.setMessage("Kabul ediyor musunuz?").setPositiveButton("Kabul", dialogClickListener)
                .setNegativeButton("Red", dialogClickListener).show();
    }

    private void kabul() {
      /*db.collection("projects").add("procetusers", currentuserid);*/


    }

    private void getNotification() {

        db.collection("notifications").whereGreaterThanOrEqualTo("user_id", auth.getCurrentUser().getUid().toString())
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
                            NotificationModel notificaiton = doc.toObject(NotificationModel.class);
                            namelist.add(notificaiton.getProject_name() + " - " + notificaiton.getInviter_name());
                            userid_list.add(doc.getId());
                            // verileri bu liste aldık artık ekranda gösterebiliriz
                            //listUsers.add(userModel);
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Notification.this, android.R.layout.simple_list_item_1, namelist);
                        // homepage_listview.setVisibility(View.VISIBLE);
                        notification_listview.setAdapter(arrayAdapter);

                    }
                });
    }
}
