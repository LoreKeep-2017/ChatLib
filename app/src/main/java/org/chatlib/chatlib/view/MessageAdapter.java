package org.chatlib.chatlib.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.chatlib.chatlib.R;
import org.chatlib.chatlib.controller.ChatNetworkManager;
import org.chatlib.chatlib.model.Message;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MessageAdapter";
    private List<Message> mMessages;
    private static final int VIEW_TYPE_MESSAGE_SENT = 0;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 1;

    private Context context;

    public MessageAdapter(Context context, List<Message> mRequestBodies) {
        this.context = context;
        this.mMessages = mRequestBodies;
    }

    public void addMessage(Message message) {
        if (message.getBody() != null
                && !message.getBody().isEmpty() || message.getImageUrl() != null && !message.getImageUrl().isEmpty())
        mMessages.add(message);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            Log.e(TAG, "onCreateViewHolder: "  + VIEW_TYPE_MESSAGE_SENT);
            View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageVH(view);

        } else {
            Log.e(TAG, "onCreateViewHolder: "  + VIEW_TYPE_MESSAGE_RECEIVED);
            View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.message_receive_item, parent, false);
        return new MessageReceiveVH(view);


        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //holder.bind(position);
        Message message = mMessages.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                Log.e(TAG, "onBindViewHolder: " + VIEW_TYPE_MESSAGE_SENT);
                ((MessageVH) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                Log.e(TAG, "onBindViewHolder: " + VIEW_TYPE_MESSAGE_RECEIVED);
                ((MessageReceiveVH) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = mMessages.get(position);
        if (message.getAuthor().equals("client")){
            Log.e(TAG, "getItemViewType: " + VIEW_TYPE_MESSAGE_SENT);
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            Log.e(TAG, "getItemViewType: " + VIEW_TYPE_MESSAGE_RECEIVED);
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }

    }

    class MessageVH extends RecyclerView.ViewHolder {
        TextView mMessageText;
        TextView mMessageTime;
        ImageView mMessageImage;

        MessageVH(View itemView) {
            super(itemView);
            mMessageText = itemView.findViewById(R.id.message_text);
            mMessageTime = itemView.findViewById(R.id.message_time);
            mMessageImage = itemView.findViewById(R.id.message_image);
        }


        void bind(Message message) {
            if (message.getBody() == null || message.getBody().isEmpty()){
                mMessageText.setVisibility(View.GONE);
            } else {
                mMessageText.setText(message.getBody());
            }
            mMessageTime.setText(message.getTime());
            if (message.getImageUrl() != null) {
                Picasso.with(context).
                        load("http://139.59.139.151/client/"+message.getIdRoom()+"/"+message.getImageUrl()).
                        into(mMessageImage);
            }
        }
    }

    class MessageReceiveVH extends RecyclerView.ViewHolder {
        TextView mMessageText;
        TextView mMessageTime;
        ImageView mMessageImage;

        MessageReceiveVH(View itemView) {
            super(itemView);
            mMessageText = itemView.findViewById(R.id.message_receive_text);
            mMessageTime = itemView.findViewById(R.id.message_receive_time);
            mMessageImage = itemView.findViewById(R.id.message_receive_image);
        }

        void bind(Message message) {
            if (message.getBody() == null || message.getBody().isEmpty()){
                mMessageText.setVisibility(View.GONE);
            } else {
                mMessageText.setText(message.getBody());
            }
            mMessageTime.setText(message.getTime());
            if (message.getImageUrl() != null) {
                Picasso.with(context).
                        load("http://139.59.139.151/client/"+message.getIdRoom()+"/"+message.getImageUrl()).
                        into(mMessageImage);
            }
        }
    }
}
