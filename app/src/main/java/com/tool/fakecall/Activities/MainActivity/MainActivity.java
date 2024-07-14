package com.tool.fakecall.Activities.MainActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

        binding.btnSettings.setOnClickListener(view -> {
            showSettingDialog(this);
        });

    }

    public void showSettingDialog(Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);
        dialogBuilder.setView(dialogView);

        AlertDialog ratingAlertDialog = dialogBuilder.create();
        if (ratingAlertDialog.getWindow() != null) {
            ratingAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        ratingAlertDialog.setCancelable(false);

        // Find views
//        ImageView ivVolume = dialogView.findViewById(R.id.ivVolume);
//        ImageView ivVibration = dialogView.findViewById(R.id.ivVibation);
//        ImageView ivFlash = dialogView.findViewById(R.id.ivFlash);
//        TextView tvLanguage = dialogView.findViewById(R.id.tvLanguage);
//        TextView tvRateUs = dialogView.findViewById(R.id.tvRateUs);
//        ImageView ivCross = dialogView.findViewById(R.id.ivCross);

        boolean volume = SharedHelper.getBoolean(this, "volume_off", false);
        boolean vibration = SharedHelper.getBoolean(this, "vibration_off", false);
        boolean flash = SharedHelper.getBoolean(this, "flash_off", false);

//        if (volume) {
//            ivVolume.setImageResource(R.drawable.sound_off);
//        }
//
//        if (vibration) {
//            ivVibration.setImageResource(R.drawable.vibrating_off);
//        }
//
//        if (flash) {
//            ivFlash.setImageResource(R.drawable.flash_off);
//        }

//        ivVolume.setOnClickListener(new View.OnClickListener() {
//            boolean volumeState = volume;
//            @Override
//            public void onClick(View v) {
//                if (volumeState) {
//                    ivVolume.setImageResource(R.drawable.icon_sound);
//                    SharedHelper.saveBoolean(context, "volume_off", false);
//                    volumeState = false;
//                } else {
//                    ivVolume.setImageResource(R.drawable.sound_off);
//                    SharedHelper.saveBoolean(context, "volume_off", true);
//                    volumeState = true;
//                }
//            }
//        });

//        ivVibration.setOnClickListener(new View.OnClickListener() {
//            boolean vibrationState = vibration;
//            @Override
//            public void onClick(View v) {
//                if (vibrationState) {
//                    ivVibration.setImageResource(R.drawable.ic_vibration);
//                    SharedHelper.saveBoolean(context, "vibration_off", false);
//                    vibrationState = false;
//                } else {
//                    ivVibration.setImageResource(R.drawable.vibrating_off);
//                    SharedHelper.saveBoolean(context, "vibration_off", true);
//                    vibrationState = true;
//                }
//            }
//        });

//        ivFlash.setOnClickListener(new View.OnClickListener() {
//            boolean flashState = flash;
//            @Override
//            public void onClick(View v) {
//                if (flashState) {
//                    ivFlash.setImageResource(R.drawable.ic_flash);
//                    SharedHelper.saveBoolean(context, "flash_off", false);
//                    flashState = false;
//                } else {
//                    ivFlash.setImageResource(R.drawable.flash_off);
//                    SharedHelper.saveBoolean(context, "flash_off", true);
//                    flashState = true;
//                }
//            }
//        });

//        ivCross.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ratingAlertDialog.dismiss();
//            }
//        });
//
//        tvLanguage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, Languages.class));
//                ratingAlertDialog.dismiss();
//            }
//        });

        ratingAlertDialog.show();
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