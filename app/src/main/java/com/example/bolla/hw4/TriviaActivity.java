package com.example.bolla.hw4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity implements GetImage.IData1 {
    ArrayList<Question> ques;
    int currentQuesIndex = 0;
    TextView ques_num, time_left,ques_text,loading_text;
    ImageView imgVw;
    RadioGroup rg;
    Button quit_button,next_button;
    int num_of_correct_ques = 0;
    ProgressBar pb;


    final static String COUNT_KEY = "ques_correct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        getSupportActionBar().setDisplayShowHomeEnabled(Boolean.TRUE);
        getSupportActionBar().setTitle("Trivia Activity");

        ques_num = (TextView) findViewById(R.id.quesNumView);
        time_left = (TextView) findViewById(R.id.timeleftView);
        ques_text = (TextView) findViewById(R.id.quesView);
        imgVw = (ImageView) findViewById(R.id.imageView);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        quit_button = (Button) findViewById(R.id.quitButton);
        next_button = (Button) findViewById(R.id.nextButton);
        loading_text = (TextView) findViewById(R.id.loadingView);
        pb = (ProgressBar) findViewById(R.id.progressBar);




       
        if(getIntent().getExtras().containsKey(MainActivity.QUES_KEY)){
            if(getIntent().getExtras().getSerializable(MainActivity.QUES_KEY) != null){
                ques = (ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.QUES_KEY);

                ques_num.setText("Q1");
                if(ques.get(0).getImageUrl()!=""){
                    new GetImage(this).execute(ques.get(0).getImageUrl(),ques.get(0).getId());
                }
                ques_text.setText(ques.get(0).getText());
                updateRadioGroup();

                new CountDownTimer(120000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        time_left.setText("Time Left: " + millisUntilFinished / 1000 + " seconds");

                    }

                    public void onFinish() {
                        Log.d("demo","inside onFinish");
                        Toast.makeText(TriviaActivity.this,"timer over",Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(TriviaActivity.this,StatsActivity.class);
                        i2.putExtra(COUNT_KEY,num_of_correct_ques);
                        i2.putExtra(MainActivity.QUES_KEY,ques);
                        startActivity(i2);
                    }
                }.start();

                next_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(currentQuesIndex == ques.size()-1){
                            Intent i3 = new Intent(TriviaActivity.this,StatsActivity.class);
                            i3.putExtra(COUNT_KEY,num_of_correct_ques);
                            i3.putExtra(MainActivity.QUES_KEY,ques);
                            startActivity(i3);
                        }
                        else{
                            int child_index = rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));
                            if(ques.get(currentQuesIndex).getCh().getAnswer().equals(Integer.toString(child_index+1))){
                                Log.d("demo","inside equal sign");
                                num_of_correct_ques = num_of_correct_ques + 1;
                            }

                            currentQuesIndex = currentQuesIndex + 1;
                            int temp = currentQuesIndex + 1;

                            ques_num.setText("Q"+temp);


                            if(ques.get(currentQuesIndex).getImageUrl()!="") {
                                new GetImage(TriviaActivity.this).execute(ques.get(currentQuesIndex).getImageUrl(), ques.get(currentQuesIndex).getId());
                            }

                            ques_text.setText(ques.get(currentQuesIndex).getText());
                            updateRadioGroup();
                        }

                    }
                });

                quit_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i5 =new Intent(TriviaActivity.this,MainActivity.class);
                        startActivity(i5);
                    }
                });
            }
            else{
                Toast.makeText(TriviaActivity.this,"No Questions",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(TriviaActivity.this,"No Questions",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setUpData(Bitmap bitmap, String ques_id) {
        pb.setVisibility(View.INVISIBLE);
        loading_text.setVisibility(View.INVISIBLE);
        imgVw.setVisibility(View.VISIBLE);

        if(ques_id.equals(ques.get(currentQuesIndex).getId())){
            Log.d("demo","inside setup data");
            imgVw.setImageBitmap(bitmap);
        }

    }

    @Override
    public void showProgress() {

        imgVw.setVisibility(View.INVISIBLE);
        pb.setVisibility(View.VISIBLE);
        loading_text.setVisibility(View.VISIBLE);
        Log.d("demo","inside showProgress");
    }

    public void updateRadioGroup(){
        if(currentQuesIndex != 0){
            rg.removeAllViews();
            rg.clearCheck();
        }
        for(int i=0;i < ques.get(currentQuesIndex).getCh().getChoice().length;i++){
            RadioButton r =new RadioButton(this);
            r.setText(ques.get(currentQuesIndex).getCh().getChoice()[i]);
            r.setTag(Integer.parseInt("1"));
            rg.addView(r,i);
        }
    }
}
