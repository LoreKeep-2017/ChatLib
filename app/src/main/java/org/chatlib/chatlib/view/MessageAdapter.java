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
    private List<Message> mRequestBodies;

    public MessageAdapter(List<Message> mRequestBodies) {
        this.mRequestBodies = mRequestBodies;
    }

    public void addMessage(Message requestBody) {
        mRequestBodies.add(requestBody);
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
        Message curRequestBody = mRequestBodies.get(position);
        holder.mMessageText.setText(curRequestBody.getBody());
    }

    @Override
    public int getItemCount() {
        return mRequestBodies.size();
    }

    protected class MessageVH extends RecyclerView.ViewHolder {
        TextView mMessageText;

        public MessageVH(View itemView) {
            super(itemView);
            mMessageText = itemView.findViewById(R.id.message_text);
        }
    }
}
