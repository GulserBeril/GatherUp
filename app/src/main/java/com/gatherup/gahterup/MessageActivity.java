package com.gatherup.gahterup;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gatherup.gahterup.Model.NotificationModel;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore db;
    private FirebaseStorage storageReference;

    CircleImageView msg_profile_image;
    TextView msg_username;
    // ImageButton msg_btn_send;
    //  EditText msg_txt_send;

    RecyclerView recyclerView;
    String useremail;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance();

        Toolbar toolbar = findViewById(R.id.msg_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(MessageActivity.this, Message.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        recyclerView = findViewById(R.id.msg_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        msg_profile_image = findViewById(R.id.msg_profile_image);
        msg_username = findViewById(R.id.msg_username);
        //  msg_btn_send = findViewById(R.id.msg_btn_send);
        // msg_txt_send = findViewById(R.id.msg_txt_send);

        intent = getIntent();
        useremail = intent.getStringExtra("useremail");

        db.collection("users").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {


                        for (DocumentSnapshot doc : snapshots) {
                            UserModel userModel = doc.toObject(UserModel.class);

                            if (userModel.getEmail() != null && userModel.getEmail().equals(useremail)) {
                                msg_username.setText(userModel.getName() + " " + userModel.getSurname());
                            }
                        }

                    }
                });

        String pp = auth.getCurrentUser().getUid().toString();
        StorageReference storageReference1 = storageReference.getReference().child(pp);

        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MessageActivity.this).load(uri).into(msg_profile_image);
            }
        });

        /*ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            String username, profile_image;

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                username = documentSnapshot.getString("name");
                if (username != auth.getCurrentUser().getUid().toString()) {
                    msg_username.setText(username);
                    msg_username.setEnabled(true);
                }
            }
        });*/
    }
}
