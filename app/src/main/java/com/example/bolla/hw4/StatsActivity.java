package com.example.bolla.hw4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    ProgressBar pb;
    TextView perc_text;
    Button quit_button, tryagain_button;
    ArrayList<Question> ques1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        getSupportActionBar().setDisplayShowHomeEnabled(Boolean.TRUE);
        getSupportActionBar().setTitle("Stats Activity");

        pb = (ProgressBar) findViewById(R.id.progressBar);
        perc_text = (TextView) findViewById(R.id.percTextView);
        quit_button = (Button) findViewById(R.id.quitButton);
        tryagain_button = (Button) findViewById(R.id.tryAgainButton);

        if(getIntent().getExtras().containsKey(TriviaActivity.COUNT_KEY) && getIntent().getExtras().containsKey(MainActivity.QUES_KEY)){
            pb.setMax(100);
            ques1 = (ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.QUES_KEY);
            int perc = (int) Math.round(getIntent().getExtras().getInt(TriviaActivity.COUNT_KEY)*(100.0)/ques1.size());
            pb.setProgress(perc);
            perc_text.setText(pb.getProgress()+"%");
        }
        else{
            Toast.makeText(StatsActivity.this,"No input data",Toast.LENGTH_SHORT).show();
        }

        quit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4 =new Intent(StatsActivity.this,MainActivity.class);
                startActivity(i4);
            }
        });

        tryagain_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5 = new Intent(StatsActivity.this,TriviaActivity.class);
                i5.putExtra(MainActivity.QUES_KEY,ques1);
                startActivity(i5);
            }
        });



    }
}
