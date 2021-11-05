package com.example.petapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

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
}
