package org.chatlib.chatlib.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatNetworkManager {
    private final Request mApiReq = new Request.Builder()
            .url("ws://")
            .addHeader("Origin", "*")
            .addHeader("Pragma", "no-cache")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Sec-WebSocket-Extensions", "permessage-deflate")
            .build();


    private OkHttpClient mOkClient;
    private WebSocket mWebSocket;

    public ChatNetworkManager(WebSocketListener listener) {
        mOkClient = new OkHttpClient();
        mWebSocket = mOkClient.newWebSocket(mApiReq, listener);
    }

    public void connect() {

    }

    public void sendMessage(String message) {

    }

    public void disconnect() {

    }
}
