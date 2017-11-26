package org.chatlib.chatlib;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatActivity extends Activity {
    private static final String TAG = "ChatActivity ----->";

    private EditText mInputMessageText;
    private Button mSendMessage;
    private RecyclerView mChatRV;
    private ChatNetworkManager mNetworkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mInputMessageText = findViewById(R.id.input_message_text);

        mSendMessage = findViewById(R.id.send_button);
        mSendMessage.setOnClickListener(new SendMessage());

        mChatRV = findViewById(R.id.chat_recycler);
        mChatRV.setLayoutManager(new LinearLayoutManager(this));
        mChatRV.setAdapter(null);

        mNetworkManager = new ChatNetworkManager(new WSListener());
    }

    private void updateChat() {

    }

    //region Listener
    private class SendMessage implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mNetworkManager.sendMessage(mInputMessageText.getText().toString());
            mInputMessageText.setText("");
        }
    }

    private class WSListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {

        }


        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Log.wtf(TAG, t);
        }
    }
    //endregion
}
