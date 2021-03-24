package com.example.testnewproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {

    Button btnEdit, btnSubmitedit;
    TextView viewName, viewAge, viewDob, viewFavGenre;
    EditText editName, editAge, editDoB, editFavGenre;
    private FirebaseFirestore db;

    FirebaseAuth auth;
    FirebaseUser fUser;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = FirebaseFirestore.getInstance();

        viewName = findViewById(R.id.viewName);
        viewAge = findViewById(R.id.viewAge);
        viewDob = findViewById(R.id.viewDoB);
        viewFavGenre = findViewById(R.id.viewFavGenre);

        btnEdit = findViewById(R.id.btnEdit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(i);
            }
        });

        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();
        id = fUser.getUid();




        DocumentReference docRef = db.collection("Profile").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String getName = documentSnapshot.getString("name");
                    String getAge = documentSnapshot.getString("age");
                    String getDob = documentSnapshot.getString("date_of_birth");
                    String getFavGenre = documentSnapshot.getString("favorite_genre");
                    viewName.setText("Name      : " + getName);
                    viewAge.setText("Age        : " + getAge);
                    viewDob.setText("Date of Birth  : " + getDob);
                    viewFavGenre.setText("Fav Genre : " + getFavGenre);
                }
            }
        });








    }




}
