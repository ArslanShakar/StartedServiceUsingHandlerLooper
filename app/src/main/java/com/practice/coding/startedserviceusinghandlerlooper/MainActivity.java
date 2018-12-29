package com.practice.coding.startedserviceusinghandlerlooper;

import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private ProgressBar progressBar;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBarHorizontal);
        tv = findViewById(R.id.textView);

        handler = new Handler();
    }

    public void runService(View view) {
        displayProgresBar(true);
        tv.append("\nCode Running...");

        ResultReceiver resultReceiver = new DownloadsResultReceiver(null);

        for (String song : Playlist.songs)
        {
            /*
            For every song intent executed and send msg to to StartedService class..like 3 songs 3 times intent executed and send message..
             */
            Intent intent = new Intent(this, MyStartedService.class);
            intent.putExtra(Constants.MESSAGE_KEY, song);
            intent.putExtra(Intent.EXTRA_RESULT_RECEIVER, resultReceiver);

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

    //Communicate the with UI through Result Receiver

    class DownloadsResultReceiver extends ResultReceiver
    {
        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public DownloadsResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(final int resultCode, final Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            if(resultCode == RESULT_OK && resultData != null)
            {
                final String songName = resultData.getString(Constants.MESSAGE_KEY);
                final boolean isTaskCompleted = resultData.getBoolean(Constants.PROGRESS_KEY);

                /*MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        tv.append("\n"+songName+ " : Donloaded Successfully." );

                        if(isTaskCompleted)
                        {
                            displayProgresBar(false);
                        }
                    }
                });*/

                //second way to update data on main ui
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.append("\n"+songName+ " : Donloaded Successfully." );

                        if(isTaskCompleted)
                        {
                            displayProgresBar(false);
                        }
                    }
                });
            }
        }
    }
}