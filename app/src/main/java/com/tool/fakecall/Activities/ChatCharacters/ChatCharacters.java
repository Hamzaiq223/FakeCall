package com.tool.fakecall.Activities.ChatCharacters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tool.fakecall.Activities.Chat.Chat;
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
        arrayList.add(new CharactersModel("Hashim Alma","Hashim Amla",R.drawable.hashim_amla));
        arrayList.add(new CharactersModel("Leo Messi","Messi",R.drawable.leo_messi));
        arrayList.add(new CharactersModel("Spider Man","Spider Man",R.drawable.spider_man));
        arrayList.add(new CharactersModel("Mr Bean","Mr Bean",R.drawable.mr_bean));
        arrayList.add(new CharactersModel("Ghost","Ghost",R.drawable.ghost));
        arrayList.add(new CharactersModel("Beiber","Beiber",R.drawable.justin_beiber));
        arrayList.add(new CharactersModel("Harry Potter","Harry",R.drawable.harry_potter));

        chatCharacterAdapter  =  new ChatCharacterAdapter(this,arrayList,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        binding.rvChatCharacters.setLayoutManager(gridLayoutManager);
        binding.rvChatCharacters.setAdapter(chatCharacterAdapter);

    }

    @Override
    public void onCharacterClick(CharactersModel charactersModel) {
        startActivity(new Intent(this, Chat.class).putExtra("character_name",charactersModel.getCode()).putExtra("character_image",charactersModel.getImage()));
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.ivBack:
                 finish();
                 break;

         }
    }
}