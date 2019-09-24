package com.example.studentdatabase;

public class UserDetails {

    private String branch,year,batch;

    UserDetails(String branch,String year,String batch){
        this.branch = branch;
        this.batch = batch;
        this.year = year;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
