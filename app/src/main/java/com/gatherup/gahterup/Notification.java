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
import android.widget.Toast;

import com.gatherup.gahterup.Enums.Enums;
import com.gatherup.gahterup.Helper.ErrorHelper;
import com.gatherup.gahterup.Helper.UserHelper;
import com.gatherup.gahterup.Model.NotificationModel;
import com.gatherup.gahterup.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Notification extends AppCompatActivity {
    BottomNavigationView notification_navigation;
    ListView notification_listview;
    List<NotificationModel> notificationList;
    String currentuserid;
    UserModel userModel;
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
        UserHelper userHelper = new UserHelper();
        userModel = userHelper.getUser();
        notification_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                yesno(notificationList.get(position));
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

    private void yesno(final NotificationModel notificationModel) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        kabul(notificationModel);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        red(notificationModel);
                        break;
                }
            }
        };

        String notification_type = notificationModel.getNotification_type();
        String message;
        if (notification_type.equals("invite")) {
            message = "Teklifi kabul ediyor musunuz?";
        } else {
            message = "Katılımı kabul ediyor musunuz?";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Notification.this);
        builder.setMessage(message).setPositiveButton("Kabul", dialogClickListener)
                .setNegativeButton("Red", dialogClickListener).show();
    }

    private void red(NotificationModel notificationModel) {
        String notificationid = notificationModel.getNotificationid();
        db.collection(Enums.FirebaseTables.notifications.toString()).document(notificationid).update("state", true);
        Toast.makeText(this, "Projeyi red ettiniz", Toast.LENGTH_SHORT).show();

    }

    private void kabul(NotificationModel notificationModel) {

        try {
            String projectid = notificationModel.getProject_id();
            String notificationid = notificationModel.getNotificationid();
            String personel = userModel.getName() + " " + userModel.getSurname();
            String notification_type = notificationModel.getNotification_type();
            String inviter = notificationModel.getInviter_name();

            if (notification_type.equals("invite")) {
                db.collection(Enums.FirebaseTables.projects.toString()).document(projectid).update("projectusers", FieldValue.arrayUnion(personel));
            } else {
                db.collection(Enums.FirebaseTables.projects.toString()).document(projectid).update("projectusers", FieldValue.arrayUnion(inviter));
            }


            db.collection(Enums.FirebaseTables.notifications.toString()).document(notificationid).update("state", true);
            getNotification();

            if (notification_type.equals("invite")) {
                Toast.makeText(this, "Projeyi kabul ettiniz", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, inviter +"\n Katılımı kabul ettiniz", Toast.LENGTH_SHORT).show();

            }
            /*db.collection("projects").add("procetusers", currentuserid);*/
        } catch (Exception e) {
            ErrorHelper.saveError(Notification.this, e);
        }


    }

    private void getNotification() {

        db.collection("notifications").whereEqualTo("user_id", auth.getCurrentUser().getUid().toString())
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
                        notificationList = new ArrayList<>();
                        for (DocumentSnapshot doc : snapshots) {
                            NotificationModel notificaiton = doc.toObject(NotificationModel.class);
                            notificaiton.setNotificationid(doc.getId());
                            if (notificaiton.getState() == false) {
                                namelist.add(notificaiton.getProject_name() + " - " + notificaiton.getInviter_name());
                                notificationList.add(notificaiton);
                            }
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
