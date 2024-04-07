package com.tool.fakecall.Activities.Chat;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.tool.fakecall.Adapter.ChatAdapter;
import com.tool.fakecall.Adapter.ChatQuestionAdapter;
import com.tool.fakecall.Common.FlashlightController;
import com.tool.fakecall.Common.SharedHelper;
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
import java.util.List;

public class Chat extends AppCompatActivity implements ChatAdapter.AnswerSetListener,ChatQuestionAdapter.click {

    ChatQuestionAdapter chatQuestionAdapter;
    RecyclerView rvQuestions,rvChat;
    ChatAdapter chatAdapter;
    ArrayList<QuestionsAnswer> arrayList = new ArrayList<>();
    Boolean isAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        rvQuestions = findViewById(R.id.rvQuestions);
        rvChat = findViewById(R.id.rvChat);

        Context context = null; // Provide your Android context here
        int resourceId = R.raw.characters; // Replace "your_json_file" with the name of your JSON file in the res/raw folder
        String characterName = "Santa"; // Specify the character name for which you want to get questions and answers
        ArrayList<QuestionsModel.QuestionsAnswer> questionsAnswers = getQuestionsAnswersForCharacter(this, resourceId, characterName);

        chatQuestionAdapter = new ChatQuestionAdapter(this,questionsAnswers,this);
        rvQuestions.setAdapter(chatQuestionAdapter);

        chatAdapter = new ChatAdapter(arrayList,rvChat,rvQuestions,this);
        rvChat.setAdapter(chatAdapter);

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
    public void onItemClick(int position, List<QuestionsModel.QuestionsAnswer> list) {
        if(isAdded || arrayList.size() == 0){
            arrayList.add(new QuestionsAnswer(list.get(position).getQuestion(),list.get(position).getAnswer()));
            chatAdapter.notifyDataSetChanged();
            list.remove(position);
            chatQuestionAdapter.notifyDataSetChanged();
            int lastItemPosition = arrayList.size() - 1;
            rvChat.scrollToPosition(lastItemPosition);
            rvChat.smoothScrollToPosition(lastItemPosition);
            isAdded = false;
        }
    }

    @Override
    public void onAnswerSet(Boolean check) {
        isAdded = check;
        FlashlightController flashlightController = new FlashlightController(this);

        // To turn on the flashlight
        flashlightController.turnOnFlash();

        // To turn off the flashlight
        flashlightController.turnOffFlash();
    }
}