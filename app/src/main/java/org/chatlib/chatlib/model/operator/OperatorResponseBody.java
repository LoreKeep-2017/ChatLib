package org.chatlib.chatlib.model.operator;

import com.google.gson.annotations.SerializedName;

import org.chatlib.chatlib.model.Message;

public class OperatorResponseBody {
    @SerializedName("messages")
    private Message[] mMessages;

    public Message[] getmMessages() {
        return mMessages;
    }

    public void setmMessages(Message[] mMessages) {
        this.mMessages = mMessages;
    }
}
