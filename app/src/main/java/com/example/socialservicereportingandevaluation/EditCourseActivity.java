package com.example.socialservicereportingandevaluation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class EditCourseActivity extends AppCompatActivity {

    // creating variables for our edit text, firebase database,
    // database reference, course rv modal,progress bar.
    private TextInputEditText nameEdt, issueDescEdt, locationEdt, contactEdt, emailEdt, dateEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CourseRVModal courseRVModal;
    private ProgressBar loadingPB;
    // creating a string for our course id.
    private String issueID, subject, priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        // initializing all our variables on below line.
        Button addCourseBtn = findViewById(R.id.idBtnAddIssue);
        nameEdt = findViewById(R.id.idEdtName);
        issueDescEdt = findViewById(R.id.idEdtCourseDescription);
        locationEdt = findViewById(R.id.idEdtIssueLocation);
        contactEdt = findViewById(R.id.idEdtContact);
        emailEdt = findViewById(R.id.idEdtEmail);
        dateEdt = findViewById(R.id.idEdtDate);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        courseRVModal = getIntent().getParcelableExtra("issues");
        Button deleteCourseBtn = findViewById(R.id.idBtnDeleteCourse);

        if (courseRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            nameEdt.setText(courseRVModal.getName());
            locationEdt.setText(courseRVModal.getContact());
            contactEdt.setText(courseRVModal.getLocation());
            emailEdt.setText(courseRVModal.getDate());
            dateEdt.setText(courseRVModal.getEmail());
            issueDescEdt.setText(courseRVModal.getIssueDescription());
            issueID = courseRVModal.getIssueId();
            subject = courseRVModal.getSubject();
            priority = courseRVModal.getPriority();

        }

        // on below line we are initialing our database reference and we are adding a child as our issue id.
        databaseReference = firebaseDatabase.getReference("issues").child(issueID);
        // on below line we are adding click listener for our add course button.
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are making our progress bar as visible.
                loadingPB.setVisibility(View.VISIBLE);
                // on below line we are getting data from our edit text.
                String name = nameEdt.getText().toString();
                String courseDesc = issueDescEdt.getText().toString();
                String location = locationEdt.getText().toString();
                String contact = contactEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String date = dateEdt.getText().toString();
                // on below line we are creating a map for
                // passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("issueDescription", courseDesc);
                map.put("location", location);
                map.put("contact", contact);
                map.put("email", email);
                map.put("date", date);
                map.put("issueId", issueID);
                map.put("subject", subject);
                map.put("priority", priority);

                // on below line we are calling a database reference on
                // add value event listener and on data change method
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // making progress bar visibility as gone.
                        loadingPB.setVisibility(View.GONE);
                        // adding a map to our database.
                        databaseReference.updateChildren(map);
                        // on below line we are displaying a toast message.
                        Toast.makeText(EditCourseActivity.this, "Issue Updated..", Toast.LENGTH_SHORT).show();
                        // opening a new activity after updating our coarse.
                        startActivity(new Intent(EditCourseActivity.this, Home.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on toast.
                        Toast.makeText(EditCourseActivity.this, "Fail to update issue..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // adding a click listener for our delete course button.
        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete a course.
                deleteCourse();
            }
        });

    }

    private void deleteCourse() {
        // on below line calling a method to delete the course.
        databaseReference.removeValue();
        // displaying a toast message on below line.
        Toast.makeText(this, "Issue Deleted..", Toast.LENGTH_SHORT).show();
        // opening a main activity on below line.
        startActivity(new Intent(EditCourseActivity.this, Home.class));
    }
}
