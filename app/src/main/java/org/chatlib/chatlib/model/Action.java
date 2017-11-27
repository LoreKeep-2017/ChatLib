package org.chatlib.chatlib.model;

public enum Action {
    SEND_MESSAGE("sendMessage"),
    CHANGE_STATUS_ROOM("changeStatusRoom"),
    CLOSE_ROOM("closeRoom"),
    GET_ALL_MESSAGES("getAllMessages"),
    SEND_FIRST_MESSAGE("sendFirstMessage");

    String mAction;

    Action(String mAction) {
        this.mAction = mAction;
    }
}
