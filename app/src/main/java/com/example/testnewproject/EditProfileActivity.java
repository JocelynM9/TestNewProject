package com.example.testnewproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    Button submitEditbtn;
    EditText editName, editAge, editDoB, editFavGenre;
    private FirebaseFirestore db;

    FirebaseAuth auth;
    FirebaseUser fUser;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editDoB = findViewById(R.id.editDoB);
        editFavGenre = findViewById(R.id.editFavGenre);
        submitEditbtn = findViewById(R.id.btnSubmitEdit);

        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();
        id = fUser.getUid();

        db = FirebaseFirestore.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db.collection("Profile").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                editName.setText(documentSnapshot.getString("name"));
                editAge.setText(documentSnapshot.getString("age"));
                editDoB.setText(documentSnapshot.getString("date_of_birth"));
                editFavGenre.setText(documentSnapshot.getString("favorite_genre"));

            }
        });

        submitEditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String age = editAge.getText().toString().trim();
                String dob = editDoB.getText().toString().trim();
                String favGenre = editFavGenre.getText().toString().trim();


                saveToFirestore(id, name, age, dob, favGenre);
            }
        });


    }


    private void saveToFirestore(String id, String name, String age, String dob, String favGenre){

        if(!name.isEmpty() || !age.isEmpty() || !dob.isEmpty() || !favGenre.isEmpty()){

            HashMap<String, Object> editProfile = new HashMap<>();
            editProfile.put("id", id);
            editProfile.put("name", name);
            editProfile.put("age", age);
            editProfile.put("date_of_birth", dob);
            editProfile.put("favorite_genre", favGenre);

            db.collection("Profile").document(id).set(editProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(EditProfileActivity.this, "Success insert !", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    startActivity(i);
                    finish();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfileActivity.this, "Failed insert!", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(EditProfileActivity.this, "Empty fields not allowed!!!", Toast.LENGTH_SHORT).show();
        }

    }

}
