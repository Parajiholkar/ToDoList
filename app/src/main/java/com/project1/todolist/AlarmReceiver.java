package com.project1.todolist;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        // Create notification intent
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 123, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Create notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "taskAlarm")
                .setSmallIcon(R.drawable.baseline_alarm_24)
                .setContentTitle(intent.getStringExtra("TaskName"))
                .setContentText(intent.getStringExtra("ReminderTime"))
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(123,builder.build());
    }
}

