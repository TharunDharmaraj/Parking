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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Slots extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slots);
        ActionBar actionBar = getSupportActionBar();
        Bundle bundle = getIntent().getExtras();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String rfid = bundle.getString("rfid");
        CollectionReference usersRef = firestore.collection("RFIDS");
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