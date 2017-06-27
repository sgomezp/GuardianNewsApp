package com.example.android.guardiannewsapp;

/**
 * Created by sgomezp on 22/06/2017.
 */

public class News {

    // Headline of the news
    private String mHeadline;

    // Section of the news
    private String mSection;

    // Date of publishing news
    private String mDate;

    // Time of publishing news
    private  String mTime;

    // URL Headline
    private String mWebUrl;

    /*
    * Create a new News object
    * @param headline
    * @param section
    * @param date
    * @param time
    * */

    public News(String headline, String section, String date, String webUrl){
        mHeadline = headline;
        mSection = section;
        mDate = date;
        //mTime = time;
        mWebUrl = webUrl;
    }

    // Get headline
    public String getHeadline(){
        return mHeadline;
    }

    // Set headline
    public void setHeadline(String headline){
        mHeadline = headline;
    }

    // Get section
    public String getSection(){
        return mSection;
    }

    // Set section
    public void setSection(String section){
        mSection = section;
    }

    // Get date
    public String getDate(){
        return mDate;
    }

    // Set date
    public void setDate(String date){
        mDate = date;
    }

    // Get time
    public String getTime(){
        return mTime;
    }

    // Set Time
    public void setTime(String time){
        mTime = time;
    }

    // Get webUrl
    public String getWebUrl(){
        return mWebUrl;
    }

    // Set webUrl
    public void setWebUrl(String webUrl){
        mWebUrl = webUrl;
    }



    @Override
    public String toString() {

        return super.toString();
    }








}
