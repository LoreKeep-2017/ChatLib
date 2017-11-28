package org.chatlib.chatlib.model.operator;

import com.google.gson.annotations.SerializedName;

public class OperatorResponse {
    @SerializedName("action")
    private String mAction;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("code")
    private int mCode;

    @SerializedName("body")
    private OperatorResponseBody mBody;

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
        return mBody;
    }

    public void setBody1(OperatorResponseBody mBody) {
        this.mBody = mBody;
    }
}
