package org.chatlib.chatlib;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageVH> {
    private List<Message> mMessages;

    public MessageAdapter(List<Message> mMessages) {
        this.mMessages = mMessages;
    }

    public void addMessage(Message message) {
        mMessages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public MessageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessageVH(view);
    }

    @Override
    public void onBindViewHolder(MessageVH holder, int position) {
        Message curMessage = mMessages.get(position);
        holder.mMessageText.setText(curMessage.getMessageText());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    protected class MessageVH extends RecyclerView.ViewHolder {
        TextView mMessageText;

        public MessageVH(View itemView) {
            super(itemView);
            mMessageText = itemView.findViewById(R.id.message_text);
        }
    }
}
