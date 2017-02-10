package com.example.bolla.hw4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by bolla on 9/22/2016.
 */
public class Choices implements Serializable {
    private String[] choice;
    private String answer;

    public static Choices createChoice(JSONObject js2) throws JSONException {
        Choices C = new Choices();

        JSONArray choiceJSONArray = js2.getJSONArray("choice");
        String[] choice_array = new String[choiceJSONArray.length()];

        for (int i=0;i<choiceJSONArray.length();i++){
            choice_array[i] = choiceJSONArray.getString(i);
        }

        C.setChoice(choice_array);
        C.setAnswer(js2.getString("answer"));

        return C;
    }


    public String[] getChoice() {
        return choice;
    }

    public void setChoice(String[] choice) {
        this.choice = choice;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Choices [choice=" + Arrays.toString(choice) +", answer=" + answer + "] \n" ;
    }
}
