package com.example.socialservicereportingandevaluation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Report extends AppCompatActivity {
    EditText date;
    DatePickerDialog datePickerDialog;

    // creating variables for our button, edit text,
    // firebase database, database reference, progress bar.
    private Button addIssueBtn;
    private TextInputEditText NameEdt, issueDescEdt, locationEdt, contactEdt, emailEdt, dateEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private Button state;
    private ProgressBar loadingPB;
    private String issueID, issueID1, source;
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
    String state1 = "pending";


    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteTextView1;
    AutoCompleteTextView autoCompleteTextViewDate;

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
        dateEdt = findViewById(R.id.idEdtDate);
        loadingPB = findViewById(R.id.idPBLoading);
        state= (Button) findViewById(R.id.pending);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        autoCompleteTextView = findViewById(R.id.idSubject);
        autoCompleteTextView1 = findViewById(R.id.idEdtContact);
        autoCompleteTextViewDate = findViewById(R.id.idEdtEmail);


        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_item,sub);
        adapterItems1 = new ArrayAdapter<String>(this,R.layout.dropdown_item,dist);

        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView1.setAdapter(adapterItems1);

        databaseReference = firebaseDatabase.getReference("issues");
        // adding click listener for our add course button.

//        addIssueBtn.setText("Open");

        autoCompleteTextViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Report.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                autoCompleteTextViewDate.setText((dayOfMonth  + 1) + "/"
                                        +  monthOfYear + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        addIssueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                // getting data from our edit text.
                String Name = NameEdt.getText().toString();
                String issueDesc = issueDescEdt.getText().toString();
                String location = locationEdt.getText().toString();
//                String contact = contactEdt.getText().toString();
                String email = autoCompleteTextViewDate.getText().toString();
                String date = dateEdt.getText().toString();
                String subject = autoCompleteTextView.getText().toString();
                String contact = autoCompleteTextView1.getText().toString();
//                String priority = autoCompleteTextView1.getText().toString();
                String cdate = new Date().toString();
                source = "Mobile app";
                createNotificationChannel();

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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("id", "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent snoozeIntent = new Intent(this, IssueDetails.class);
        snoozeIntent.setAction("ACTION_SNOOZE");
        snoozeIntent.putExtra("EXTRA_NOTIFICATION_ID", 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                    .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                    .setContentTitle("Issue has been reported")
                    .setContentText("You have reported an issue to YONECO and it will be handle soon ")
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("You have reported an issue to YONECO and it will be handle soon."))
                    .addAction(R.drawable.ic_baseline_notifications_24, "SNOOZE", snoozePendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());




        }

}