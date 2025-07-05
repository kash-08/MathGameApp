package com.company.mathgame;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Random;

public class GameDiv extends AppCompatActivity {

    TextView score;
    TextView life;
    TextView time;
    TextView question;
    EditText answer;
    Button ok;
    Button next;
    Random random=new Random();
    int num1;
    int num2;
    int userAnswer;
    int realAnswer;
    int userScore=0;
    int userLife=3;
    CountDownTimer timer;
    private static final long START_TIME_IN_MILLIS = 60000;
    Boolean timer_running;
    long time_left_in_millis=START_TIME_IN_MILLIS;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_div);
        score = findViewById(R.id.textViewScore);
        life = findViewById(R.id.textViewLife);
        time = findViewById(R.id.textViewTime);
        question = findViewById(R.id.textViewQuestion);
        answer = findViewById(R.id.editTextAnswer);
        ok = findViewById(R.id.buttonOk);
        next = findViewById(R.id.buttonNext);

        gameContinue();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userAnswer=Integer.valueOf(answer.getText().toString());
                pauseTimer();
                if(userAnswer==realAnswer){
                    userScore=userScore+10;
                    score.setText(""+userScore);
                    question.setText("Congratulations! Your answer is correct.");


                }else{
                    userLife=userLife-1;
                    life.setText(""+userLife);
                    question.setText("Sorry! Your answer is wrong.");

                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer.setText("");

                resetTimer();
                if(userLife<=0){
                    Toast.makeText(getApplicationContext(),"Game Over",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(GameDiv.this,Result.class);
                    i.putExtra("score",userScore);
                    startActivity(i);
                    finish();

                }else{
                    gameContinue();

                }


            }
        });
    }
    public void gameContinue(){
        num1=random.nextInt(100);
        num2=random.nextInt(100);
        realAnswer=num1/num2;




        question.setText(num1 +" / "+ num2);
        startTimer();

    }

    public void startTimer(){
        timer= new CountDownTimer(time_left_in_millis,1000) {
            @Override
            public void onTick(long l) {
                time_left_in_millis=l;
                updateText();

            }

            @Override
            public void onFinish() {
                timer_running=false;
                pauseTimer();
                resetTimer();
                updateText();
                userLife=userLife-1;
                life.setText(""+userLife);
                question.setText("Sorry! Time is up.");


            }
        }.start();
        timer_running=true;
    }
    public void updateText(){
        int second = (int) (time_left_in_millis/1000)%60;
        String time_left = String.format(Locale.getDefault(),"%02d",second);
        time.setText(time_left);


    }
    public void pauseTimer(){
        timer.cancel();
        timer_running=false;

    }
    public void resetTimer(){
        time_left_in_millis=START_TIME_IN_MILLIS;
        updateText();

    }
}