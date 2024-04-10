package com.tool.fakecall.Activities.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.tool.fakecall.Activities.Languages.Languages;
import com.tool.fakecall.Adapter.ACAdapter;
import com.tool.fakecall.Adapter.ChatCharacterAdapter;
import com.tool.fakecall.Adapter.VCAdapter;
import com.tool.fakecall.Models.CharactersModel;
import com.tool.fakecall.R;
import com.tool.fakecall.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements VCAdapter.click,ACAdapter.click, ChatCharacterAdapter.click{

    ActivityMainBinding binding;

    ArrayList<CharactersModel> arrayList;

    VCAdapter vcAdapter;

    ACAdapter acAdapter;

    ChatCharacterAdapter chatCharacterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        arrayList = new ArrayList<>();
        arrayList.add(new CharactersModel("C Ronaldo","Ronaldo",R.drawable.c_ronaldo));
        arrayList.add(new CharactersModel("Rose","Rose",R.drawable.rose));
        arrayList.add(new CharactersModel("Lisa","Lisa",R.drawable.lisa));
        arrayList.add(new CharactersModel("Taylor Swift","Taylor Swift",R.drawable.taylor_swift));

        vcAdapter = new VCAdapter(this,arrayList,this);
        binding.rvVideoCall.setAdapter(vcAdapter);

        acAdapter = new ACAdapter(this,arrayList,this);
        binding.rvAudioCall.setAdapter(acAdapter);

        chatCharacterAdapter  =  new ChatCharacterAdapter(this,arrayList,this);
        binding.rvChat.setAdapter(chatCharacterAdapter);

        Locale locale;

        binding.btnCLanguage.setOnClickListener(view -> {
           startActivity(new Intent(this, Languages.class));
        });

    }



    @Override
    public void onItemClick(CharactersModel charactersModel) {

    }

    @Override
    public void onClick(CharactersModel charactersModel) {

    }

    @Override
    public void onCharacterClick(CharactersModel charactersModel) {

    }
}