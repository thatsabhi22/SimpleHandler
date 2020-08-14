package com.theleafapps.pro.simpleandroidhandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    Thread thread;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thread = new Thread(new MyThread());

        progressBar = findViewById(R.id.progressBar);



    }

    private class MyThread implements Runnable {
        @Override
        public void run() {
            for(int i = 0;i<100;i++){

            }
        }
    }
}