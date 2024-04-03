package com.tool.fakecall.Activities.Languages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.tool.fakecall.Adapter.LanguagesAdapter;
import com.tool.fakecall.Models.LanguageModel;
import com.tool.fakecall.R;
import com.tool.fakecall.databinding.ActivityLanguagesBinding;

import java.util.ArrayList;

public class Languages extends AppCompatActivity {

    ActivityLanguagesBinding binding ;
    ArrayList<LanguageModel> arrayList = new ArrayList<>();
    LanguagesAdapter languagesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_languages);

        arrayList.add(new LanguageModel("English",R.drawable.uk_flag));
        arrayList.add(new LanguageModel("Espanol",R.drawable.spain_flag));

        languagesAdapter = new LanguagesAdapter(this,arrayList);
        binding.rvLanguages.setAdapter(languagesAdapter);
    }
}