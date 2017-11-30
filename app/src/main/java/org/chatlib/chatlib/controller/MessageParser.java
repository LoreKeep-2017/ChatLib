package org.chatlib.chatlib.controller;

import com.google.gson.Gson;

import org.chatlib.chatlib.model.client.ClientRequest;
import org.chatlib.chatlib.model.operator.OperatorResponse;

class MessageParser {
    private Gson mParser;

    MessageParser() {
        mParser = new Gson();
    }

    OperatorResponse parseToResponse(String json) {
        return mParser.fromJson(json, OperatorResponse.class);
    }

    String parseToJSON(ClientRequest cr) {
        return mParser.toJson(cr);
    }
}