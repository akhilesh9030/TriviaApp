package com.example.bolla.hw4;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by bolla on 9/22/2016.
 */
public class Question implements Serializable {
    private String id;
    private String text;
    private String imageUrl;
    private Choices ch;

    public static Question createQuestion(JSONObject js) throws JSONException {
        Question Q = new Question();

        Q.setId(js.getString("id"));
        Q.setText(js.getString("text"));

        if(js.has("image")){
            Q.setImageUrl(js.getString("image"));
        }
        else{
            Q.setImageUrl("");
        }


        JSONObject choicesJSONObject = js.getJSONObject("choices");

        Choices C = Choices.createChoice(choicesJSONObject);
        Q.setCh(C);
        return Q;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Choices getCh() {
        return ch;
    }

    public void setCh(Choices ch) {
        this.ch = ch;
    }


    @Override
    public String toString() {
        return "Question [id=" + id +", text=" + text + ", imageURL=" + imageUrl + ", Choices=" + ch.toString() + "] \n";
    }
}
