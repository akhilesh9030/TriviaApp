package com.example.bolla.hw4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadTriviaData.IData {
    ArrayList<Question> questions = new ArrayList<>();
    Button exit_button, start_button;
    TextView tv, loading_trivia;
    ImageView imgVw;
    ProgressDialog pd;
    ProgressBar pb;
    final static String QUES_KEY = "question_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(Boolean.TRUE);
        getSupportActionBar().setTitle("Trivia Quiz App");


        exit_button = (Button) findViewById(R.id.exitButton);
        start_button = (Button)findViewById(R.id.startTriviaButton);
        imgVw = (ImageView) findViewById(R.id.imageView);
        tv = (TextView) findViewById(R.id.textView);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        loading_trivia = (TextView) findViewById(R.id.loadingTriviaView);

        start_button.setEnabled(false);

        if(isConnectedOnline()){
            new LoadTriviaData(this).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");

            exit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            start_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this,TriviaActivity.class);
                    i.putExtra(QUES_KEY,questions);
                    startActivity(i);
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }
    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nw = cm.getActiveNetworkInfo();
        if(nw != null && nw.isConnected())
        {
            return true;
        }
        return false;
    }

    @Override
    public void setUpData(ArrayList<Question> ques) {
        questions = ques;
        pb.setVisibility(View.INVISIBLE);
        loading_trivia.setVisibility(View.INVISIBLE);
        imgVw.setVisibility(View.VISIBLE);

        start_button.setEnabled(true);
        imgVw.setImageResource(R.drawable.trivia);
        tv.setText("Trivia Ready");

    }

    @Override
    public void showProgress() {
        imgVw.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.VISIBLE);
        loading_trivia.setVisibility(View.VISIBLE);

    }
}
