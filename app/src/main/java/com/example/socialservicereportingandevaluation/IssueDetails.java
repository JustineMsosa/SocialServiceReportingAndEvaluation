package com.example.socialservicereportingandevaluation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class IssueDetails extends AppCompatActivity {
    // creating variables for our edit text, firebase database,
    // database reference, course rv modal,progress bar.
    private Button replyIssueBtn;
    private TextInputEditText ReplyEdt;
    TextView name, issueDesc, location, contact, email, date, subject, priority, source, message;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextInputEditText Reply;
    IssueRVModal issueRVModal;
    private ProgressBar loadingPB;
    // creating a string for our course id.
    private String issueID, subject1, priority1, source1, state1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_details);
        Button addIssueBtn = findViewById(R.id.idBtnAddIssue);
        ReplyEdt = findViewById(R.id.idEdtReply);
        Button addCourseBtn = findViewById(R.id.idBtnAddIssue);
        Reply  = findViewById(R.id.idEdtReply);
        Button replyIssueBtn = findViewById(R.id.idBtnReplyIssue);
        name = findViewById(R.id.idTVName);
        issueDesc = (TextView)findViewById(R.id.idTVIssueDesc);
        location = findViewById(R.id.idTVLocation);
        contact = findViewById(R.id.idTVContact);
        email = findViewById(R.id.idTVEmail);
        date = findViewById(R.id.idTVDate);
        loadingPB = findViewById(R.id.idPBLoading);
        subject = findViewById(R.id.idTVSubject);
        priority = findViewById(R.id.idTVPriority);
        message = findViewById(R.id.idTVMessage);
        source = findViewById(R.id.idTVSource);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        issueRVModal = getIntent().getParcelableExtra("issues");
        Button deleteCourseBtn = findViewById(R.id.idBtnDeleteCourse);

        if (issueRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            name.setText("Name: "+ issueRVModal.getName());
            location.setText("Location: "+ issueRVModal.getLocation());
            contact.setText("Contact: "+ issueRVModal.getContact());
            email.setText("Email: "+ issueRVModal.getEmail());
            date.setText("Date: "+ issueRVModal.getDate());
            issueDesc.setText("Issue Description: "+ issueRVModal.getIssueDescription());
            subject.setText("Subject: "+ issueRVModal.getSubject());
            priority.setText("Priority: "+ issueRVModal.getPriority());
            source.setText("Source: "+ issueRVModal.getSource());
            message.setText("Remark: "+ issueRVModal.getRepoterMessage());
            issueID = issueRVModal.getUid();
//             issueDesc1 = issueRVModal.getIssueDescription();
//             location1 = issueRVModal.getLocation();
//             contact1 = issueRVModal.getContact();
//             email1 = issueRVModal.getEmail();
//             date1 = issueRVModal.getDate();
//             state1 = issueRVModal.getState1();


        }

        // on below line we are initialing our database reference and we are adding a child as our issue id.
        databaseReference = firebaseDatabase.getReference("issues").child(issueID);
        // on below line we are adding click listener for our edit issue button.
        replyIssueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are making our progress bar as visible.
//                loadingPB.setVisibility(View.VISIBLE);
                // on below line we are getting data from our edit text.
                String name = Reply.getText().toString();

                // on below line we are creating a map for
                // passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("issueDescription", issueDesc);
                map.put("location", location);
                map.put("contact", contact);
                map.put("email", email);
                map.put("date", date);
                map.put("issueId", issueID);
                map.put("subject", subject);
                map.put("source", source);
                map.put("priority", priority);
                map.put("state1", state1);

                // on below line we are calling a database reference on
                // add value event listener and on data change method
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // making progress bar visibility as gone.
//                        loadingPB.setVisibility(View.GONE);
                        // adding a map to our database.
//                        databaseReference.updateChildren(map);
                        // on below line we are displaying a toast message.
                        Toast.makeText(IssueDetails.this, "Issue Updated..", Toast.LENGTH_SHORT).show();
                        // opening a new activity after updating our issue.

                        startActivity(new Intent(IssueDetails.this, Home.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on toast.
                        Toast.makeText(IssueDetails.this, "Fail to update issue..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}