package com.example.socialservicereportingandevaluation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Report extends AppCompatActivity {

    // creating variables for our button, edit text,
    // firebase database, database reference, progress bar.
    private Button addIssueBtn;
    private TextInputEditText NameEdt, issueDescEdt, locationEdt, contactEdt, emailEdt, dateEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private Button state;
    private ProgressBar loadingPB;
    private String issueID, issueID1, source, state1;
    String[] sub = {"Child abuse", "Gender based violence","Child labor","Child marriages"};
    String[] dist = {"Zomba", "Dedza", "Dowa", "Kasungu", "Lilongwe", "Nkhotakota", "Ntcheu",
                    "Ntchisi", "Salima", "Chitipa", "Karonga", "Likoma", "Mzimba", "Rumphi",
                    "Balaka", "Blantyre", "Chikwawa", "Chiradzulu", "Mulanje", "Machinga",
                    "Mulanje", "Mwanza", "Nsanje", "Thyolo", "Phalombe", "Mangochi", "Neno"};
    String priority = "";
    String message = "Your issue will be handled soon";
    String assign = "reporter";
    String resolvedDate = "not yet";
    String repoterMessage = " ";
    String openDate = "";
    String ta, village = "";


    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteTextView1;

    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems1;
     String dateO = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        // initializing all our variables.
        addIssueBtn = findViewById(R.id.idBtnAddIssue);
        NameEdt = findViewById(R.id.idEdtName);
        issueDescEdt = findViewById(R.id.idEdtCourseDescription);
        locationEdt = findViewById(R.id.idEdtIssueLocation);
//        contactEdt = findViewById(R.id.idEdtContact);
        emailEdt = findViewById(R.id.idEdtEmail);
        dateEdt = findViewById(R.id.idEdtDate);
        loadingPB = findViewById(R.id.idPBLoading);
        state= (Button) findViewById(R.id.pending);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        autoCompleteTextView = findViewById(R.id.idSubject);
        autoCompleteTextView1 = findViewById(R.id.idEdtContact);

        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_item,sub);
        adapterItems1 = new ArrayAdapter<String>(this,R.layout.dropdown_item,dist);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView1.setAdapter(adapterItems1);

        databaseReference = firebaseDatabase.getReference("issues");
        // adding click listener for our add course button.

//        addIssueBtn.setText("Open");

        addIssueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                // getting data from our edit text.
                String Name = NameEdt.getText().toString();
                String issueDesc = issueDescEdt.getText().toString();
                String location = locationEdt.getText().toString();
//                String contact = contactEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String date = dateEdt.getText().toString();
                String subject = autoCompleteTextView.getText().toString();
                String contact = autoCompleteTextView1.getText().toString();
//                String priority = autoCompleteTextView1.getText().toString();
                String cdate = new Date().toString();
                source = "Mobile app";
                state1 = "pending";

                DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
                mDatabaseRef.child("issues").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int size = (int) dataSnapshot.getChildrenCount();
                        issueID = ""+size;
                        IssueRVModal issueRVModal = new IssueRVModal(issueID, Name, issueDesc, location,
                                contact, email, date, subject, priority, source, state1,
                                message, dateO, resolvedDate, repoterMessage, assign, ta, village);
                        // on below line we are calling a add value event
                        // to pass data to firebase database.
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // on below line we are setting data in our firebase database.
                                databaseReference.child(issueID).setValue(issueRVModal);
                                // displaying a toast message.
                                Toast.makeText(Report.this, "Issue Added..", Toast.LENGTH_SHORT).show();
                                // starting a main activity.
                                startActivity(new Intent(Report.this, Home.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // displaying a failure message on below line.
                                Toast.makeText(Report.this, "Fail to add Issue..", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // on below line we are passing all data to our modal class.


            }
        });
    }
}