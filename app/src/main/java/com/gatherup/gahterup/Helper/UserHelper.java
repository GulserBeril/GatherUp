package com.gatherup.gahterup.Helper;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.gatherup.gahterup.Model.UserModel;
import com.gatherup.gahterup.Profile_Edit;
import com.gatherup.gahterup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UserHelper {

    FirebaseAuth auth;
    FirebaseFirestore db;
    StorageReference storageReference;

    public UserHelper() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public UserModel getUser() {
        final UserModel userModel = new UserModel();
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

                    }
                }
            }
        });
        return userModel;
    }
}
