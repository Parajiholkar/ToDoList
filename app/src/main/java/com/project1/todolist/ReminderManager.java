package com.project1.todolist;


import static android.content.Context.ALARM_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class ReminderManager {
    private static final int REQUEST_CODE = 123;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    Context context ;

    public ReminderManager(Context context){
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
    }
    public void setTaskReminder(String taskName, String ReminderTime, long reminderTimeMillis) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("TaskName",taskName);
        intent.putExtra("ReminderTime",ReminderTime);
        alarmIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, reminderTimeMillis, alarmIntent);
    }
}
