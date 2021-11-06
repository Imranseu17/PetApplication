package com.example.petapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
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


    public Note(String taskName, String description,
                String status,String createdDate,String deadline) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.deadline = deadline;
    }

    protected Note(Parcel in) {
        id = in.readInt();
        taskName = in.readString();
        description = in.readString();
        status = in.readString();
        createdDate = in.readString();
        deadline = in.readString();
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
    }
}
