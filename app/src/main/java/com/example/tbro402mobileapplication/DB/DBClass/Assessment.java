package com.example.tbro402mobileapplication.DB.DBClass;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity

public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date startDate;
    private Date endDate;
    private String title;
    private int course;
    private String type;
    private String status;

    public Assessment(int id, Date startDate, Date endDate, String title, int course, String type, String status){
        this.id=id;
        this.startDate=startDate;
        this.endDate=endDate;
        this.title=title;
        this.course=course;
        this.type=type;
        this.status=status;
    }

    @Ignore
    public Assessment(){}

    public int getId() {
        return id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getCourse() {
        return course;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }
}
