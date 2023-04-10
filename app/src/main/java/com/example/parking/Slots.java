package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Slots extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slots);
        ActionBar actionBar = getSupportActionBar();
        Bundle bundle = getIntent().getExtras();

        // Get a reference to the Firebase Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Get a reference to the "RFID" collection
        CollectionReference rfidCollectionRef = db.collection("RFIDS");

// Retrieve all documents in the "RFID" collection
        rfidCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> slotValues = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        DocumentReference documentRef = document.getReference();
                        documentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    String slotNumber = documentSnapshot.getString("slot");
                                    String outTime = documentSnapshot.getString("OutTime");
                                    int slot = Integer.parseInt(slotNumber);
                                    ImageView slot1 = findViewById(R.id.slot1);
                                    ImageView slot2 = findViewById(R.id.slot2);
                                    ImageView slot3 = findViewById(R.id.slot3);
                                    ImageView slot4 = findViewById(R.id.slot4);
                                    if(slot == 1){
                                        slot1.setImageResource(R.drawable.car_occupied);
                                    }else if(slot ==2){
                                        slot2.setImageResource(R.drawable.car_occupied);
                                    }else if(slot ==3){
                                        slot3.setImageResource(R.drawable.car_occupied);
                                    }else if(slot ==4){
                                        slot4.setImageResource(R.drawable.car_occupied);
                                    }
                                    Log.d("slots",Integer.toString(slot));
                                } else {
                                    Log.d("TimeTAG", "No such document");
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TimeTAG", "Error getting document: " + e);
                            }
                        });
                    }
                } else {
                    // Handle errors
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
        String rfid = bundle.getString("rfid");
        System.out.println("RFIDDD"+rfid);

        CollectionReference usersRef = db.collection("RFIDS");
        DocumentReference rfidDocRef = usersRef.document(rfid);
        rfidDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String slotNumber = documentSnapshot.getString("slot");
                    TextView tv = findViewById(R.id.textView);
                    tv.setText("SLOT" + slotNumber);
                    int slot = Integer.parseInt(slotNumber);
                    ImageView slot1 = findViewById(R.id.slot1);
                    ImageView slot2 = findViewById(R.id.slot2);
                    ImageView slot3 = findViewById(R.id.slot3);
                    ImageView slot4 = findViewById(R.id.slot4);
                    if(slot == 1){
                        slot1.setImageResource(R.drawable.car_yours);
                    }else if(slot ==2){
                        slot2.setImageResource(R.drawable.car_yours);
                    }else if(slot ==3){
                        slot3.setImageResource(R.drawable.car_yours);
                    }else if(slot ==4){
                        slot4.setImageResource(R.drawable.car_yours);
                    }
                    System.out.println(slot+"BOOKED");
                    Log.d("BOOKED",slotNumber);
                } else {
                    Log.d("NewTimeTAG", "No such document");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TimeTAG", "Error getting document: " + e);
            }
        });
        assert actionBar != null;
        actionBar.setTitle("Book Slots");
        btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Billing.class);
                i.putExtra("rfid",rfid);
                startActivity(i);
            }
        });
    }
}