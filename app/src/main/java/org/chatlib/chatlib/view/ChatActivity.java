package org.chatlib.chatlib.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.chatlib.chatlib.R;
import org.chatlib.chatlib.controller.ChatNetworkManager;

import java.util.ArrayList;

import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatActivity extends Activity {
    private static final String TAG = "ChatActivity ----->";
    public static final int PICK_IMAGE = 1;

    private LinearLayout mLinearLayout;

    private EditText mInputMessageText;
    private Button mSendMessage;
    private Button mAttachButton;
    private ImageView mImageView;
    private Button mDeleteImageButton;
    private RecyclerView mChatRV;

    private MessageAdapter mMessageAdapter;
    private ChatNetworkManager mNetworkManager;

    //region Activity Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mLinearLayout = findViewById(R.id.edit_message);

        mInputMessageText = findViewById(R.id.input_message_text);

        mSendMessage = findViewById(R.id.send_button);
        mSendMessage.setOnClickListener(new SendMessage());

        mAttachButton = findViewById(R.id.attach_button);
        mAttachButton.setOnClickListener(new AttachListener());

        mImageView = findViewById(R.id.input_image);

        mDeleteImageButton = findViewById(R.id.delete_image);
        mDeleteImageButton.setOnClickListener(new DeleteImageListener());

        mMessageAdapter = new MessageAdapter(new ArrayList<>());

        mChatRV = findViewById(R.id.chat_message_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mChatRV.setLayoutManager(linearLayoutManager);
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

    private class AttachListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
    }

    private class DeleteImageListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mImageView.setImageURI(null);
            mDeleteImageButton.setVisibility(View.GONE);
            mImageView.getLayoutParams().height = 0;//LinearLayout.LayoutParams.WRAP_CONTENT;
            mImageView.getLayoutParams().width = 0;//LinearLayout.LayoutParams.WRAP_CONTENT;
            mLinearLayout.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
//            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) mLinearLayout.getLayoutParams();
//            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
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

    //region result

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            mImageView.setImageURI(data.getData());
            mDeleteImageButton.setVisibility(View.VISIBLE);
            mImageView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            mImageView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        }
        
    }
    //endregion
}
