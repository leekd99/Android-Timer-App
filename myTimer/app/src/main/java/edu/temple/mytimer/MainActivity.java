package edu.temple.mytimer;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button stopButton;
    private Button lapButton;
    private Button resumeButton;
    private Button resetButton;

    private TextView timeDisplay;
    private ListView lapListDisplay;

    private ArrayList<String> lapTimes;
    private LapAdapter myAdapter;

    private myThread timerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find displays
        findDisplays();
        initializeUI();

        lapTimes = new ArrayList<>();
        myAdapter = new LapAdapter(this,R.layout.lap_element_list_view,lapTimes);
        lapListDisplay.setAdapter(myAdapter);

        //initially set buttons invisible
        initializeTimer();

        //Start button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                onStartPressed();
                timerThread = new myThread();
                timerThread.start();

            }//end onClick
        });//end setOnClick

        //Stop Button
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                onStopPressed();

                timerThread.pause();

            }//end onClick
        });//end setOnClick

        //Lap Button
        lapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                recordLap();

            }//end onClick
        });//end setOnClick

        //Resume Button
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                onResumePressed();

                timerThread.unPause();

            }//end onClick
        });//end setOnClick

        //Reset Button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                initializeTimer();
                lapTimes.clear();
                myAdapter.notifyDataSetChanged();
                timerThread.resetTimer();
                timerThread.close();

            }//end onClick
        });//end setOnClick

    }//end onCreate

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerThread.close();
    }//end onDestroy

    public void onStartPressed(){

        startButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.VISIBLE);
        lapButton.setVisibility(View.VISIBLE);
        resumeButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);

    }//end onStartPressed

    public void onStopPressed(){

        startButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.GONE);
        lapButton.setVisibility(View.GONE);
        resumeButton.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.VISIBLE);

    }//end onStopPressed

    public void recordLap(){

        //Toast.makeText(MainActivity.this, R.string.lap_button_pressed_msg, Toast.LENGTH_SHORT).show();
        //lapListDisplay.setVisibility(View.VISIBLE);

        lapTimes.add(timeDisplay.getText().toString());
        myAdapter.notifyDataSetChanged();

    }//end recordLap

    public void onResumePressed(){

        startButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.VISIBLE);
        lapButton.setVisibility(View.VISIBLE);
        resumeButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);

    }//end onResumePressed

    public void initializeTimer(){

        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
        lapButton.setVisibility(View.GONE);
        resumeButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);

    }//end onResetPressed

    public void initializeUI(){

        //set colors
        startButton.setBackgroundColor(Color.CYAN);
        startButton.setTextColor(Color.WHITE);

        stopButton.setBackgroundColor(Color.RED);
        stopButton.setTextColor(Color.WHITE);

        lapButton.setBackgroundColor(Color.CYAN);
        lapButton.setTextColor(Color.WHITE);

        resumeButton.setBackgroundColor(Color.GREEN);
        resumeButton.setTextColor(Color.WHITE);

        resetButton.setBackgroundColor(Color.CYAN);
        resetButton.setTextColor(Color.WHITE);

    }//end initializeUI

    public void findDisplays(){

        //find button resources
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        lapButton = findViewById(R.id.lap_button);
        resumeButton = findViewById(R.id.resume_button);
        resetButton = findViewById(R.id.reset_button);

        timeDisplay = findViewById(R.id.timeDisplay);
        lapListDisplay = findViewById(R.id.lapList);

    }//end findDisplays

    Handler displayHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            setTimerDisplay((String)msg.obj);

            return false;
        }//end handleMessage
    });//end displayHandler

    public void setTimerDisplay(String time){

        timeDisplay.setText(time);

    }//end setTimerDisplay

    private class myThread extends Thread {

        boolean threadRunning = false;
        boolean pause = false;
        Timer myTimer = new Timer();

        @Override
        public void run() {

            threadRunning = true;

            while (threadRunning ) {

                //check for timer limit
                if(myTimer.timerLimitReached()){
                    close();
                }//end check for timer limit

                while(pause){
                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }//end pause
                }//end pause

                //System.out.println(myTimer.formatToString());
                Message msg = Message.obtain();
                msg.obj = myTimer.formatToString();
                displayHandler.sendMessage(msg);
                myTimer.increment();

                //Have thread sleep after increment
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }//end try catch

            }//end while

        }//end run

        public void resetTimer(){
            myTimer.reset();

            Message msg = Message.obtain();
            msg.obj = myTimer.formatToString();
            displayHandler.sendMessage(msg);
            close();
        }//end resetTimer

        public void pause(){
            pause = true;
        }//end pause

        public void unPause(){
            pause = false;
        }//end unPause

        //method to close thread
        public void close(){
            threadRunning = false;
        }//end close

    }//end myThread

}//end MainActivity