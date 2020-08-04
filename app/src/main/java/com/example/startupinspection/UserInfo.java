package com.example.startupinspection;

public class UserInfo {

    private String userName;
    private String date;
    private String truckNum;
    private String dept;
    private int id;

    public UserInfo() {}

    public UserInfo(String userName, String date, String truckNum, String dept) {
        this.userName = userName;
        this.date = date;
        this.truckNum = truckNum;
        this.dept = dept;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(String truckNum) {
        this.truckNum = truckNum;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
