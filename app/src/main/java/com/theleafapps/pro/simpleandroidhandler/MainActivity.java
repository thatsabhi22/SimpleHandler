package com.theleafapps.pro.simpleandroidhandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private static final int MY_MESSAGE_ID = 1;
    private static final String TAG = "MainActivity";
    //Since the handler is declared and defined in MainActivity,
    //this handler will be associated with this activity i.e. this handler is stuck with the
    //message queue of the main thread
    Handler handler;
    Thread thread;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        thread = new Thread(new MyThread());
        thread.start();

        handler = new Handler(){

            // On your UI thread's handler, you can then get the message and process it:
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MY_MESSAGE_ID:
                        progressBar.setProgress(msg.arg1);
                        break;

                    default:
                        Log.w(TAG, "Invalid message received: " + msg.what);
                        break;
                }
            }
        };
    }

    //this separate thread will send message to the main thread through handler.sendMessage()
    private class MyThread implements Runnable {

        @Override
        public void run() {
            for(int i = 0;i<100;i++){

                //the message is going to be send from this statement to the Handler in the main thread
                // the handler's handleMessage() method will handle the message send from here
                Message message =  handler.obtainMessage(MY_MESSAGE_ID, i, 0);
                message.sendToTarget();

                // handler.sendMessage(message) not worked
                // reason : The problem is the use of Message object.
                // It is a transient object, so once it has been sent to the Handler,
                // the background thread should no longer use it.
                // The receiving thread "owns" it at that point.

                //reference : https://stackoverflow.com/questions/42554809/android-thread-handler-error-illegalstateexception-the-specified-message-queue

                // To let things visible putting sleep of 1 sec
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}