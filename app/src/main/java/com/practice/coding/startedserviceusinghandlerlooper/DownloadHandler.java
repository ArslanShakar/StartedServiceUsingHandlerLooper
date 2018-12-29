package com.practice.coding.startedserviceusinghandlerlooper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

public class DownloadHandler extends Handler {

    private MyStartedService myStartedService;
    private ResultReceiver resultReceiver;
    private static final String TAG = "MyTag";
    @Override
    public void handleMessage(Message msg) {
        String songName = msg.obj.toString();
        downloadSong(songName);
       // myStartedService.stopSelf(); //when app is close it completely shutdowm the service, even work is completed or not
        //myStartedService.stopSelf(msg.arg1); //when the start id match with the most recent then service is shutdown, onDestroy called
        boolean result = myStartedService.stopSelfResult(msg.arg1);
        Log.d(TAG, "service stopSelf Result : "+result+" & start Id : "+ msg.arg1);

        Bundle bundle = new Bundle();
        bundle.putString(Constants.MESSAGE_KEY, songName);
        bundle.putBoolean(Constants.PROGRESS_KEY, result);
        resultReceiver.send(MainActivity.RESULT_OK, bundle);
    }

    private void downloadSong(String songName) {
        try {
            Log.d(TAG, "run : Start downloading...");
            Thread.sleep(5000);
            Log.d(TAG, songName + " : Downloaded.");
        } catch (InterruptedException e) {}

    }


    public void setMyStartedService(MyStartedService myStartedService) {
        this.myStartedService = myStartedService;
    }

    public void setResultReceiver(ResultReceiver resultReceiver) {
        this.resultReceiver = resultReceiver;
    }
}
