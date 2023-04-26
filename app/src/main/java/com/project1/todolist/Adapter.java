package com.project1.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView task , ttime , tdate ;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.task = itemView.findViewById(R.id.tasktext);
            this.tdate = itemView.findViewById(R.id.dateText);
            this.ttime = itemView.findViewById(R.id.TimeText);
        }
    }
}
