package com.gatherup.gahterup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gatherup.gahterup.Fragments.Users;
import com.gatherup.gahterup.Model.ProjectModel;
import com.gatherup.gahterup.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Edit extends AppCompatActivity {

    BottomNavigationView profile_edit_navigation;
    Button profile_edit_back, profile_edit_addmore_abilities, profile_edit_addmore_projects, profile_edit_save;
    CircleImageView profile_edit_profilepicture;
    EditText profile_edit_name, profile_edit_surname, profile_edit_email, profile_edit_birthdate, profile_edit_universityname, profile_edit_entranceyear, profile_edit_year, profile_edit_duty, profile_edit_position, profile_edit_projectname, profile_edit_description, profile_edit_combo;
    //TextView profile_edit_abilities_list;
    ListView profile_edit_abilities_list;
    List<String> abilities_list;

    UserModel userModel;


    FirebaseAuth auth;
    FirebaseFirestore db;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        profile_edit_navigation = findViewById(R.id.profile_edit_navigation);
        profile_edit_combo = findViewById(R.id.profile_edit_combo);
        profile_edit_back = findViewById(R.id.profile_edit_back);
        profile_edit_addmore_abilities = findViewById(R.id.profile_edit_addmore_abilities);
        profile_edit_addmore_projects = findViewById(R.id.profile_edit_addmore_projects);
        profile_edit_save = findViewById(R.id.profile_edit_save);
        profile_edit_profilepicture = findViewById(R.id.profile_edit_profilepicture);
        profile_edit_name = findViewById(R.id.profile_edit_name);
        profile_edit_surname = findViewById(R.id.profile_edit_surname);
        profile_edit_email = findViewById(R.id.profile_edit_email);
        profile_edit_birthdate = findViewById(R.id.profile_edit_birthdate);
        profile_edit_universityname = findViewById(R.id.profile_edit_universityname);
        profile_edit_entranceyear = findViewById(R.id.profile_edit_entranceyear);
        profile_edit_year = findViewById(R.id.profile_edit_year);
        profile_edit_duty = findViewById(R.id.profile_edit_duty);
        profile_edit_position = findViewById(R.id.profile_edit_position);
        profile_edit_projectname = findViewById(R.id.profile_edit_projectname);
        profile_edit_description = findViewById(R.id.profile_edit_description);
        profile_edit_abilities_list = findViewById(R.id.profile_edit_abilities_list);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();


        String pp = auth.getCurrentUser().getUid().toString();
        StorageReference storageReference1 = storageReference.child(pp);

        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Profile_Edit.this).load(uri).into(profile_edit_profilepicture);
            }
        });

        //database'den verileri çekmek için
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
                        profile_edit_name.setText(name);
                        profile_edit_surname.setText(surname);
                        profile_edit_email.setText(email);
                        profile_edit_birthdate.setText(birthdate);
                        profile_edit_universityname.setText(universityname);
                        profile_edit_entranceyear.setText(entranceyear);
                        profile_edit_year.setText(year);
                        profile_edit_duty.setText(duty);
                        profile_edit_position.setText(position);
                        profile_edit_projectname.setText(projectname);
                        profile_edit_description.setText(description);



                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Profile_Edit.this, android.R.layout.simple_list_item_1, abilities_list);
                        profile_edit_abilities_list.setVisibility(View.VISIBLE);
                        profile_edit_abilities_list.setAdapter(arrayAdapter);


                        /*for (int i = 0; i < abilities_list.size(); i++) {
                            profile_edit_abilities_list.setText(profile_edit_abilities_list.getText() + "\n" + abilities_list.get(i) + "\n");
                        }*/
                    } else {
                        Toast.makeText(Profile_Edit.this, getApplicationContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Profile_Edit.this, getApplicationContext().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                }
            }
        });

        profile_edit_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_homepage:
                        startActivity(new Intent(Profile_Edit.this, HomePage.class));
                        break;
                    case R.id.navigation_projects:
                        startActivity(new Intent(Profile_Edit.this, Project.class));
                        break;
                    case R.id.navigation_messages:
                        startActivity(new Intent(Profile_Edit.this, Message.class));
                        break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(Profile_Edit.this, Notification.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Profile_Edit.this, Profile.class));
                        break;
                }
                finish();
                return true;
            }
        });
    }

    public void profile_edit_back_click(View view) {
        super.onBackPressed();
    }

    public void profile_edit_save_click(View view) {
        //butona basıldığında verileri update etmek için
        final String name = profile_edit_name.getText().toString();
        final String surname = profile_edit_surname.getText().toString();
        final String email = profile_edit_email.getText().toString();
        final String birthdate = profile_edit_birthdate.getText().toString();
        final String universityname = profile_edit_universityname.getText().toString();
        final String entranceyear = profile_edit_entranceyear.getText().toString();
        // final String abilities = profile_edit_abilities_list.getText().toString();
        final String year = profile_edit_year.getText().toString();
        final String duty = profile_edit_duty.getText().toString();
        final String position = profile_edit_position.getText().toString();
        final String projectname = profile_edit_projectname.getText().toString();
        final String projectdescription = profile_edit_description.getText().toString();

        db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("name", name);
        db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("surname", surname);
        db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("email", email);
        db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("birthdate", birthdate);
        db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("universityname", universityname);
        db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("entranceyear", entranceyear);
        //db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("abilities", abilitieslist);
        db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("year", year);
        db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("duty", duty);
        db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("position", position);
        db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("projectname", projectname);
        db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("projectdescription", projectdescription);

        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }


    public void profile_edit_pp_click(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            String ppname = auth.getCurrentUser().getUid().toString();
            StorageReference storageReference1 = storageReference.child(ppname);
            storageReference1.putFile(uri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                profile_edit_profilepicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void profile_edit_abilities_click(View view) {

        abilities_list = new ArrayList<String>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Profile_Edit.this, android.R.layout.simple_list_item_1, abilities_list);

        String abilities = profile_edit_combo.getText().toString();
        abilities_list.add(abilities);
        profile_edit_combo.clearFocus();
        profile_edit_abilities_list.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();


        db.collection("users").document(auth.getCurrentUser().getUid().toString()).update("abilities", FieldValue.arrayUnion(abilities));


        /*
        DocumentReference reflist = db.collection("users").document(auth.getCurrentUser().getUid().toString());
        reflist.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<String> abilities_list = new ArrayList<String>();
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                    }
                }
            }
        });

        abilitieslist.add(abilities);
        */
    }
}
