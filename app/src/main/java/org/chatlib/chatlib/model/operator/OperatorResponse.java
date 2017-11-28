package org.chatlib.chatlib.model.operator;

import com.google.gson.annotations.SerializedName;

import org.chatlib.chatlib.model.Message;

public class OperatorResponse {
    @SerializedName("action")
    private String mAction;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("code")
    private int mCode;

    @SerializedName("")
    private OperatorResponseBody mBody1;

    @SerializedName("body")
    private Message[] mBody2;

    public String getAction() {
        return mAction;
    }

    public void setAction(String mAction) {
        this.mAction = mAction;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int mCode) {
        this.mCode = mCode;
    }

    public OperatorResponseBody getBody1() {
        return mBody1;
    }

    public void setBody1(OperatorResponseBody mBody1) {
        this.mBody1 = mBody1;
    }

    public Message[] getBody2() {
        return mBody2;
    }

    public void setBody2(Message[] mBody2) {
        this.mBody2 = mBody2;
    }
}
