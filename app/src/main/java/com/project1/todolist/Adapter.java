package com.project1.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    Context context;
    ArrayList<String> array ;

    public Adapter(Context context, ArrayList<String> array){
        this.context = context ;
        this.array = array;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.todolistview,parent,false);
        viewHolder viewH = new viewHolder(v);
        return viewH ;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.ctext.setText(array.get(position));
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CheckBox ctext ;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ctext = itemView.findViewById(R.id.checkbox) ;

        }
    }


}
