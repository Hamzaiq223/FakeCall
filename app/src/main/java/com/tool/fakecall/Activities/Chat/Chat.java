package com.tool.fakecall.Activities.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.tool.fakecall.Adapter.ChatQuestionAdapter;
import com.tool.fakecall.Models.QuestionsAnswer;
import com.tool.fakecall.Models.QuestionsModel;
import com.tool.fakecall.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Chat extends AppCompatActivity implements ChatQuestionAdapter.click {

    ChatQuestionAdapter chatQuestionAdapter;
    RecyclerView rvQuestions;

    ArrayList<QuestionsAnswer> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        rvQuestions = findViewById(R.id.rvQuestions);
        Context context = null; // Provide your Android context here
        int resourceId = R.raw.characters; // Replace "your_json_file" with the name of your JSON file in the res/raw folder
        String characterName = "Santa"; // Specify the character name for which you want to get questions and answers
        ArrayList<QuestionsModel.QuestionsAnswer> questionsAnswers = getQuestionsAnswersForCharacter(this, resourceId, characterName);

        chatQuestionAdapter = new ChatQuestionAdapter(this,questionsAnswers,this);
        rvQuestions.setAdapter(chatQuestionAdapter);

    }

    public static ArrayList<QuestionsModel.QuestionsAnswer> getQuestionsAnswersForCharacter(Context context, int resourceId, String characterName) {
        ArrayList<QuestionsModel.QuestionsAnswer> questionsAnswers = new ArrayList<>();
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONObject jsonObject = new JSONObject(jsonString.toString());

            JSONArray charactersArray = jsonObject.getJSONArray("characters");
            for (int i = 0; i < charactersArray.length(); i++) {
                JSONObject characterObject = charactersArray.getJSONObject(i);
                String name = characterObject.getString("name");
                if (name.equalsIgnoreCase(characterName)) {
                    JSONArray questionsAnswersArray = characterObject.getJSONArray("questions_answers");
                    questionsAnswers = parseQuestionsAnswers(questionsAnswersArray);
                    break; // Stop searching once character is found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questionsAnswers;
    }

    private static ArrayList<QuestionsModel.QuestionsAnswer> parseQuestionsAnswers(JSONArray questionsAnswersArray) throws JSONException {
        ArrayList<QuestionsModel.QuestionsAnswer> questionsAnswers = new ArrayList<>();
        for (int i = 0; i < questionsAnswersArray.length(); i++) {
            JSONObject questionsAnswerObject = questionsAnswersArray.getJSONObject(i);
            QuestionsModel.QuestionsAnswer questionsAnswer = new QuestionsModel().new QuestionsAnswer();
            questionsAnswer.setQuestion(questionsAnswerObject.getString("question"));
            questionsAnswer.setAnswer(questionsAnswerObject.getString("answer"));
            questionsAnswers.add(questionsAnswer);
        }
        return questionsAnswers;
    }

    @Override
    public void onItemClick(QuestionsModel.QuestionsAnswer questionsAnswer) {
        arrayList.add(new QuestionsAnswer(questionsAnswer.getQuestion(),questionsAnswer.getAnswer()));
    }
}