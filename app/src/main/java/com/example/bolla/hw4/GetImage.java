package com.example.bolla.hw4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bolla on 9/25/2016.
 */
public class GetImage extends AsyncTask<String ,Void, Bitmap> {
    IData1 activity;
    String ques_id;

    public GetImage(IData1 activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        activity.showProgress();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        InputStream ip = null;
        ques_id = strings[1];
        try
        {
            URL u = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            ip = con.getInputStream();

            Bitmap image = BitmapFactory.decodeStream(ip);
            return image;
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(ip != null){
                try {
                    ip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        activity.setUpData(bitmap,ques_id);
    }

    static public interface IData1{
        public void setUpData(Bitmap bitmap, String ques_id);
        public void showProgress();
    }
}
