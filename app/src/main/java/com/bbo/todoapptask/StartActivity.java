package com.bbo.todoapptask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
    Button Sql, Json;
//    private static final String CHANNEL_ID = "CHANNEL_SAMPLE";
    private NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        notificationManager = NotificationManagerCompat.from(this);
        Timer();
        Sql = findViewById(R.id.sql);
        Json = findViewById(R.id.json);
        Sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, SqlActivity.class);
                startActivity(intent);
            }
        });

        Json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void Timer() {

        // for (long i = Time; i < 86400000; i = i += 60000) {

        new CountDownTimer(4000, 4000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ShowNotification();
                }


            }
        }.start();
        // }


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void ShowNotification() {

        Toast.makeText(this, "Time", Toast.LENGTH_SHORT).show();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }
        Intent intent = new Intent(StartActivity.this, SqlActivity.class);
        intent.putExtra("Comming", 1);

        // PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        Notification notification = new NotificationCompat.Builder(this, "my_channel_01")
                .setContentTitle("Example")
                .setContentText("Example Action")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(1, notification);



    }
}