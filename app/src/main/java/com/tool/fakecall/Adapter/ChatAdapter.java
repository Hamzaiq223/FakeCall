package com.tool.fakecall.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tool.fakecall.Models.QuestionsModel;
import com.tool.fakecall.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {

    private List<QuestionsModel.QuestionsAnswer> messageList;
    private String friend_id;
    private Context context;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public ChatAdapter(List<QuestionsModel.QuestionsAnswer> messageList, String friend_id, Context context){
        this.friend_id = friend_id;
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        QuestionsModel.QuestionsAnswer message = messageList.get(position);
//        if(message.getSender_id().equals(friend_id)) {
//            return VIEW_TYPE_MESSAGE_RECEIVED;
//        }
        return VIEW_TYPE_MESSAGE_SENT;
    }

    @NonNull
    @Override
    public ChatAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == VIEW_TYPE_MESSAGE_SENT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent,
                    parent, false);
        }
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received,
                    parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MessageViewHolder holder, int position) {
        QuestionsModel.QuestionsAnswer message = messageList.get(position);
//        if(message.getPhotoUrl() == null){
////            holder.photo_image_view.setVisibility(View.GONE);
////            holder.setTexts(message);
//        }
//        else{
////            holder.photo_image_view.setImageURI(Uri.parse(message.getPhotoUrl()));
////            String dateTime = message.getTimestamp() + ", " + message.getDate();
//            holder.message_text_view.setVisibility(View.GONE);
//            holder.time_text_view.setText(dateTime);
//        }
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        try{
            return messageList.size();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return 0;
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView message_text_view;
        private TextView time_text_view;
        private ImageView photo_image_view;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            message_text_view = itemView.findViewById(R.id.text_message_body);
            time_text_view = itemView.findViewById(R.id.text_message_time);
            photo_image_view = itemView.findViewById(R.id.photoImageView);
        }

//        private void setTexts(QuestionsModel.QuestionsAnswer message) {
//            message_text_view.setText(message.getMessage());
//            String dateTime = message.getTimestamp() + ", " + message.getDate();
//            time_text_view.setText(dateTime);
//        }

    }

}
