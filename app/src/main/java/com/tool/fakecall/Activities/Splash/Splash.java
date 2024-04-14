package com.tool.fakecall.Activities.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.tool.fakecall.Activities.Languages.Languages;
import com.tool.fakecall.Activities.MainActivity.MainActivity;
import com.tool.fakecall.Common.SharedHelper;
import com.tool.fakecall.R;

public class Splash extends AppCompatActivity {

    private static final long SPLASH_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String language = SharedHelper.getString(this,"language",null);

        new Handler().postDelayed(() -> {

        if(language != null && !language.equals("")){
              Log.d("Language",language);
            new Languages().changeLanguage(this,language);
        }else{
            startActivity(new Intent(Splash.this, MainActivity.class));
            finish(); // Finish the splash activity so the user can't go back to it

        }
        }, SPLASH_DELAY);

    }
}