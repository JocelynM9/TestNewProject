package com.example.testnewproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity {
    Button btnLogout;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db;

    private EditText title, desc;
    private Button saveBtn, showAllBtn;
    private String uTitle, uId, uDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        saveBtn = findViewById(R.id.saveBtn);
        showAllBtn = findViewById(R.id.showAllBtn);
        btnLogout = findViewById(R.id.btnLogout);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            saveBtn.setText("Update");
            uTitle = bundle.getString("uTitle");
            uId = bundle.getString("uId");
            uDesc = bundle.getString("uDesc");
            title.setText(uTitle);
            desc.setText(uDesc);
        }else {
            saveBtn.setText("Save");
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getTitle = title.getText().toString().trim();
                String getDesc = desc.getText().toString().trim();

                Bundle bundle1 = getIntent().getExtras();
                if(bundle1 != null){
                    String id = uId;
                    updateToFirestore(getTitle, getDesc, id);
                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFirestore(getTitle, getDesc, id);
                }

            }
        });

        showAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ShowActivity.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                finish();
                startActivity(i);
            }
        });

    }

    private void updateToFirestore(String getTitle, String getDesc, String id) {

        db.collection("Documents").document(id).update("title", getTitle, "desc", getDesc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(HomeActivity.this, "Data updated!!" , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(HomeActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveToFirestore(String getTitle, String getDesc, String id) {

        if(!getTitle.isEmpty() && !getDesc.isEmpty()){

            HashMap<String, Object> map = new HashMap<>();

            map.put("id", id);
            map.put("title", getTitle);
            map.put("desc", getDesc);

            db.collection("Documents").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(HomeActivity.this, "Data saved!!", Toast.LENGTH_SHORT).show();

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(HomeActivity.this, "Failed to insert to database!", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(this, "Empty fields not allowed!!", Toast.LENGTH_SHORT).show();
        }

    }
}
