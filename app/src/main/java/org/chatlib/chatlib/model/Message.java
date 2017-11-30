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

    public long getTime() {
        return mTime;
    }

    public Message setTime(long mTime) {
        this.mTime = mTime;
        return this;
    }
}
