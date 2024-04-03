package com.tool.fakecall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tool.fakecall.Models.LanguageModel;
import com.tool.fakecall.R;

import java.util.List;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.ViewHolder> {

    private final Context context;
    private  List<LanguageModel> list;
    private int selectedItem = RecyclerView.NO_POSITION;

    public LanguagesAdapter(Context context, List<LanguageModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_languages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvLanguage.setText(list.get(position).getName());
        holder.ivFlag.setImageResource(list.get(position).getImage());

        // Set the selected state of radio button based on the position
        holder.rbLanguage.setChecked(position == selectedItem);

        holder.rbLanguage.setOnClickListener(v -> {
            selectedItem = holder.getAdapterPosition();
            notifyDataSetChanged(); // Update UI to reflect the changes
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLanguage;
        RadioButton rbLanguage;
        ImageView ivFlag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFlag = itemView.findViewById(R.id.ivCountryFlag);
            rbLanguage = itemView.findViewById(R.id.rbLanguage);
            tvLanguage = itemView.findViewById(R.id.tvLanguage);
        }
    }

}