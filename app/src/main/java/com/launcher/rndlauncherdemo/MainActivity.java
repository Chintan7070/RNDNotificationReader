package com.launcher.rndlauncherdemo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.launcher.rndlauncherdemo.services.MusicNotificationListener;
import com.launcher.rndlauncherdemo.utils.ConstantVal;

public class MainActivity extends AppCompatActivity {

    private TextView tvTakePermisison, tvStartService, tvStopService;
    private TextView tvPrevius, tvPlayPause, tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initIdes();

        tvTakePermisison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);

            }
        });

        tvStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent(MainActivity.this, MusicNotificationListener.class);
                startService(intent);*/

                Intent intent = new Intent(MainActivity.this, MusicNotificationListener.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                } else {
                    startService(intent);
                }
            }
        });


        tvStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MusicNotificationListener.class);
                stopService(intent);
            }
        });

        tvPrevius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConstantVal.notiMusicIsPlaying){
                    Intent pauseIntent = new Intent("com.android.music.musicservicecommand");
                    pauseIntent.putExtra("command", "pause");
                    sendBroadcast(pauseIntent);
                }else{
                    Intent playIntent = new Intent("com.android.music.musicservicecommand");
                    playIntent.putExtra("command", "play");
                    sendBroadcast(playIntent);
                }
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void initIdes() {
        tvTakePermisison = findViewById(R.id.tvTakePermisison);
        tvStartService = findViewById(R.id.tvStartService);
        tvStopService = findViewById(R.id.tvStopService);

        tvPrevius = findViewById(R.id.tvPrevius);
        tvPlayPause = findViewById(R.id.tvPlayPause);
        tvNext = findViewById(R.id.tvNext);
    }
}