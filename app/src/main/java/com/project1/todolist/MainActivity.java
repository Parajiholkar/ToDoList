package com.project1.todolist;


import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.project1.todolist.recycleview.Adapter;
import com.project1.todolist.recycleview.RecycleViewModule;
import com.project1.todolist.recycleview.WrapContentLinearLayoutManager;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    ArrayList<RecycleViewModule> array = new ArrayList<>() ;
    FloatingActionButton fab ;
    Button addbtn ;
    String Rtaskstr,Rdate,Rtime ;
    EditText task;
    CheckBox checkBox ;
    TextView Edate,Etime, notaskadded ;
    int year,month,day, hour,minute ;
    Calendar Rcalender ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dialog dialog = new Dialog(this) ; //dialog
        dialog.setContentView(R.layout.custom_dialogbox);
        recyclerView = findViewById(R.id.recycleview) ;
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        fab = findViewById(R.id.fab);
        notaskadded = findViewById(R.id.notaskadded);
        addbtn = dialog.findViewById(R.id.addbtn) ;
        task = dialog.findViewById(R.id.edttask) ;
        Edate = dialog.findViewById(R.id.tdate);
        Etime = dialog.findViewById(R.id.ttime);
        checkBox = dialog.findViewById(R.id.checkbox);
        Calendar calendar = Calendar.getInstance();
        Rcalender = Calendar.getInstance();
        try {
            loaddata();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // to Create Notification Channel
        createnotificationchannel();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        Edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Rcalender.set(Calendar.YEAR,year);
                        Rcalender.set(Calendar.MONTH,month);
                        Rcalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        Edate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(Rcalender.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        Etime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour =calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE) ;

                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Rcalender.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        Rcalender.set(Calendar.MINUTE,minute);
                        Etime.setText(DateFormat.getTimeInstance().format(Rcalender.getTime()));
//
                    }
                },hour, minute,false);
                timePickerDialog.show();
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size;
                Rtaskstr = task.getText().toString().trim() ;
                Rtime = Etime.getText().toString().trim();
                Rdate = Edate.getText().toString().trim();
                if(!Objects.equals(Rtaskstr, "")) {
                    if (!Objects.equals(Rtime, "Select Time")) {
                        if (!Rdate.equals("Select Date")) {
                            task.getText().clear();
                            Edate.setText(R.string.tdate);
                            Etime.setText(R.string.ttime);
                            try {
                                if(checkBox.isChecked()){
                                    ReminderManager reminderManager = new ReminderManager(MainActivity.this);
                                    reminderManager.setTaskReminder(Rtaskstr,Rtime+", "+Rdate,Rcalender.getTimeInMillis());
                                    Toast.makeText(MainActivity.this, "Reminder is set at "+ DateFormat.getDateTimeInstance(DateFormat.MEDIUM, TimeFormat.CLOCK_12H).format(Rcalender.getTime()), Toast.LENGTH_SHORT).show();
                                }
                                savedata(Rtaskstr,Rdate,Rtime) ;
                            }catch (Exception e){
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Please Select Date!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Please Select Time!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Please Enter Task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loaddata(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("taskList",MODE_PRIVATE);
        Gson gson = new Gson();
        String arraystr = pref.getString("arraylist",null);
        Type type = new TypeToken<ArrayList<RecycleViewModule>>(){}.getType();

        ArrayList<RecycleViewModule> arraylist1 = gson.fromJson(arraystr,type);

        if(arraylist1==null){
            notaskadded.setVisibility(View.VISIBLE);
        }else {
            notaskadded.setVisibility(View.GONE);
            array = arraylist1 ;
            Adapter adapter1 = new Adapter(MainActivity.this,array);
            recyclerView.setAdapter(adapter1);
        }
    }

    public void savedata(String rtaskstr, String rdate, String rtime) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("taskList",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


        Gson gson = new Gson();
        array.add(0,new RecycleViewModule(rtaskstr,rdate,rtime));

        String arraystr = gson.toJson(array);
        editor.putString("arraylist",arraystr);
        editor.apply();
        loaddata();

    }



    private void createnotificationchannel() {

        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            CharSequence name = "taskReminderChannel";
            String discription = "Channel for Task Reminder";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("taskAlarm",name,importance);
            notificationChannel.setDescription(discription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}