package com.gatherup.gahterup;
import android.content.Intent;
import android.provider.Telephony;
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
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity implements View.OnTouchListener, AdapterView.OnItemClickListener {
    EditText profile_combo;
    ListPopupWindow profile_lpw;
    String[] profile_list;
    BottomNavigationView profile_navigation;
    Button profile_back, profile_addmore_abilities, profile_addmore_projects, profile_save;
    ImageView profile_profilepicture;
    EditText profile_name, profile_surname, profile_email, profile_birthdate, profile_universityname, profile_entranceyear, profile_year, profile_duty, profile_position, profile_projectname, profile_description;

    FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        profile_navigation = findViewById(R.id.profile_navigation);
        profile_combo = findViewById(R.id.profile_combo);
        profile_back = findViewById(R.id.profile_back);
        profile_addmore_abilities = findViewById(R.id.profile_addmore_abilities);
        profile_addmore_projects = findViewById(R.id.profile_addmore_projects);
        profile_save = findViewById(R.id.profile_save);
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

        auth = FirebaseAuth.getInstance();

        profile_combo.setOnTouchListener(this);

        profile_list = new String[]{"Java", "Python", "Php", "SQL"};
        profile_lpw = new ListPopupWindow(this);
        profile_lpw.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, profile_list));
        profile_lpw.setAnchorView(profile_combo);
        profile_lpw.setModal(true);
        profile_lpw.setOnItemClickListener(this);

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
                        startActivity(new Intent(Profile.this, MessageInbox.class));
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = profile_list[position];
        profile_combo.setText(item);
        profile_lpw.dismiss();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() >= (v.getWidth() - ((EditText) v)
                    .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                profile_lpw.show();
                return true;
            }
        }
        return false;
    }
    public  void  profile_back_click(View view){
        super.onBackPressed();
    }

    public void profile_save_click(View view) {

        final String name = profile_name.getText().toString();
        final String surname = profile_surname.getText().toString();
        final String email = profile_email.getText().toString();
        final String birthdate =profile_birthdate.getText().toString();
        final String universityname = profile_universityname.getText().toString();
        final String enteranceyear = profile_entranceyear.getText().toString();
        final String workyear=  profile_year.getText().toString();
        final String duty = profile_duty.getText().toString();
        final String position = profile_position.getText().toString();
        final String projectName = profile_projectname.getText().toString();
        final String projectDescription = profile_description.getText().toString();



            db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("birthdate",birthdate);

            db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("universityName", universityname);

            Intent intent = new Intent(this,Project.class);
            startActivity(intent);



    }
}
