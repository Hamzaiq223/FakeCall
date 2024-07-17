package com.tool.fakecall.Activities.VideoCharacters;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tool.fakecall.Adapter.VCAdapter;
import com.tool.fakecall.Base.BaseActivity;
import com.tool.fakecall.Models.CharactersModel;
import com.tool.fakecall.R;
import com.tool.fakecall.databinding.ActivityVideoCharactersBinding;

import java.util.ArrayList;

public class VideoCharacters extends BaseActivity implements VCAdapter.click, View.OnClickListener {

    ActivityVideoCharactersBinding binding;
    ArrayList<CharactersModel> arrayList;
    VCAdapter videoCharacterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_characters);

        setStatusBarColor();

        arrayList = new ArrayList<>();
        arrayList.add(new CharactersModel("C Ronaldo","Ronaldo",R.drawable.c_ronaldo));
        arrayList.add(new CharactersModel("Leo Messi","Leo Messi",R.drawable.leo_messi));
        arrayList.add(new CharactersModel("Leo Messi","Leo Messi",R.drawable.leo_messi));
        arrayList.add(new CharactersModel("Leo Messi","Leo Messi",R.drawable.leo_messi));

        videoCharacterAdapter = new VCAdapter(this,arrayList,this::onItemClick,true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        binding.rvCharacters.setLayoutManager(gridLayoutManager);
        binding.rvCharacters.setAdapter(videoCharacterAdapter);

    }

    @Override
    public void onItemClick(CharactersModel charactersModel) {
        Toast.makeText(this, ""+charactersModel.getName(), Toast.LENGTH_SHORT).show();
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