package org.chatlib.chatlib.controller;

import com.google.gson.Gson;

import org.chatlib.chatlib.model.client.ClientRequest;
import org.chatlib.chatlib.model.operator.Greeting;
import org.chatlib.chatlib.model.operator.OperatorResponse;

public class MessageParser {
    private Gson mParser;

    public MessageParser() {
        mParser = new Gson();
    }

    public OperatorResponse parseToResponse(String json) {
        return mParser.fromJson(json, OperatorResponse.class);
    }

    public String parseToJSON(ClientRequest cr) {
        return mParser.toJson(cr);
    }

    public Greeting parseGreeting(String json) {
        return mParser.fromJson(json, Greeting.class);
    }
}
