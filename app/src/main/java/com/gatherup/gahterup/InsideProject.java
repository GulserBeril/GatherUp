package com.gatherup.gahterup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InsideProject extends AppCompatActivity {
    BottomNavigationView inside_project_navigation;

    TextView inside_project_title, inside_project_projectname, inside_project_howmany, inside_project_projectdescription, inside_project_projectneeds;
    Switch inside_project_onlymanager_switch, inside_project_everymember_switch;
    CompactCalendarView compactCalendarView;
    final Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());


    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside_project);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        inside_project_navigation = findViewById(R.id.inside_project_navigation);
        inside_project_title = findViewById(R.id.inside_project_title);
        inside_project_projectname = findViewById(R.id.inside_project_projectname);
        inside_project_howmany = findViewById(R.id.inside_project_howmany);
        inside_project_projectdescription = findViewById(R.id.inside_project_projectdescription);
        inside_project_projectneeds = findViewById(R.id.inside_project_projectneeds);
        /*inside_project_onlymanager_switch = findViewById(R.id.inside_project_onlymanager_switch);
        inside_project_everymember_switch = findViewById(R.id.inside_project_everymember_switch);*/

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        DocumentReference ref = db.collection("projects").document(auth.getCurrentUser().getUid().toString());
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        //indexe göre projeleri getirmeyi yap!!!

                        /*String projectname = task.getResult().getData().get("projectname").toString();
                        String numberofparticipant = task.getResult().getData().get("numberofparticipant").toString();
                        String projectdescription = task.getResult().getData().get("projectdescription").toString();
                        String projectneeds = task.getResult().getData().get("projectneeds").toString();

                        inside_project_howmany.setText(numberofparticipant);

                        inside_project_projectdescription.setText(projectdescription);
                        inside_project_projectneeds.setText(projectneeds);

                        inside_project_title.setText(projectname);
                        inside_project_projectname.setText(projectname);*/
                        /*ArrayList<String> projectname = (ArrayList<String>) document.get("projectname");
                        ArrayList<String> numberofparticipant = (ArrayList<String>) document.get("numberofparticipant");
                        ArrayList<String> projectdescription = (ArrayList<String>) document.get("projectdescription");
                        ArrayList<String> projectneeds = (ArrayList<String>) document.get("projectneeds");

                        inside_project_projectname.setText(projectname.get(0));
                        inside_project_howmany.setText(numberofparticipant.get(0));
                        inside_project_projectdescription.setText(projectdescription.get(0));
                        inside_project_projectneeds.setText(projectneeds.get(0));*/

                    } else {
                        Toast.makeText(InsideProject.this, getApplicationContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(InsideProject.this, getApplicationContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        inside_project_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(InsideProject.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(InsideProject.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(InsideProject.this, Message.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(InsideProject.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(InsideProject.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
        final ActionBar actionBar = getSupportActionBar();
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        Event ev1 = new Event(Color.RED, 1558282278000L, "There are tasks to do.");
        compactCalendarView.addEvent(ev1);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                if (dateClicked.toString().compareTo("Tue May 21 00:00:00 GMT 2019") == 0) {
                    Toast.makeText(context, "There is a task you need to do !!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "No events Planned for that day", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });

    }
}
