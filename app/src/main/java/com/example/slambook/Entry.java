package com.example.slambook;

public class Entry {
    String fullname, remark;

    public String getFullname() {
        return fullname;
    }

    public String getRemark() {
        return remark;
    }

    public Entry(String fullname, String remark) {
        this.fullname = fullname;
        this.remark = remark;
    }
}
