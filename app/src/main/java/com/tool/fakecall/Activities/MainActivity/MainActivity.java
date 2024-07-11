package com.tool.fakecall.Activities.MainActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.tool.fakecall.Activities.ChatCharacters.ChatCharacters;
import com.tool.fakecall.Activities.Languages.Languages;
import com.tool.fakecall.Adapter.ACAdapter;
import com.tool.fakecall.Adapter.ChatCharacterAdapter;
import com.tool.fakecall.Adapter.VCAdapter;
import com.tool.fakecall.Common.SharedHelper;
import com.tool.fakecall.Models.CharactersModel;
import com.tool.fakecall.R;
import com.tool.fakecall.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements VCAdapter.click,ACAdapter.click, ChatCharacterAdapter.click,View.OnClickListener{

    ActivityMainBinding binding;

    ArrayList<CharactersModel> arrayList;

    VCAdapter vcAdapter;

    ACAdapter acAdapter;

    ChatCharacterAdapter chatCharacterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
//                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }

        arrayList = new ArrayList<>();
        arrayList.add(new CharactersModel("C Ronaldo","Ronaldo",R.drawable.c_ronaldo));
        arrayList.add(new CharactersModel("Hashim Alma","Hashim Amla",R.drawable.hashim_amla));
        arrayList.add(new CharactersModel("Leo Messi","Messi",R.drawable.leo_messi));
        arrayList.add(new CharactersModel("Spider Man","Spider Man",R.drawable.spider_man));

        vcAdapter = new VCAdapter(this,arrayList,this);
        binding.rvVideoCall.setAdapter(vcAdapter);

        acAdapter = new ACAdapter(this,arrayList,this);
        binding.rvAudioCall.setAdapter(acAdapter);

        chatCharacterAdapter  =  new ChatCharacterAdapter(this,arrayList,this);
        binding.rvChat.setAdapter(chatCharacterAdapter);

        Locale locale;

        showCustomDialog();

        binding.btnCLanguage.setOnClickListener(view -> {
           startActivity(new Intent(this, Languages.class));
        });

    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();
        dialog.show();

//        Button btnOk = dialogView.findViewById(R.id.btn_ok);
//        btnOk.setOnClickListener(v -> dialog.dismiss());
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnChatCharacters:
                startActivity(new Intent(this, ChatCharacters.class));
                break;
        }
    }
}