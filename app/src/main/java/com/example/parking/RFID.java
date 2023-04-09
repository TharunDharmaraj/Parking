package com.example.parking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RFID extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    ArrayList<String> rfid1 = new ArrayList<String>();
    Button btn ;
    String selected = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("RFID");
        btn = findViewById(R.id.button2);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        // Obtain a reference to the "RFID" collection
        CollectionReference rfidCollectionRef = firestore.collection("RFIDS");
        // Query all documents in the "RFID" collection
        rfidCollectionRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Loop through all documents and append their names to "rfid1"
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String documentName = documentSnapshot.getId();
                    rfid1.add(documentName);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TimeTAG", "Error getting document: " + e);
            }
        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,rfid1);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int spinnerPosition = aa.getPosition("RFID1");
        spin.setSelection(spinnerPosition);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Slots.class);
                i.putExtra("rfid",selected);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        selected = rfid1.get(position);
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}