package com.example.socialservicereportingandevaluation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {
    // creating variables for our list, context, interface and position.
    private ArrayList<CourseRVModal> courseRVModalArrayList;
    private Context context;
    private CourseClickInterface courseClickInterface;
    int lastPos = -1;

    // creating a constructor.
    public CourseRVAdapter(ArrayList<CourseRVModal> courseRVModalArrayList, Context context,
                           CourseClickInterface courseClickInterface) {
        this.courseRVModalArrayList = courseRVModalArrayList;
        this.context = context;
        this.courseClickInterface = courseClickInterface;
    }

    @NonNull
    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRVAdapter.ViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        // setting data to our recycler view item on below line.
        CourseRVModal courseRVModal = courseRVModalArrayList.get(position);
        holder.courseTV.setText(courseRVModal.getName());
        holder.subjectIV.setText(courseRVModal.getSubject());
        holder.coursePriceTV.setText(courseRVModal.getContact());
        if (courseRVModal.getPriority()== null){
            holder.state.setText(courseRVModal.getPriority());
        }


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
        return courseRVModalArrayList.size();
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
