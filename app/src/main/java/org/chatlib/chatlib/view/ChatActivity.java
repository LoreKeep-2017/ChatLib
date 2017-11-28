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
import org.chatlib.chatlib.model.Action;
import org.chatlib.chatlib.model.Message;
import org.chatlib.chatlib.model.client.ClientRequest;
import org.chatlib.chatlib.model.operator.OperatorResponse;

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
            send(mInputMessageText.getText().toString());
            mInputMessageText.setText("");
        }

        private void send(String text) {
            if (text != null && !text.isEmpty()) {
                Message message = new Message("client", text);

                ClientRequest request = new ClientRequest(
                        Action.SEND_MESSAGE.toString(),
                        new Message[]{message}
                );

                String jsonMessage = mMessageParser.parseToJSON(request);
                mNetworkManager.sendMessage(jsonMessage);
            }
        }
    }

    public class WSListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, okhttp3.Response response) {
            /*String json = mNetworkManager.initChatting();
            Greeting greeting = mMessageParser.parseGreeting(json);
            mMessageAdapter.addMessage(new Message("operator", greeting.getGreeting()));*/
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            OperatorResponse receivedResponse = mMessageParser.parseToResponse(text);
            switch (receivedResponse.getAction()) {
                case "sendMessage":
                    Message[] mes = receivedResponse
                            .getBody1()
                            .getMessages();
                    mMessageAdapter.addMessage(mes[mes.length - 1]);
                    break;
                case "sendFirstMessage":
                    break;
                case "changeStatusRoom":
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
            Log.wtf(TAG, t);
        }
    }
    //endregion
}
