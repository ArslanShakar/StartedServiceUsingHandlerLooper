package com.practice.coding.startedserviceusinghandlerlooper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

public class MyStartedService extends Service {

    private DownloadThread downloadThread;
    private static final String TAG = "MyTag";


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate Callled : ");
        downloadThread = new DownloadThread();
        downloadThread.start();

        while (downloadThread.mHandler == null) {
            /*
            untill handler is initialized in download thread the loop executing when handler initiaized then next code executed..
            Actually all service method run on main thread ... main thread pehly execute ho jata ha .. download thread ma execution ho rhi hoti
            r abi handler ko reference nahi milta thats why here exception occur..so use infinite loop u can say that..
            When handler initialized this condition will become false and execution move to next code means onStartCommand next executed
            */
        }

        downloadThread.mHandler.setMyStartedService(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ResultReceiver resultReceiver = intent.getParcelableExtra(Intent.EXTRA_RESULT_RECEIVER);
        downloadThread.mHandler.setResultReceiver(resultReceiver);
        String songName = intent.getStringExtra(Constants.MESSAGE_KEY);
        Log.d(TAG, "onStartCommand Callled : "+songName+" With Start Id : "+startId);

        /* handler Send the message to the MessageQueue by using the underlaying thread reference..means jis thread k sath handler attacted
        ho ga..os thread k reference sy hm handler ko access kry gy and Message/data-packet MessageQueue ma place kry gy..
        */

        Message message = Message.obtain();
        message.obj = songName;
        message.arg1 = startId;
        downloadThread.mHandler.sendMessage(message);

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //omBind does not call when we use started service
        Log.d(TAG, "onBind Called.");
        return null; //started service return nothing so here we require to implements this method so pass null
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy Called.");
    }


}
