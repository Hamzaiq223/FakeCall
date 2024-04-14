package com.tool.fakecall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tool.fakecall.Activities.Languages.Languages;
import com.tool.fakecall.Models.LanguageModel;
import com.tool.fakecall.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.ViewHolder>  {

    private final Context context;
    private  ArrayList<LanguageModel> list;
    private ArrayList<LanguageModel> filteredLanguageList;
    private int selectedItem = RecyclerView.NO_POSITION;
    click click;

    public LanguagesAdapter(Context context, ArrayList<LanguageModel> list, click click) {
        this.context = context;
        this.list = list;
        this.filteredLanguageList = new ArrayList<>(list);
        this.click = click;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_languages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvLanguage.setText(filteredLanguageList.get(position).getName());
        holder.ivFlag.setImageResource(filteredLanguageList.get(position).getImage());

        // Set the selected state of radio button based on the position
        holder.rbLanguage.setChecked(position == selectedItem);

        holder.rbLanguage.setOnClickListener(v -> {
            selectedItem = holder.getAdapterPosition();
            notifyDataSetChanged(); // Update UI to reflect the changes
        });

        holder.cvLanguage.setOnClickListener(view -> {
            holder.rbLanguage.setChecked(position == selectedItem);
            click.onLanguageClick(filteredLanguageList.get(position).getName());
        });
    }

    @Override
    public int getItemCount() {
        return filteredLanguageList.size();
    }

    public void filter(String text) {
        filteredLanguageList.clear();
        if (text.isEmpty()) {
            filteredLanguageList.addAll(list);
        } else {
            for (LanguageModel language : list) {
                if (language.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredLanguageList.add(language);
                }
            }
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLanguage;
        RadioButton rbLanguage;
        ImageView ivFlag;
        CardView cvLanguage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFlag = itemView.findViewById(R.id.ivCountryFlag);
            rbLanguage = itemView.findViewById(R.id.rbLanguage);
            tvLanguage = itemView.findViewById(R.id.tvLanguage);
            cvLanguage = itemView.findViewById(R.id.cvLanguage);
        }
    }

    public interface click{
        void onLanguageClick(String language);
    }

}