package org.chatlib.chatlib.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.chatlib.chatlib.R;
import org.chatlib.chatlib.controller.ChatNetworkManager;
import org.chatlib.chatlib.controller.MessageParser;
import org.chatlib.chatlib.model.Message;

import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatActivity extends Activity {
    private static final String TAG = "ChatActivity ----->";

    private EditText mInputMessageText;
    private Button mSendMessage;
    private RecyclerView mChatRV;

    private MessageAdapter mMessageAdapter;
    private ChatNetworkManager mNetworkManager;
    private MessageParser mMessageParser;


    //region Activity Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mInputMessageText = findViewById(R.id.input_message_text);

        mSendMessage = findViewById(R.id.send_button);
        mSendMessage.setOnClickListener(new SendMessage());

        mMessageAdapter = new MessageAdapter(new ArrayList<Message>());

        mChatRV = findViewById(R.id.chat_message_list);
        mChatRV.setLayoutManager(new LinearLayoutManager(this));
        mChatRV.setAdapter(mMessageAdapter);

        mNetworkManager = new ChatNetworkManager(new WSListener());
        mMessageParser = new MessageParser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetworkManager.disconnect();
    }

    //endregion

    //region Listener
    private class SendMessage implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String jsonMessage = mMessageParser.parseToJSON(mInputMessageText
                    .getText()
                    .toString());
            mInputMessageText.setText("");

            mNetworkManager.sendMessage(jsonMessage);
        }
    }

    private class WSListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            mNetworkManager.connect();
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Message receiveRequestBody = mMessageParser.parseToMessage(text);
            mMessageAdapter.addMessage(receiveRequestBody);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Log.wtf(TAG, t);
        }
    }
    //endregion
}
