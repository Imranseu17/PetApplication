package com.example.petapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String taskName;

    private String description;

    private String status;

    private String createdDate;

    private String deadline;


    @Nullable
    private String email;


    @Nullable
    private String phoneNumber;


    @Nullable
    private String url;


    public Note(String taskName, String description,
                String status,String createdDate,String deadline,
                String email, String phoneNumber , String url) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.url = url;
    }


    protected Note(Parcel in) {
        id = in.readInt();
        taskName = in.readString();
        description = in.readString();
        status = in.readString();
        createdDate = in.readString();
        deadline = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        url = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getDeadline() {
        return deadline;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Nullable
    public String getUrl() {
        return url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(taskName);
        parcel.writeString(description);
        parcel.writeString(status);
        parcel.writeString(createdDate);
        parcel.writeString(deadline);
        parcel.writeString(email);
        parcel.writeString(phoneNumber);
        parcel.writeString(url);
    }
}
