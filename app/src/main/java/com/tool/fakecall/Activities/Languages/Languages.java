package com.tool.fakecall.Activities.Languages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.tool.fakecall.Activities.MainActivity.MainActivity;
import com.tool.fakecall.Adapter.LanguagesAdapter;
import com.tool.fakecall.Models.LanguageModel;
import com.tool.fakecall.R;
import com.tool.fakecall.databinding.ActivityLanguagesBinding;

import java.util.ArrayList;
import java.util.Locale;

public class Languages extends AppCompatActivity implements LanguagesAdapter.click {

    ActivityLanguagesBinding binding ;
    ArrayList<LanguageModel> arrayList = new ArrayList<>();
    LanguagesAdapter languagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_languages);

        arrayList.add(new LanguageModel("English",R.drawable.uk_flag));
        arrayList.add(new LanguageModel("Espanol",R.drawable.spain_flag));

        languagesAdapter = new LanguagesAdapter(this,arrayList,this);
        binding.rvLanguages.setAdapter(languagesAdapter);
    }

    @Override
    public void onLanguageClick(String language) {
        changeLanguage(language);
    }

    public void changeLanguage(String language){
        Locale locale;
        switch (language) {
            case "French":
                locale = new Locale("fr");
                break;
            case "Espanol":
                locale = new Locale("es");
                break;
            // Add cases for other languages
            default:
                locale = new Locale("en");
                break;
        }



        // Change locale settings in the app
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, getResources().getDisplayMetrics());

        // Restart the app to apply language changes
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}