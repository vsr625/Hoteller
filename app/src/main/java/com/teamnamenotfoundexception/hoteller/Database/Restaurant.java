package com.teamnamenotfoundexception.hoteller.Database;

public class Restaurant {
    public String RestId;
    public String RestName;
    public String RestArea;
    public String lat;
    public String mImagePath;
    public String log;

    public Restaurant(String restId, String restName, String restArea, String lat, String log, String mImagePath) {
        RestId = restId;
        RestName = restName;
        RestArea = restArea;
        this.lat = lat;
        this.mImagePath = mImagePath;
        this.log = log;
    }

    public String getmImagePath(){
        return mImagePath;
    }

    public String getRestId(){
        return RestId;
    }

    public void setRestId(String restId) {
        RestId = restId;
    }

    public String getRestName() {
        return RestName;
    }

    public void setRestName(String restName) {
        RestName = restName;
    }

    public String getRestArea() {
        return RestArea;
    }

    public void setRestArea(String restArea) {
        RestArea = restArea;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
