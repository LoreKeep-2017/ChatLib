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

import java.util.ArrayList;

import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatActivity extends Activity {
    private static final String TAG = "ChatActivity ----->";

    private EditText mInputMessageText;
    private Button mSendMessage;
    private RecyclerView mChatRV;

    private MessageAdapter mMessageAdapter;
    private ChatNetworkManager mNetworkManager;

    //region Activity Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mInputMessageText = findViewById(R.id.input_message_text);

        mSendMessage = findViewById(R.id.send_button);
        mSendMessage.setOnClickListener(new SendMessage());

        mMessageAdapter = new MessageAdapter(new ArrayList<>());

        mChatRV = findViewById(R.id.chat_message_list);
        mChatRV.setLayoutManager(new LinearLayoutManager(this));
        mChatRV.setAdapter(mMessageAdapter);

        mNetworkManager = new ChatNetworkManager(new WSListener());
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
            mNetworkManager.sendMessage(mInputMessageText.getText().toString());
            mInputMessageText.setText("");
        }
    }

    public class WSListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, okhttp3.Response response) {
            mMessageAdapter.addMessage(mNetworkManager.sendAndGetFirstMessage());
            runOnUiThread(() -> mChatRV.setAdapter(mMessageAdapter));
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            mMessageAdapter.addMessage(mNetworkManager.getResponseMessage(text));
            runOnUiThread(() -> mChatRV.setAdapter(mMessageAdapter));
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
            Log.wtf(TAG, t);
        }
    }
    //endregion
}
