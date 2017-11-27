package org.chatlib.chatlib.model;

import java.util.Date;

public class Message {
    private String mAuthor;
    private String mBody;
    private int mIdRoom;
    private Date mTime;

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

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date mTime) {
        this.mTime = mTime;
    }
}
