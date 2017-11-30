package org.chatlib.chatlib.model.client;

import com.google.gson.annotations.SerializedName;

import org.chatlib.chatlib.model.Message;

public class ClientRequest {

    public ClientRequest() {
        mType = "client";
    }

    public ClientRequest(String mAction, Message mBody) {
        this();
        this.mAction = mAction;
        this.mBody = mBody;
    }

    @SerializedName("type")
    private String mType;

    @SerializedName("action")
    private String mAction;

    @SerializedName("body")
    private Message mBody;

    public String getAction() {
        return mAction;
    }

    public void setAction(String mAction) {
        this.mAction = mAction;
    }

    public Message getmBody() {
        return mBody;
    }

    public void setmBody(Message mBody) {
        this.mBody = mBody;
    }
}
