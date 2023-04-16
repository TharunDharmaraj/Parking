package com.example.parking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Billing extends AppCompatActivity {

    Button btn;
    String inTime, outTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Your Bill");
        btn = findViewById(R.id.button2);
        Bundle bundle = getIntent().getExtras();
        String rfid = bundle.getString("rfid");
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        TextView innTime = findViewById(R.id.inTime);
        TextView outtTime = findViewById(R.id.outTime);
        TextView hoursSpent = findViewById(R.id.hoursSpent);
        CollectionReference usersRef = firestore.collection("RFIDS");

        DocumentReference rfidDocRef = usersRef.document(rfid);

        rfidDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    if(rfid.equals("RFID1")){
                        inTime = bundle.getString("InTime");
                        outTime = bundle.getString("OutTime");
                        innTime.setText(inTime);
                        outtTime.setText(outTime);
                    }
                    else{
                        String inTime = documentSnapshot.getString("InTime");
                        String outTime = documentSnapshot.getString("OutTime");
                        innTime.setText(inTime);
                        outtTime.setText(outTime);
                    }

                    // Parse the inTime and outTime strings into Date objects
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd MM yyyy");

                    Date inTimeDate = null;
                    try {
                        inTimeDate = dateFormat.parse(inTime);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    Date outTimeDate = null;
                    try {
                        outTimeDate = dateFormat.parse(outTime);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    long timeInside = outTimeDate.getTime() - inTimeDate.getTime();
                    double billPerHour = 05.0; // change this to the rate you want to charge per minute
                    double totalBill = (timeInside / 60000.0) * billPerHour; // convert milliseconds to minutes
                    TextView Bill = findViewById(R.id.yourBill);
                    long hours = (long) timeInside / 3600000;
                    hoursSpent.setText(Long.toString(hours)+" Hours spent");
                    Bill.setText(String.valueOf(totalBill));
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
        Log.d("TimeTAG", "Document reference: " + rfidDocRef.getPath());
        Log.d("TimeTAG", "InTime TextView found: " + (innTime != null));
        Log.d("TimeTAG", "OutTime TextView found: " + (outtTime != null));




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Billing Finished", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
