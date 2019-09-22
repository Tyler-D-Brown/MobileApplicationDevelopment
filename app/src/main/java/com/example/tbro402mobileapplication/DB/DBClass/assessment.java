package com.example.tbro402mobileapplication.DB.DBClass;

import java.util.Date;

public class assessment {
    private int id;
    private Date startDate;
    private Date endDate;
    private String title;
    private int course;
    private String type;
    private String status;

    public assessment(){
    }

    public assessment(int i, Date start, Date end, String tite, int cour, String ty, String state){
        this.id=i;
        this.startDate=start;
        this.course=cour;
        this.endDate=end;
        this.title=tite;
        this.type=ty;
        this.status=state;
    }

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
