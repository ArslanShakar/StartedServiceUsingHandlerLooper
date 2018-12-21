package com.practice.coding.startedserviceusinghandlerlooper;

import android.os.Looper;

public class DownloadThread extends Thread {
    public DownloadHandler mHandler;
    @Override
    public void run() {
        Looper.prepare(); //create message queue of download thread.
        mHandler = new DownloadHandler();
        /*we must attached the handler with one thread.so when here we initialized the handler
        then this handler is attached with this thread.. Simply in which thread initialized the handler , you want to attached the
         handler with that thread*/
        Looper.loop(); //looper loop on that queue
    }
}
