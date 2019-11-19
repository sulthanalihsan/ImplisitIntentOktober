package com.myapplication.made.implicitintentoktober;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_audio_manager)
    Button btnAudioManager;
    @BindView(R.id.btn_notification)
    Button btnNotification;
    @BindView(R.id.btn_wifi)
    Button btnWifi;
    @BindView(R.id.btn_email)
    Button btnEmail;
    @BindView(R.id.btn_sms)
    Button btnSms;
    @BindView(R.id.btn_telepon)
    Button btnTelepon;
    @BindView(R.id.btn_camera)
    Button btnCamera;
    @BindView(R.id.btn_browser)
    Button btnBrowser;
    @BindView(R.id.btn_alarm)
    Button btnAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_audio_manager, R.id.btn_notification, R.id.btn_wifi, R.id.btn_email, R.id.btn_sms, R.id.btn_telepon, R.id.btn_camera, R.id.btn_browser, R.id.btn_alarm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_audio_manager:
                startActivity(new Intent(MainActivity.this, AudioManagerActivity.class));
                break;
            case R.id.btn_notification:
                showNotification();
                break;
            case R.id.btn_wifi:
                startActivity(new Intent(MainActivity.this, WifiActivity.class));
                break;
            case R.id.btn_email:
                startActivity(new Intent(MainActivity.this, EmailActivity.class));
                break;
            case R.id.btn_sms:
                startActivity(new Intent(MainActivity.this, SmsActivity.class));
                break;
            case R.id.btn_telepon:
                startActivity(new Intent(MainActivity.this, TeleponActivity.class));
                break;
            case R.id.btn_camera:
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
                break;
            case R.id.btn_browser:
                Intent intentBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://imastudio.co.id"));
                startActivity(intentBrowser);
                break;
            case R.id.btn_alarm:
                startActivity(new Intent(MainActivity.this, AlarmActivity.class));
                break;
        }
    }

    void showNotification() {
        int NOTIFICATION_ID = 1;
        String CHANNEL_ID = "channel_1";
        String CHANNEL_NAME = "intent_channel";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://imastudio.co.id"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, intent, 0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("Notification Sederhana")
                .setContentText("Coba Notifikasi")
                .setSubText("Sub Text")
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            notificationBuilder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = notificationBuilder.build();
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                Log.d("requestCode ", "OK");
            }
        }
    }
}
