package com.tool.fakecall.Activities.VideoCharacters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.tool.fakecall.Adapter.VideoCharacterAdapter;
import com.tool.fakecall.Models.CharactersModel;
import com.tool.fakecall.R;
import com.tool.fakecall.databinding.ActivityVideoCharactersBinding;

import java.util.ArrayList;

public class VideoCharacters extends AppCompatActivity implements VideoCharacterAdapter.click {

    ActivityVideoCharactersBinding binding;
    ArrayList<CharactersModel> arrayList;
    VideoCharacterAdapter videoCharacterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_characters);

        arrayList = new ArrayList<>();
        arrayList.add(new CharactersModel("C Ronaldo","Ronaldo",R.drawable.c_ronaldo));
        arrayList.add(new CharactersModel("Leo Messi","Leo Messi",R.drawable.leo_messi));
        arrayList.add(new CharactersModel("Leo Messi","Leo Messi",R.drawable.leo_messi));
        arrayList.add(new CharactersModel("Leo Messi","Leo Messi",R.drawable.leo_messi));

        videoCharacterAdapter = new VideoCharacterAdapter(this,arrayList,this::onItemClick);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        binding.rvCharacters.setLayoutManager(gridLayoutManager);
        binding.rvCharacters.setAdapter(videoCharacterAdapter);
    }

    @Override
    public void onItemClick(CharactersModel charactersModel) {
        Toast.makeText(this, ""+charactersModel.getName(), Toast.LENGTH_SHORT).show();
    }
}