package com.project1.todolist;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    TextView Edate,Etime ;
    int year,month,day, hour,minute ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dialog dialog = new Dialog(this) ; //dialog
        dialog.setContentView(R.layout.custom_dialogbox);
        recyclerView = findViewById(R.id.recycleview) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.fab);
        addbtn = dialog.findViewById(R.id.addbtn) ;
        task = dialog.findViewById(R.id.edttask) ;
        Edate = dialog.findViewById(R.id.tdate);
        Etime = dialog.findViewById(R.id.ttime);
        Calendar calendar = Calendar.getInstance();
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
                        Calendar dcalender = Calendar.getInstance();
                        dcalender.set(Calendar.YEAR,year);
                        dcalender.set(Calendar.MONTH,month);
                        dcalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        Edate.setText(DateFormat.getDateInstance(DateFormat.FULL).format(dcalender.getTime()));
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
                        if(hourOfDay<10 || minute<10){
                            if(hourOfDay<10 && minute<10){
                                Etime.setText("0"+hourOfDay+":"+"0"+minute);
                            } else if (minute<10) {
                                Etime.setText(hourOfDay+":"+"0"+minute);
                            }else {
                                Etime.setText("0"+hourOfDay+":"+minute);
                            }
                        }else {
                            Etime.setText(hourOfDay+":"+minute);
                        }
                    }
                },hour, minute,false);
                timePickerDialog.show();
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rtaskstr = task.getText().toString() ;
                Rtime = Etime.getText().toString();
                Rdate = Edate.getText().toString();
                if(!Objects.equals(Rtaskstr, "")) {
                    if (!Objects.equals(Rtime, "Select Time")) {
                        if (!Rdate.equals("Select Date")) {
                            task.getText().clear();
                            Edate.setText(R.string.tdate);
                            Etime.setText(R.string.ttime);
                            array.add(new RecycleViewModule(Rtaskstr,Rdate,Rtime));
                            Adapter adapter1 = new Adapter(MainActivity.this,array);
                            recyclerView.setAdapter(adapter1);
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
}