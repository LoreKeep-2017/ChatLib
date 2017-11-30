package org.chatlib.chatlib.controller;

import org.chatlib.chatlib.model.Action;
import org.chatlib.chatlib.model.Message;
import org.chatlib.chatlib.model.client.ClientRequest;
import org.chatlib.chatlib.model.operator.OperatorResponse;

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
            .url("http://139.59.139.151:8080/api/v1/greating")
            .addHeader("Origin", "*")
            .addHeader("Pragma", "no-cache")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Sec-WebSocket-Extensions", "permessage-deflate")
            .build();


    private OkHttpClient mOkClient;
    private WebSocket mWebSocket;
    private MessageParser mMessageParser;

    public ChatNetworkManager(WebSocketListener listener) {
        mOkClient = new OkHttpClient();
        mWebSocket = mOkClient.newWebSocket(mApiReq, listener);
        mMessageParser = new MessageParser();
    }

    public void sendMessage(String message) {
        if (message != null && !message.isEmpty()) {
            Message m = new Message("client", message);

            ClientRequest request = new ClientRequest(
                    Action.SEND_MESSAGE.toString(),
                    m
            );

            String jsonMessage = mMessageParser.parseToJSON(request);
            mWebSocket.send(jsonMessage);
        }
    }

    public Message sendAndGetFirstMessage() {
        String result = "";
        try {
            Response res = mOkClient.newCall(mGreeting).execute();
            result = res.body().string();
            OperatorResponse opResp = mMessageParser.parseToResponse(result);
            result = opResp.getGreeting();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return new Message("operator", result);
    }

    public Message getResponseMessage(String responseText) {
        OperatorResponse receivedResponse = mMessageParser.parseToResponse(responseText);
        Message respMes = new Message();

        if (receivedResponse.getAction().equals(Action.SEND_MESSAGE.toString())) {
            Message[] mes = receivedResponse
                    .getBody()
                    .getMessages();
            respMes = mes[mes.length - 1];

        } else if (receivedResponse.getAction().equals(Action.SEND_FIRST_MESSAGE.toString())) {

        } else if (receivedResponse.getAction().equals(Action.CHANGE_STATUS_ROOM.toString())) {

        } else if (receivedResponse.getAction().equals(Action.GET_ALL_MESSAGES.toString())) {

        } else if (receivedResponse.getGreeting() != null
                && !receivedResponse.getGreeting().isEmpty())
            respMes.setAuthor("operator")
                    .setBody(receivedResponse.getGreeting());

        return respMes;
    }

    public void disconnect() {
        mWebSocket.close(1000, "OK");
    }
}
