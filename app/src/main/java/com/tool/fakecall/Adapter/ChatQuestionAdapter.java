package com.tool.fakecall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tool.fakecall.Models.CharactersModel;
import com.tool.fakecall.Models.QuestionsModel;
import com.tool.fakecall.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatQuestionAdapter extends RecyclerView.Adapter<ChatQuestionAdapter.ViewHolder> {

    private final Context context;
    private final List<QuestionsModel.QuestionsAnswer> itemList;
    click click;

    public ChatQuestionAdapter(Context context, List<QuestionsModel.QuestionsAnswer> itemList,click click1) {
        this.context = context;
        this.itemList = itemList;
        this.click = click1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_questions_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvQuestion.setText(itemList.get(position).getQuestion());

        holder.clQuestion.setOnClickListener(view -> {
            click.onItemClick(itemList.get(position));
            itemList.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion;
        ConstraintLayout clQuestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clQuestion = itemView.findViewById(R.id.clQuestion);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
        }
    }

    public interface click{
        void onItemClick(QuestionsModel.QuestionsAnswer questionsAnswer);
    }
}