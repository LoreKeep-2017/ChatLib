package org.chatlib.chatlib.controller;

import org.chatlib.chatlib.model.Action;
import org.chatlib.chatlib.model.Message;
import org.chatlib.chatlib.model.client.ClientRequest;
import org.chatlib.chatlib.model.operator.OperatorResponse;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatNetworkManager {

    private static final String SCHEME_AND_HOST_WS = "ws://139.59.139.151/";
    private static final String SCHEME_AND_HOST_HTTP = "http://139.59.139.151/";
    private static final String GREETINGS_URL_PATH = "greating/";
    private static final String BASE_URL_PATH = "api/v1/client";
    private static final String RESTORE_URL_PATH = "diff/?id=*&size=0";

    private final Request.Builder mBaseApiReqBuilder = new Request.Builder()
            .addHeader("Origin", "*")
            .addHeader("Pragma", "no-cache")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Sec-WebSocket-Extensions", "permessage-deflate");

    private final Request mBaseApiReq = mBaseApiReqBuilder
            .url(SCHEME_AND_HOST_WS.concat(BASE_URL_PATH))
            .build();

    private final Request mGreeting = mBaseApiReqBuilder
            .url(SCHEME_AND_HOST_HTTP.concat(GREETINGS_URL_PATH))
            .build();

    private static boolean isFirst = true;

    private OkHttpClient mOkClient;
    private WebSocket mWebSocket;
    private MessageParser mMessageParser;

    public ChatNetworkManager(WebSocketListener listener) {
        mOkClient = new OkHttpClient();
        mWebSocket = mOkClient.newWebSocket(mBaseApiReq, listener);
        mMessageParser = new MessageParser();
    }

    public void sendMessage(String message, String image) {
//        if (message != null && !message.isEmpty() || image != null) {
        Message m = new Message("client", message, image);

        Action action = Action.SEND_MESSAGE;

        if (isFirst) {
            action = Action.SEND_FIRST_MESSAGE;
            isFirst = false;
        }

        ClientRequest request = new ClientRequest(
                action.toString(),
                m
        );

        String jsonMessage = mMessageParser.parseToJSON(request);
        mWebSocket.send(jsonMessage);
//        }
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

        return new Message("operator", result, Calendar.getInstance().getTime().getTime() / 1000L);
    }

    public Message[] sendRestoreMessage(int roomId) {
        Message[] messages = new Message[0];
        Request rq = mBaseApiReqBuilder
                .url(SCHEME_AND_HOST_HTTP
                        .concat(RESTORE_URL_PATH.replace("*", String.valueOf(roomId))))
                .build();
        try {
            Response res = mOkClient.newCall(rq).execute();
            String result = res.body().string();
            OperatorResponse opResp = mMessageParser.parseToResponse(result);
            messages = opResp.getBody().getMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return messages;
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

        } else if (receivedResponse.getAction().equals(Action.GET_ALL_MESSAGES.toString())) {

        } else if (receivedResponse.getGreeting() != null
                && !receivedResponse.getGreeting().isEmpty())
            respMes.setAuthor("operator")
                    .setBody(receivedResponse.getGreeting());

        return respMes;
    }

    public void disconnect() {
        mWebSocket.close(1000, "OK");
        isFirst = false;
    }
}
