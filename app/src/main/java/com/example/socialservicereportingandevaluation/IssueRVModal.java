package com.example.socialservicereportingandevaluation;

import android.os.Parcel;
import android.os.Parcelable;

public class IssueRVModal implements Parcelable {
    // creating variables for our different fields.
    private String name;
    private String issueDescription;
    private String contact;
    private String location;
    private String date;
    private String email;
    private String uid;
    private String subject;
    private String priority;
    private String source;
    private String state1;
    private String message;
    private String openDate;
    private String resolvedDate;
    private String repoterMessage;
    private String assign;
    private String ta;
    private String village;


    public String getUid(){return uid;}

    public void setUid(String uid) {
        this.uid = uid;
    }

    // creating an empty constructor.
    public IssueRVModal() {

    }

    protected IssueRVModal(Parcel in) {
        name = in.readString();
        uid = in.readString();
        issueDescription = in.readString();
        contact = in.readString();
        location = in.readString();
        date = in.readString();
        email = in.readString();
        subject = in.readString();
        priority = in.readString();
        source = in.readString();
        state1 = in.readString();
        message = in.readString();
        openDate = in.readString();
        resolvedDate = in.readString();
        repoterMessage = in.readString();
        assign = in.readString();
        ta = in.readString();
        village = in.readString();
    }

    public static final Creator<IssueRVModal> CREATOR = new Creator<IssueRVModal>() {
        @Override
        public IssueRVModal createFromParcel(Parcel in) {
            return new IssueRVModal(in);
        }

        @Override
        public IssueRVModal[] newArray(int size) {
            return new IssueRVModal[size];
        }
    };

    // creating getter and setter methods.
    public String getRepoterMessage(){return repoterMessage; }
    public void setRepoterMessage(String reporterMessage) {this.repoterMessage = repoterMessage; }

    public String getTa(){return ta; }
    public void setTa(String ta) {this.ta = ta; }

    public String getAssign(){return assign; }
    public void setAssign(String reporterMessage) {this.assign = assign; }

    public String getVillage(){return village; }
    public void setVillage(String village) {this.village = village; }

    public String getResolvedDate(){return resolvedDate; }
    public void setResolvedDate(String resolvedDate) {this.resolvedDate = resolvedDate; }

    public String getOpenDate(){return openDate; }
    public void setOpenDate(String openDate) {this.openDate = openDate; }

    public String getMessage(){return message; }
    public void setMessage(String message) {this.message = message; }

    public String getState1(){return state1; }
    public void setState1(String state1) {this.state1 = state1; }

    public String getSubject(){return subject;}

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getSource(){return source;}

    public void setSource(String source) {
        this.source = source;
    }

    public String getPriority(){return priority;}

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getName(){return name;}

    public void setName(String name) {
        this.name = name;
    }
    public String getIssueDescription(){ return  issueDescription;}

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getContact(){ return contact;}

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation(){return location;}

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate(){ return date;}

    public void setDate(String date) {
        this.date = date;
    }


    public String getEmail(){return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    public IssueRVModal(String uid, String name, String courseDescription, String coursePrice,
                        String bestSuitedFor, String courseImg, String courseLink, String subject,
                        String priority, String source,
                        String state1, String message, String openDate, String resolvedDate,
                        String repoterMessage, String assign, String ta, String village) {
        this.name = name;
        this.uid = uid;
        this.issueDescription = courseDescription;
        this.contact = coursePrice;
        this.location = bestSuitedFor;
        this.date = courseImg;
        this.email = courseLink;
        this.subject = subject;
        this.priority = priority;
        this.source = source;
        this.state1 = state1;
        this.message = message;
        this.openDate = openDate;
        this.resolvedDate = resolvedDate;
        this.repoterMessage = repoterMessage;
        this.assign = assign;
        this.ta = ta;
        this.village = village;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(uid);
        dest.writeString(issueDescription);
        dest.writeString(contact);
        dest.writeString(location);
        dest.writeString(date);
        dest.writeString(email);
        dest.writeString(subject);
        dest.writeString(priority);
        dest.writeString(source);
        dest.writeString(state1);
        dest.writeString(message);
        dest.writeString(openDate);
        dest.writeString(resolvedDate);
        dest.writeString(repoterMessage);
        dest.writeString(assign);
        dest.writeString(ta);
        dest.writeString(village);

    }
}

