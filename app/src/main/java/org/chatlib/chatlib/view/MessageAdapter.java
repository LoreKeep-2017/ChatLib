package org.chatlib.chatlib.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.chatlib.chatlib.R;
import org.chatlib.chatlib.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageVH> {
    private List<Message> mMessages;

    public MessageAdapter(List<Message> mRequestBodies) {
        this.mMessages = mRequestBodies;
    }

    public void addMessage(Message message) {
        if (message.getBody() != null
                && !message.getBody().isEmpty())
            mMessages.add(message);
    }

    @Override
    public MessageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageVH(view);
    }

    @Override
    public void onBindViewHolder(MessageVH holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    class MessageVH extends RecyclerView.ViewHolder {
        TextView mMessageText;

        MessageVH(View itemView) {
            super(itemView);
            mMessageText = itemView.findViewById(R.id.message_text);
        }

        void bind(int position) {
            Message curMessage = mMessages.get(position);
            mMessageText.setText(curMessage.getBody());
        }
    }
}
