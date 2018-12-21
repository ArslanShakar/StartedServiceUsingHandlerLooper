package com.practice.coding.startedserviceusinghandlerlooper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBarHorizontal);
        tv = findViewById(R.id.textView);

    }

    public void runService(View view) {
        displayProgresBar(true);
        tv.append("\nCode Running...");
        for (String song : Playlist.songs)
        {
            /*
            For every song intent executed and send msg to to StartedService class..like 3 songs 3 times intent executed and send message..
             */
            Intent intent = new Intent(this, MyStartedService.class);
            intent.putExtra(Constants.MESSAGE_KEY, song);

            startService(intent); //here Service Start and startService take intent parameter..
            //this intent is reveived in StartedService Java Class in onStartCommand..
        }
    }

    public void displayProgresBar(boolean display)
    {
        if(display)
        {
            progressBar.setVisibility(View.VISIBLE);
        }else
        {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void stopService(View view) {
        /*
        stopService method shut down the service completely.
        */
        Intent intent = new Intent(this, MyStartedService.class);
        stopService(intent);
    }
}