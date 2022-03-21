package com.example.socialservicereportingandevaluation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements IssueRVAdapter.CourseClickInterface {

    private FloatingActionButton addIssueFAB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView issueRV;
    private ProgressBar loadingPB;
    private ArrayList<IssueRVModal> issueRVModalArrayList;
    private IssueRVAdapter issueRVAdapter;
    private RelativeLayout homeRL;
    private TextView noIssue, noInternet;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // initializing all our variables.
        issueRV = findViewById(R.id.idRVIssues);
//        noIssue = (TextView) findViewById(R.id.idNoIssue);
//        noInternet = (TextView) findViewById(R.id.idNoInternet);
        btn = (Button) findViewById(R.id.pending);
        homeRL = findViewById(R.id.idRLBSheet);
        loadingPB = findViewById(R.id.idPBLoading);
        addIssueFAB = findViewById(R.id.idFABAddCourse);
        firebaseDatabase = FirebaseDatabase.getInstance();
        issueRVModalArrayList = new ArrayList<>();
        // on below line we are getting database reference.
//        databaseReference = firebaseDatabase.getReference("issues");
        databaseReference = firebaseDatabase.getReference("issues");
//                btn.setText("Open");
//        loadingPB.setVisibility(View.GONE);
//        noIssue.setVisibility(View.GONE);
//        noInternet.setVisibility(View.GONE);

//        Runnable runnable = new Runnable() {
//            public void run () {
//                // Do your stuff here  -- show your progress bar
//                loadingPB.setVisibility(View.GONE);
//                noIssue.setVisibility(View.VISIBLE);
//                noInternet.setVisibility(View.VISIBLE);
//            }
//        };
//        Handler handler = new Handler();
//        handler.postDelayed(runnable, 10000);

//        Query query = databaseReference.orderByChild("source").equalTo("Web App");
        // on below line adding a click listener for our floating action button.
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
//        mDatabaseRef.child("issues").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                int size = (int) dataSnapshot.getChildrenCount();
//                Toast.makeText(Home.this, "You have "+size+" Issues", Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        addIssueFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opening a new activity for adding a course.
                Intent i = new Intent(Home.this, Report.class);
                startActivity(i);
            }
        });
        // on below line initializing our adapter class.
        issueRVAdapter = new IssueRVAdapter(issueRVModalArrayList, this, this::onCourseClick);
        // setting layout malinger to recycler view on below line.
        issueRV.setLayoutManager(new LinearLayoutManager(this));
        // setting adapter to recycler view on below line.
        issueRV.setAdapter(issueRVAdapter);
        // on below line calling a method to fetch courses from database.
        getCourses();
    }

    private void getCourses() {
        // on below line clearing our list.
        issueRVModalArrayList.clear();


        // on below line we are calling add child event listener method to read the data.
        Query query = databaseReference.orderByChild("source").endAt("Mobile app");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // on below line we are hiding our progress bar.
                loadingPB.setVisibility(View.GONE);
//                noIssue.setVisibility(View.GONE);
                // adding snapshot to our array list on below line.
                issueRVModalArrayList.add(snapshot.getValue(IssueRVModal.class));
                // notifying our adapter that data has changed.
                issueRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // this method is called when new child is added
                // we are notifying our adapter and making progress bar
                // visibility as gone.
                loadingPB.setVisibility(View.GONE);
                issueRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // notifying our adapter when child is removed.
                issueRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // notifying our adapter when child is moved.
                issueRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCourseClick(int position) {
        // calling a method to display a bottom sheet on below line.
        displayBottomSheet(issueRVModalArrayList.get(position));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // adding a click listener for option selected on below line.
        int id = item.getItemId();
        switch (id) {
            case R.id.idLogOut:
                // displaying a toast message on user logged out inside on click.
                Toast.makeText(getApplicationContext(), "User Logged Out", Toast.LENGTH_LONG).show();
                // on below line we are signing out our user.
//                mAuth.signOut();
                // on below line we are opening our login activity.
                Intent i = new Intent(Home.this, MainActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // on below line we are inflating our menu
        // file for displaying our menu options.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void displayBottomSheet(IssueRVModal modal) {
        // on below line we are creating our bottom sheet dialog.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, homeRL);
        bottomSheetTeachersDialog.setContentView(layout);
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        // calling a method to display our bottom sheet.
        bottomSheetTeachersDialog.show();
        TextView nameTV = layout.findViewById(R.id.idTVCourseName);
        TextView courseDescTV = layout.findViewById(R.id.idTVCourseDesc);
        TextView suitedForTV = layout.findViewById(R.id.idTVSuitedFor);
        TextView priceTV = layout.findViewById(R.id.idTVCoursePrice);
        ImageView courseIV = layout.findViewById(R.id.idIVIssue);
        // on below line we are setting data to different views on below line.
        nameTV.setText("Name: "+modal.getName());
        courseDescTV.setText(modal.getIssueDescription());
        suitedForTV.setText("Location: " + modal.getLocation());
        priceTV.setText("Contact: " + modal.getContact());
//        Picasso.get().load(modal.getCourseImg()).into(courseIV);
        Button viewBtn = layout.findViewById(R.id.idBtnVIewDetails);
        Button editBtn = layout.findViewById(R.id.idBtnEditCourse);

        // adding on click listener for our edit button.
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are opening our EditCourseActivity on below line.
                Intent i = new Intent(Home.this, EditIssueActivity.class);
                // on below line we are passing our course modal
                i.putExtra("issues", modal);
                startActivity(i);
            }
        });
        // adding click listener for our view button on below line.
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are navigating to browser
                // for displaying course details from its url
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(modal.getEmail()));
//                startActivity(i);
//                Toast.makeText(Home.this, "All issues Issue..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Home.this, IssueDetails.class);
                intent.putExtra("issues", modal);
                startActivity(intent);

            }
        });
    }
}