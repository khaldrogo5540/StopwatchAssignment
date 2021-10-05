package com.example.stopwatchassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    private TextView mTimeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
        {
            //Get the previous state of the stopwatch if the activity has been destroyed
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    //Saved the state of the stopwatch if it's about to be destroyed
    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    //if the activity is paused, stop the stopwatch
    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //if the activity is resumed, start the stopwatch again if it was running previously
    @Override
    protected void onResume()
    {
        super.onResume();
        if (wasRunning)
        {
            running = true;
        }
    }


    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    private void runTimer()
    {
        mTimeView = findViewById(R.id.time_view);
        //final Handler handler = new Handler();

        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()){
                    try{
                        int hours = seconds / 3600;
                        int minutes = (seconds % 3600) / 60;
                        int secs = seconds % 60;

                        //format the seconds into hours, minutes adn seconds
                        String time = String.format(Locale.getDefault(),
                                "%d:%02d:%02d",hours,
                                minutes, secs);

                        //Set the text view text
                        mTimeView.setText(time);

                        //if running is true, increment the seconds variable
                        if (running){
                            seconds++;
                        }
                        Thread.sleep(1000);

                    } catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    } catch(Exception e){}

                }
            }
        });
        myThread.start();
        
        /*
        //while using Handler
        //The post() method processes code without delay, so the code in the runnable
        //will run almost immediately
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                //format the seconds into hours, minutes adn seconds
                String time = String.format(Locale.getDefault(),
                                            "%d:%02d:%02d",hours,
                                                minutes, secs);

                //Set the text view text
                mTimeView.setText(time);

                //if running is true, increment the seconds variable
                if (running){
                    seconds++;
                }

                //Post the code again with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
       */

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}