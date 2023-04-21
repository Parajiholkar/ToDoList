package com.project1.todolist;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView todolist ;
    ArrayList<String> array = new ArrayList<>() ;
    FloatingActionButton fab ;
    Button addbtn ;
    String task ;
    EditText edttext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dialog dialog = new Dialog(this) ;
        dialog.setContentView(R.layout.custom_dialogbox);
        todolist = findViewById(R.id.recycleview) ;
        fab = findViewById(R.id.fab);
        addbtn = dialog.findViewById(R.id.addbtn) ;
        edttext = dialog.findViewById(R.id.edtText) ;
        todolist.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = edttext.getText().toString() ;
                edttext.getText().clear();
                array.add(task);
                Adapter Adapter1 = new Adapter(getApplicationContext(),array) ;
                todolist.setAdapter(Adapter1);
            }
        });




    }
}