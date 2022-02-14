package com.example.socialservicereportingandevaluation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IssueDetails extends AppCompatActivity {
    // creating variables for our edit text, firebase database,
    // database reference, course rv modal,progress bar.
    TextView name, issueDesc, location, contact, email, date, subject, priority, source;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CourseRVModal courseRVModal;
    private ProgressBar loadingPB;
    // creating a string for our course id.
    String issueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_details);

        Button addCourseBtn = findViewById(R.id.idBtnAddIssue);
        name = findViewById(R.id.idTVName);
        issueDesc = (TextView)findViewById(R.id.idTVIssueDesc);
        location = findViewById(R.id.idTVLocation);
        contact = findViewById(R.id.idTVContact);
        email = findViewById(R.id.idTVEmail);
        date = findViewById(R.id.idTVDate);
        loadingPB = findViewById(R.id.idPBLoading);
        subject = findViewById(R.id.idTVSubject);
        priority = findViewById(R.id.idTVPriority);
        source = findViewById(R.id.idTVSource);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        courseRVModal = getIntent().getParcelableExtra("issues");
        Button deleteCourseBtn = findViewById(R.id.idBtnDeleteCourse);

        if (courseRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            name.setText("Name: "+courseRVModal.getName());
            location.setText("Location: "+courseRVModal.getLocation());
            contact.setText("Contact: "+courseRVModal.getContact());
            email.setText("Email: "+courseRVModal.getEmail());
            date.setText("Date: "+courseRVModal.getDate());
            issueDesc.setText("Issue Description: "+courseRVModal.getIssueDescription());
            subject.setText("Subject: "+courseRVModal.getSubject());
            priority.setText("Priority: "+courseRVModal.getPriority());
            source.setText("Source: "+courseRVModal.getSource());
            issueID = courseRVModal.getUid();

        }
    }
}