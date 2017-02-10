package com.example.bolla.hw4;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bolla on 9/22/2016.
 */
public class LoadTriviaData extends AsyncTask<String ,Void, ArrayList<Question>> {

    IData activity;

    public LoadTriviaData(IData activity){
        this.activity = activity;
    }


    @Override
    protected void onPreExecute() {
        activity.showProgress();
    }

    @Override
    protected ArrayList<Question> doInBackground(String... strings) {
        Log.d("demo", "in try 1");
        try
        {
            URL u = new URL(strings[0]);

            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int status_code = con.getResponseCode();

            if(status_code == HttpURLConnection.HTTP_OK) {
                StringBuilder s = new StringBuilder();
                String line = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    s.append(line + "\n");
                }

                reader.close();

                ArrayList<Question> temp = QuestionUtil.JSONParser.parseQuestions(s.toString());
                return temp;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        activity.setUpData(questions);

        if(questions != null){
            Log.d("demo","in if onpljost");
            Log.d("demo",questions.toString());
        }
        else{
            Log.d("demo","No Data retrieved");
        }

    }

    static public interface IData{
        public void setUpData(ArrayList<Question> ques);
        public void showProgress();
    }




}
