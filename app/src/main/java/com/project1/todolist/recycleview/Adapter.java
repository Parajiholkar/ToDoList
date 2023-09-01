package com.project1.todolist.recycleview;

import static android.app.DatePickerDialog.OnClickListener;
import static android.app.DatePickerDialog.OnDateSetListener;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.project1.todolist.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    Context context;
    ArrayList<RecycleViewModule> array ;
    public Adapter(Context context, ArrayList<RecycleViewModule> array){
        this.context = context ;
        this.array = array ;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.todolistview,parent,false);
        viewHolder viewHolder = new viewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.task.setText(array.get(position).Task);
        holder.tdate.setText(array.get(position).date);
        holder.ttime.setText(array.get(position).time);
        if(array.get(position).isComplete()){
            holder.completeimg.setVisibility(View.VISIBLE);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout update, delete ,complete;
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.rv_itemtab);
                try{
                    dialog.show();
                }catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                update = dialog.findViewById(R.id.update);
                delete = dialog.findViewById(R.id.delete);
                complete = dialog.findViewById(R.id.completed);

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        update(context,v,holder.getAdapterPosition());
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                                .setTitle("Delete Task")
                                .setMessage("Do you Want to Delete Task")
                                .setIcon(R.drawable.img_1)
                                .setPositiveButton("Yes", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            SharedPreferences pref = context.getSharedPreferences("taskList", context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = pref.edit();


                                            Gson gson = new Gson();
                                            array.remove(holder.getAdapterPosition());

                                            String arraystr1 = gson.toJson(array);
                                            editor.putString("arraylist", arraystr1);
                                            editor.apply();
                                            Toast.makeText(context, "Task Deleted Successfully", Toast.LENGTH_SHORT).show();

                                        }catch (Exception e){
                                            Toast.makeText(context.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        notifyItemChanged(holder.getAdapterPosition());
                                    }
                                })
                                .setNegativeButton("No", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        alert.show();
                        dialog.dismiss();



                    }
                });

                complete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                                .setTitle("Completed Task")
                                .setMessage("Congratulation, you have complete the Task \nDo you Want to Delete Task?")
                                .setIcon(R.drawable.img)
                                .setPositiveButton("Yes", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferences pref = context.getSharedPreferences("taskList", context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();


                                        Gson gson = new Gson();
                                        array.remove(holder.getAdapterPosition());

                                        String arraystr2 = gson.toJson(array);
                                        editor.putString("arraylist",arraystr2);
                                        editor.apply();
                                        notifyItemChanged(holder.getAdapterPosition());
                                        Toast.makeText(context, "Task Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferences pref = context.getSharedPreferences("taskList", context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();

                                        Gson gson = new Gson();
                                        array.get(holder.getAdapterPosition()).setComplete(true);
                                        String arraystr3 = gson.toJson(array);
                                        editor.putString("arraylist",arraystr3);
                                        editor.apply();
                                        holder.completeimg.setVisibility(View.VISIBLE);

                                    }
                                });
                        alert.show();



                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView task , ttime , tdate ;
        LinearLayout linearLayout;
        ImageView completeimg ;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.task = itemView.findViewById(R.id.tasktext);
            this.tdate = itemView.findViewById(R.id.dateText);
            this.ttime = itemView.findViewById(R.id.TimeText);
            this.linearLayout = itemView.findViewById(R.id.llv);
            this.completeimg = itemView.findViewById(R.id.completeimg);
        }
    }
    public void update(Context context,View v, int position){
        Dialog dialog = new Dialog(context);
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        dialog.setContentView(R.layout.custom_dialogbox);
        EditText task = dialog.findViewById(R.id.edttask);
        TextView date = dialog.findViewById(R.id.tdate);
        TextView time = dialog.findViewById(R.id.ttime);
        TextView addtext = dialog.findViewById(R.id.addtext);
        Button addbtn = dialog.findViewById(R.id.addbtn);
        addtext.setText(R.string.update_task);
        addbtn.setText(R.string.UPDATE);;
        task.setText(array.get(position).Task);
        date.setText(array.get(position).date);
        time.setText(array.get(position).time);
        dialog.show();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year, month, day;
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar1.set(Calendar.YEAR,year);
                        calendar1.set(Calendar.MONTH,month);
                        calendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        date.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar1.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour, minute;
                hour =calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE) ;

                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar1.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar1.set(Calendar.MINUTE,minute);
                        time.setText(DateFormat.getTimeInstance().format(calendar1.getTime()));
                    }
                },hour, minute,false);
                timePickerDialog.show();
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskstr, Date,Time;
                taskstr = task.getText().toString();
                Date = date.getText().toString();
                Time = time.getText().toString();
                if(!Objects.equals(taskstr, "")) {
                    if (!Objects.equals(Time, "Select Time")) {
                        if (!Date.equals("Select Date")) {
                            task.getText().clear();
                            date.setText(R.string.tdate);
                            time.setText(R.string.ttime);
                            SharedPreferences pref = context.getSharedPreferences("taskList",context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();

                            Gson gson = new Gson();
                            array.set(position,new RecycleViewModule(taskstr,Date,Time));

                            String arraystr4 = gson.toJson(array);
                            editor.putString("arraylist",arraystr4);
                            editor.apply();
                            dialog.dismiss();
                            notifyItemChanged(position);
                            Toast.makeText(context, "Task Update Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "Please Select Date!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(context, "Please Select Time!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, "Please Enter Task", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
