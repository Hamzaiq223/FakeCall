package com.tool.fakecall.Activities.Languages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.tool.fakecall.Activities.MainActivity.MainActivity;
import com.tool.fakecall.Adapter.LanguagesAdapter;
import com.tool.fakecall.Base.BaseActivity;
import com.tool.fakecall.Common.SharedHelper;
import com.tool.fakecall.Models.LanguageModel;
import com.tool.fakecall.R;
import com.tool.fakecall.databinding.ActivityLanguagesBinding;

import java.util.ArrayList;
import java.util.Locale;

public class Languages extends BaseActivity implements LanguagesAdapter.click {

    ActivityLanguagesBinding binding ;
    ArrayList<LanguageModel> arrayList = new ArrayList<>();
    LanguagesAdapter languagesAdapter;
    SharedHelper sharedHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_languages);

        setStatusBarColor();

        arrayList.add(new LanguageModel("Arabic", R.drawable.qatar_flag));
        arrayList.add(new LanguageModel("Urdu", R.drawable.pakistan));
        arrayList.add(new LanguageModel("English", R.drawable.uk_flag));
        arrayList.add(new LanguageModel("Espanol", R.drawable.spain_flag));
        arrayList.add(new LanguageModel("German", R.drawable.german_flag));
        arrayList.add(new LanguageModel("Hausa", R.drawable.nigerian_flag));
        arrayList.add(new LanguageModel("Turkish", R.drawable.turkey_flag));
        arrayList.add(new LanguageModel("Indonesian", R.drawable.indonesian_flag));
        arrayList.add(new LanguageModel("Korean", R.drawable.korean_flag));
        arrayList.add(new LanguageModel("Japanese", R.drawable.japnese_flag));
        arrayList.add(new LanguageModel("Russian", R.drawable.russian_flag));
        arrayList.add(new LanguageModel("Irish", R.drawable.ireland_flag));
        arrayList.add(new LanguageModel("Finish", R.drawable.finish_flag));
        arrayList.add(new LanguageModel("Afrikaans", R.drawable.afrikan_flag));
        arrayList.add(new LanguageModel("Chinese", R.drawable.china_flag));
        arrayList.add(new LanguageModel("Portuguese", R.drawable.portuguese_flag));
        arrayList.add(new LanguageModel("Hindi", R.drawable.india));

        String savedLanguage = SharedHelper.getString(this, "language", "English");

        languagesAdapter = new LanguagesAdapter(this, arrayList, this, savedLanguage);
        binding.rvLanguages.setAdapter(languagesAdapter);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                languagesAdapter.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    public void onLanguageClick(String language) {
        changeLanguage(Languages.this, language);
        SharedHelper.saveString(this, "language", language);
    }

    public void changeLanguage(Context context, String language) {
        Locale locale;
        switch (language) {
            case "Urdu":
                locale = new Locale("ur");
                break;
            case "Espanol":
                locale = new Locale("es");
                break;
            case "Turkish":
                locale = new Locale("tr");
                break;
            case "Russian":
                locale = new Locale("ru");
                break;
            case "Portuguese":
                locale = new Locale("pt");
                break;
            case "Korean":
                locale = new Locale("ko");
                break;
            case "Japanese":
                locale = new Locale("ja");
                break;
            case "Italian":
                locale = new Locale("it");
                break;
            case "Indonesian":
                locale = new Locale("in");
                break;
            case "Hindi":
                locale = new Locale("hi");
                break;
            case "Hausa":
                locale = new Locale("ha");
                break;
            case "Irish":
                locale = new Locale("ga");
                break;
            case "Finish":
                locale = new Locale("fi");
                break;
            case "Arabic":
                locale = new Locale("ar");
                break;
            case "Afrikaans":
                locale = new Locale("af");
                break;
            case "German":
                locale = new Locale("de");
                break;
            case "Chinese":
                locale = new Locale("zh");
                break;
            // Add cases for other languages
            default:
                locale = new Locale("en");
                break;
        }

        // Change locale settings in the app
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, context.getResources().getDisplayMetrics());

        // Restart the app to apply language changes
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
