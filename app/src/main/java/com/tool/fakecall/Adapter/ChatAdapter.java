package com.tool.fakecall.Adapter;

import static android.view.View.GONE;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tool.fakecall.Models.QuestionsAnswer;
import com.tool.fakecall.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_QUESTION = 1;
    private static final int VIEW_TYPE_ANSWER = 2;

    private List<QuestionsAnswer> messageList;
    private Map<Integer, Boolean> answeredMap; // Map to track whether question has been answered
    private Handler handler;
    RecyclerView rvChat,rvQuestions;

    public ChatAdapter(List<QuestionsAnswer> messageList, RecyclerView rvChat, RecyclerView rvQuestions) {
        this.messageList = messageList;
        this.answeredMap = new HashMap<>();
        this.handler = new Handler();
        this.rvChat = rvChat;
        this.rvQuestions = rvQuestions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_QUESTION) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new QuestionViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new AnswerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        QuestionsAnswer message = messageList.get(position);
        if (getItemViewType(position) == VIEW_TYPE_QUESTION) {
            ((QuestionViewHolder) holder).bind(message.getQuestion(), position);
            // Check if answer has already been added for this question
            if (!answeredMap.containsKey(position)) {
                // Schedule adding answer after 2 seconds
                handler.postDelayed(() -> addAnswer(position), 2000); // Delay of 2 seconds
            }
        } else {
            ((AnswerViewHolder) holder).bind(message.getAnswer());
            rvQuestions.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getQuestion().endsWith("?") ? VIEW_TYPE_QUESTION : VIEW_TYPE_ANSWER;
    }

    private void addAnswer(int position) {
        if (position < messageList.size()) {
            QuestionsAnswer question = messageList.get(position);
            QuestionsAnswer answer = new QuestionsAnswer("",question.getAnswer());
            messageList.add(position + 1, answer);
            answeredMap.put(position, true); // Mark question as answered
            notifyItemInserted(position + 1);
            rvChat.scrollToPosition(position + 1);
        }
    }

    private static class QuestionViewHolder extends RecyclerView.ViewHolder {
        private TextView questionTextView;

        QuestionViewHolder(View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.text_message_body);
        }

        void bind(String question, int position) {
            questionTextView.setText(question);
        }
    }

    private static class AnswerViewHolder extends RecyclerView.ViewHolder {
        private TextView answerTextView;

        AnswerViewHolder(View itemView) {
            super(itemView);
            answerTextView = itemView.findViewById(R.id.text_message_body);
        }

        void bind(String answer) {
            answerTextView.setText(answer);

        }
    }
}
