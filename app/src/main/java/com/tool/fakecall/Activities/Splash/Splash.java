package com.tool.fakecall.Activities.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.tool.fakecall.Activities.Languages.Languages;
import com.tool.fakecall.Activities.MainActivity.MainActivity;
import com.tool.fakecall.Base.BaseActivity;
import com.tool.fakecall.Common.SharedHelper;
import com.tool.fakecall.R;

public class Splash extends BaseActivity {

    private static final long SPLASH_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        String language = SharedHelper.getString(this,"language",null);

        new Handler().postDelayed(() -> {

        if(language != null && !language.equals("")){
              Log.d("Language",language);
            new Languages().changeLanguage(this,language);
        }else{
            startActivity(new Intent(Splash.this, MainActivity.class));
            finish();

        }
        }, SPLASH_DELAY);

    }
}