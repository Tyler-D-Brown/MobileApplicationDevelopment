package com.example.tbro402mobileapplication.DB.DBClass;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class Course {
    private String title;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date startDate;
    private Date endDate;
    private int term;
    private String status;
    private String note;
    private int mentor;

    @Ignore
    public Course() {
        id = -1;
        mentor = -1;
    }

    public Course(String title, int id, Date startDate, Date endDate, int term, String status, String note, int mentor) {
        this.title = title;
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.term = term;
        this.status = status;
        this.note = note;
        this.mentor = mentor;
    }

    @Ignore
    public Course(Course c){
        this.title = c.getTitle();
        this.id = c.getId();
        this.startDate = c.getStartDate();
        this.endDate = c.getEndDate();
        this.term = c.getTerm();
        this.status = c.getStatus();
        this.note = c.getNote();
        this.mentor = c.getMentor();
    }

    @Ignore
    public Course(String title, Date startDate, Date endDate, int term, String status, String note, int mentor) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.term = term;
        this.status = status;
        this.note = note;
        this.mentor = mentor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getMentor() {
        return this.mentor;
    }

    public void setMentor(int mentor) {
        this.mentor = mentor;
    }
}
