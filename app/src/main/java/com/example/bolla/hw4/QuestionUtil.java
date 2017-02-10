package com.example.bolla.hw4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bolla on 9/22/2016.
 */
public class QuestionUtil {
    static public class JSONParser{
        static ArrayList<Question> parseQuestions(String in) throws JSONException {
            ArrayList<Question> questionsList = new ArrayList<>();

            JSONObject root = new JSONObject(in);
            JSONArray questionJSOArray = root.getJSONArray("questions");

            for(int i=0;i<questionJSOArray.length();i++){
                JSONObject questionJSONObject = questionJSOArray.getJSONObject(i);

                Question Q = Question.createQuestion(questionJSONObject);
                questionsList.add(Q);
            }
            return questionsList;
        }
    }
}
