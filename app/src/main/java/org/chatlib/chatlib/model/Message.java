package org.chatlib.chatlib.model;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    @SerializedName("author")
    private String mAuthor;

    @SerializedName("body")
    private String mBody;

    @SerializedName("room")
    private int mIdRoom;

    @SerializedName("time")
    private long mTime;

    @SerializedName("image")
    private String mImage;

    @SerializedName("imageFormat")
    private String mImageFormat;




    public Message() {

    }

    public Message(String mAuthor, String mBody, String mImage) {
        this.mAuthor = mAuthor;
        this.mBody = mBody;
        this.mImage = mImage;
        if (mImage !=null) {
            mImageFormat = "png";
        }

    }

    public Message(String mAuthor, String mBody, long Time) {
        this.mAuthor = mAuthor;
        this.mBody = mBody;
        this.mTime = Time;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public Message setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
        return this;
    }

    public String getBody() {
        return mBody;
    }

    public Message setBody(String mBody) {
        this.mBody = mBody;
        return this;
    }

    public int getIdRoom() {
        return mIdRoom;
    }

    public Message setIdRoom(int mIdRoom) {
        this.mIdRoom = mIdRoom;
        return this;
    }

    public String getTime() {
        //return mTime;
        return getDate(mTime * 1000L);
    }

    public Message setTime(long mTime) {
        this.mTime = mTime;
        return this;
    }

    private String getDate(long timeStamp){

        try{
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}
