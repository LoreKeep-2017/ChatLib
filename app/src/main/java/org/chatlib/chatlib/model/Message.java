package org.chatlib.chatlib.model;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("author")
    private String mAuthor;

    @SerializedName("body")
    private String mBody;

    @SerializedName("room")
    private int mIdRoom;

    @SerializedName("time")
    private long mTime;

    public Message() {

    }

    public Message(String mAuthor, String mBody) {
        this.mAuthor = mAuthor;
        this.mBody = mBody;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String mBody) {
        this.mBody = mBody;
    }

    public int getIdRoom() {
        return mIdRoom;
    }

    public void setIdRoom(int mIdRoom) {
        this.mIdRoom = mIdRoom;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long mTime) {
        this.mTime = mTime;
    }
}
