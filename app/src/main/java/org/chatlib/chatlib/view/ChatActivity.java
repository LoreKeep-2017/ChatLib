package org.chatlib.chatlib.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.chatlib.chatlib.R;
import org.chatlib.chatlib.controller.ChatNetworkManager;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
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
    private ImageView mImageViewTmp;
    private Button mDeleteImageButton;
    private RecyclerView mChatRV;
    private ProgressBar mProgressBar;

    private MessageAdapter mMessageAdapter;
    private ChatNetworkManager mNetworkManager;

    private LoadImageFromGallery loadImageFromGallery;

    private String imageString;

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
        mImageViewTmp = findViewById(R.id.input_image_tmp);

        mDeleteImageButton = findViewById(R.id.delete_image);
        mDeleteImageButton.setOnClickListener(new DeleteImageListener());

        mNetworkManager = new ChatNetworkManager(new WSListener());

        mMessageAdapter = new MessageAdapter(getApplicationContext(), new ArrayList<>());

        mChatRV = findViewById(R.id.chat_message_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mChatRV.setLayoutManager(linearLayoutManager);
        mChatRV.setAdapter(mMessageAdapter);

        mProgressBar = findViewById(R.id.progress_bar);
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
            Log.e(TAG, "onClick: " + imageString);
            if (mInputMessageText.getText() != null && !mInputMessageText.getText().equals("") || imageString != null) {
                mNetworkManager.sendMessage(mInputMessageText.getText().toString(), imageString);
                mInputMessageText.setText("");

                //clear image
                mImageView.setImageURI(null);
                mDeleteImageButton.setVisibility(View.GONE);
                mImageView.getLayoutParams().height = 0;
                mImageView.getLayoutParams().width = 0;
                mLinearLayout.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            }

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
            mImageView.getLayoutParams().height = 0;
            mImageView.getLayoutParams().width = 0;
            mLinearLayout.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && data != null) {
//            mImageView.setImageURI(data.getData());
            mProgressBar.setVisibility(View.VISIBLE);
            mImageViewTmp.setImageURI(data.getData());
            loadImageFromGallery = new LoadImageFromGallery(mImageView, mImageViewTmp, data.getData());
            loadImageFromGallery.execute();
            mDeleteImageButton.setVisibility(View.VISIBLE);
            mImageView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            mImageView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        }

    }
    //endregion

    //AsyncTask
    private class LoadImageFromGallery extends AsyncTask<Void, Void, String> {

        WeakReference weakReferenceImageView;
        WeakReference weakReferenceImageViewTmp;
        Uri imageUri;

        public LoadImageFromGallery(ImageView imageView, ImageView imageViewTmp, Uri uri) {
            weakReferenceImageView = new WeakReference<>(imageView);
            weakReferenceImageViewTmp = new WeakReference<>(imageViewTmp);
            imageUri = uri;
        }


        @Override
        protected String doInBackground(Void... voids) {
            String image = null;
            if (weakReferenceImageViewTmp != null) {
                ImageView weak = (ImageView) weakReferenceImageViewTmp.get();
                if (weak != null) {
                    weak.buildDrawingCache();
                    BitmapDrawable drawable = (BitmapDrawable) weak.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] bb = bos.toByteArray();
                    image = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                }
            }
            return image;
        }

        @Override
        protected void onPostExecute(String image) {
            super.onPostExecute(image);

            if (image != null) {
                if (weakReferenceImageView != null && weakReferenceImageViewTmp != null) {
                    ImageView weak = (ImageView) weakReferenceImageView.get();
                    ImageView weakTmp = (ImageView) weakReferenceImageViewTmp.get();
                    if (weak != null && weakTmp != null) {
                        weak.setImageURI(imageUri);
                        weakTmp.setImageURI(null);
                        imageString = image;
                    }
                }

            }

            mProgressBar.setVisibility(View.GONE);
        }
    }
    //end
}
