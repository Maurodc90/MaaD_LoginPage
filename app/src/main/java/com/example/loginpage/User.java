package com.example.loginpage;

public class User {
    private String fn,sn;

    public User() {
    }

    public User(String fn, String sn) {
        this.fn = fn;
        this.sn = sn;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

}
