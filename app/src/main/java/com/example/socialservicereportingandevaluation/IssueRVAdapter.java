package com.example.socialservicereportingandevaluation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IssueRVAdapter extends RecyclerView.Adapter<IssueRVAdapter.ViewHolder> {
    // creating variables for our list, context, interface and position.
    private ArrayList<IssueRVModal> issueRVModalArrayList;
    private Context context;
    private CourseClickInterface courseClickInterface;
    int lastPos = -1;

    // creating a constructor.
    public IssueRVAdapter(ArrayList<IssueRVModal> issueRVModalArrayList, Context context,
                          CourseClickInterface courseClickInterface) {
        this.issueRVModalArrayList = issueRVModalArrayList;
        this.context = context;
        this.courseClickInterface = courseClickInterface;
    }

    @NonNull
    @Override
    public IssueRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.issue_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueRVAdapter.ViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        // setting data to our recycler view item on below line.
        IssueRVModal issueRVModal = issueRVModalArrayList.get(position);
        holder.courseTV.setText(issueRVModal.getName());
        holder.subjectIV.setText(issueRVModal.getSubject());
        holder.coursePriceTV.setText(issueRVModal.getContact());
        holder.state.setText(issueRVModal.getState1());
//        if (courseRVModal.getPriority()== null){
//            holder.state.setText(courseRVModal.getState1());
//        }
//        if (courseRVModal.getAssign()== null){
//            holder.state.setText(courseRVModal.getAssign());
//        }
//        if (courseRVModal.getMessage()== null){
//            holder.state.setText(courseRVModal.getMessage());
//        }
//        if (courseRVModal.getOpenDate()== null){
//            holder.state.setText(courseRVModal.getOpenDate());
//        }
//        if (courseRVModal.getRepoterMessage()== null){
//            holder.state.setText(courseRVModal.getRepoterMessage());
//        }
//        if (courseRVModal.getResolvedDate()== null){
//            holder.state.setText(courseRVModal.getResolvedDate());
//        }


//        Picasso.get().load(courseRVModal.getCourseImg()).into(holder.courseIV);
        // adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position);
        holder.subjectIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseClickInterface.onCourseClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        int k = 0;
        if (position > lastPos) {
            k = 1 + k;
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return issueRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.
        private Button state;
        private TextView courseTV, coursePriceTV, subjectIV;
        String state1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line.
            subjectIV = itemView.findViewById(R.id.idIVSubject);
            courseTV = itemView.findViewById(R.id.idTVname);
            coursePriceTV = itemView.findViewById(R.id.idTVCousePrice);
            state = itemView.findViewById(R.id.pending);
//            state1 = CourseRVModal.class.getName();


//            state.setText(state1);

        }
    }

    // creating a interface for on click
    public interface CourseClickInterface {
        void onCourseClick(int position);
    }
}
