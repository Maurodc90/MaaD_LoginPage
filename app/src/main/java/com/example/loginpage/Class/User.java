package com.example.loginpage.Class;

public class User {
    private String fn, sn, url;
    private boolean isCritique;

    public User(String fn, String sn, String url, boolean isCritique) {
        this.fn = fn;
        this.sn = sn;
        this.url = url;
        this.isCritique = isCritique;
    }

    public User(String fn, String sn, boolean isCritique) {
        this.fn = fn;
        this.sn = sn;
        this.isCritique = isCritique;
    }

    public User() {
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

    public boolean isCritique() {
        return isCritique;
    }
    public void setCritique(boolean critique) {
        isCritique = critique;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

}
