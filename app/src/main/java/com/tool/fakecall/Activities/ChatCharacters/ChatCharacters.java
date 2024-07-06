package com.tool.fakecall.Activities.ChatCharacters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import com.tool.fakecall.Adapter.ChatCharacterAdapter;
import com.tool.fakecall.Models.CharactersModel;
import com.tool.fakecall.R;
import com.tool.fakecall.databinding.ActivityChatCharactersBinding;

import java.util.ArrayList;

public class ChatCharacters extends AppCompatActivity implements ChatCharacterAdapter.click, View.OnClickListener {

    ArrayList<CharactersModel> arrayList;

    ActivityChatCharactersBinding binding;

    ChatCharacterAdapter chatCharacterAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_characters);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat_characters);

        arrayList = new ArrayList<>();
        arrayList.add(new CharactersModel("C Ronaldo","Ronaldo",R.drawable.c_ronaldo));
        arrayList.add(new CharactersModel("Rose","Rose",R.drawable.rose));
        arrayList.add(new CharactersModel("Lisa","Lisa",R.drawable.lisa));
        arrayList.add(new CharactersModel("Spider Man","Taylor Swift",R.drawable.spider_man));

        chatCharacterAdapter  =  new ChatCharacterAdapter(this,arrayList,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        binding.rvChatCharacters.setLayoutManager(gridLayoutManager);
        binding.rvChatCharacters.setAdapter(chatCharacterAdapter);

    }

    @Override
    public void onCharacterClick(CharactersModel charactersModel) {

    }

    @Override
    public void onClick(View v) {

    }
}