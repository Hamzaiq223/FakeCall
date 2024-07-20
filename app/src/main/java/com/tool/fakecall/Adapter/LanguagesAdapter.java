package com.tool.fakecall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tool.fakecall.Models.LanguageModel;
import com.tool.fakecall.R;
import java.util.ArrayList;
import java.util.List;

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.ViewHolder> {

    private Context context;
    private List<LanguageModel> languageList;
    private List<LanguageModel> originalLanguageList;
    private click listener;
    private String selectedLanguage;

    public LanguagesAdapter(Context context, List<LanguageModel> languageList, click listener, String selectedLanguage) {
        this.context = context;
        this.languageList = new ArrayList<>(languageList);
        this.originalLanguageList = new ArrayList<>(languageList);
        this.listener = listener;
        this.selectedLanguage = selectedLanguage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_languages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LanguageModel language = languageList.get(position);
        holder.languageName.setText(language.getName());
        holder.languageFlag.setImageResource(language.getImage());

        if (language.getName().equals(selectedLanguage)) {
            holder.ivTick.setVisibility(View.VISIBLE);
        } else {
            holder.ivTick.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            listener.onLanguageClick(language.getName());
            selectedLanguage = language.getName();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    public void filter(String text) {
        if (text.isEmpty()) {
            languageList.clear();
            languageList.addAll(originalLanguageList);
        } else {
            List<LanguageModel> filteredList = new ArrayList<>();
            for (LanguageModel item : originalLanguageList) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            languageList.clear();
            languageList.addAll(filteredList);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView languageName;
        ImageView languageFlag,ivTick;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            languageName = itemView.findViewById(R.id.tvLanguage);
            languageFlag = itemView.findViewById(R.id.ivCountryFlag);
            ivTick = itemView.findViewById(R.id.ivTick);
        }
    }

    public interface click {
        void onLanguageClick(String language);
    }
}
