package org.chatlib.chatlib.model.client;

import org.chatlib.chatlib.model.Message;

public class Request {
    private String mType;
    private String mAction;
    private Message[] mBody;

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String mAction) {
        this.mAction = mAction;
    }

    public Message[] getBody() {
        return mBody;
    }

    public void setBody(Message[] mBody) {
        this.mBody = mBody;
    }
}
