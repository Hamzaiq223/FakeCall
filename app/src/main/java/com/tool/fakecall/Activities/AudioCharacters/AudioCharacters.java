package com.tool.fakecall.Activities.AudioCharacters;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tool.fakecall.Activities.AudioCall.AudioCall;
import com.tool.fakecall.Adapter.ACAdapter;
import com.tool.fakecall.Base.BaseActivity;
import com.tool.fakecall.Models.CharactersModel;
import com.tool.fakecall.R;
import com.tool.fakecall.databinding.ActivityAudioCharactersBinding;

import java.util.ArrayList;

public class AudioCharacters extends BaseActivity implements View.OnClickListener, ACAdapter.click {

    ActivityAudioCharactersBinding binding;

    ArrayList<CharactersModel> arrayList;
    ACAdapter acAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_audio_characters);

        setStatusBarColor();

        arrayList = new ArrayList<>();
        arrayList.add(new CharactersModel("C Ronaldo","Ronaldo",R.drawable.c_ronaldo));
        arrayList.add(new CharactersModel("Hashim Alma","Hashim Amla",R.drawable.hashim_amla));
        arrayList.add(new CharactersModel("Leo Messi","Messi",R.drawable.leo_messi));
        arrayList.add(new CharactersModel("Spider Man","Spider Man",R.drawable.spider_man));
        arrayList.add(new CharactersModel("Mr Bean","Mr Bean",R.drawable.mr_bean));
        arrayList.add(new CharactersModel("Ghost","Ghost",R.drawable.ghost));
        arrayList.add(new CharactersModel("Beiber","Beiber",R.drawable.justin_beiber));
        arrayList.add(new CharactersModel("Harry Potter","Harry",R.drawable.harry_potter));

        acAdapter  =  new ACAdapter(this,arrayList,this,true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        binding.rvAudioCharacters.setLayoutManager(gridLayoutManager);
        binding.rvAudioCharacters.setAdapter(acAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBack:
                finish();
                break;

        }
    }


    @Override
    public void onClick(CharactersModel charactersModel) {
        startActivity(new Intent(this, AudioCall.class).putExtra("character_name",charactersModel.getCode()).putExtra("character_image",charactersModel.getImage()));

    }
}