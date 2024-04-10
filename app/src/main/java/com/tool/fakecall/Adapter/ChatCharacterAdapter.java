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
import com.tool.fakecall.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatCharacterAdapter extends RecyclerView.Adapter<ChatCharacterAdapter.ViewHolder> {

    private final Context context;
    private final List<CharactersModel> itemList;
    click click;

    public ChatCharacterAdapter(Context context, List<CharactersModel> itemList,click click1) {
        this.context = context;
        this.itemList = itemList;
        this.click = click1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userImage.setImageResource(itemList.get(position).getImage());
        holder.userName.setText(itemList.get(position).getName());

        holder.clCharacter.setOnClickListener(view -> {
            click.onCharacterClick(itemList.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userImage;
        TextView userName;
        ConstraintLayout clCharacter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.ivUserImage);
            userName = itemView.findViewById(R.id.tvUserName);
            clCharacter = itemView.findViewById(R.id.clCharacter);
        }
    }

    public interface click{
        void onCharacterClick(CharactersModel charactersModel);
    }
}