package org.chatlib.chatlib.controller;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatNetworkManager {
    private final Request mApiReq = new Request.Builder()
            .url("ws://139.59.139.151:8080/api/v1/client")
            .addHeader("Origin", "*")
            .addHeader("Pragma", "no-cache")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Sec-WebSocket-Extensions", "permessage-deflate")
            .build();

    private final Request mGreeting = new Request.Builder()
            .url("http://139.59.139.151:8080/greating")
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

    public String initChatting() {
        String result = "";
        try {
            Response res = mOkClient.newCall(mGreeting).execute();
            result = res.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void sendMessage(String message) {
        mWebSocket.send(message);
    }

    public void disconnect() {
        mWebSocket.close(1000, "OK");
    }
}
