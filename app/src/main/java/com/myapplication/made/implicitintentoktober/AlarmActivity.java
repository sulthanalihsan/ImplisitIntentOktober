package com.myapplication.made.implicitintentoktober;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmActivity extends AppCompatActivity {

    @BindView(R.id.analog_clock)
    AnalogClock analogClock;
    @BindView(R.id.btn_set_alarm)
    Button btnSetAlarm;
    @BindView(R.id.tv_waktu_alarm)
    TextView tvWaktuAlarm;

    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
    }

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, i);
            calSet.set(Calendar.MINUTE, i1);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                calSet.add(Calendar.DATE, 1);
            } else if (calSet.compareTo(calNow) > 0) {
                Toast.makeText(AlarmActivity.this, "Alarm disetel", Toast.LENGTH_SHORT).show();
            }
            tvWaktuAlarm.setText("alarm diset untuk :" + calSet.getTime());

            Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 1, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
        }
    };

    @OnClick(R.id.btn_set_alarm)
    public void onViewClicked() {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);

        timePickerDialog.setTitle("set waktu alarm");
        timePickerDialog.show();
    }
}
