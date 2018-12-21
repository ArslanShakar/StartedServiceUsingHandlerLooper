package com.practice.coding.startedserviceusinghandlerlooper;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DownloadHandler extends Handler {

    private static final String TAG = "MyTag";
    @Override
    public void handleMessage(Message msg) {
        String songName = msg.obj.toString();
        downloadSong(songName);
    }

    private void downloadSong(String songName) {
        try {
            Log.d(TAG, "run : Start downloading...");
            Thread.sleep(3000);
            Log.d(TAG, songName + " : Downloaded.");
        } catch (InterruptedException e) {}

    }


}
