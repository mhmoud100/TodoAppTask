package com.bbo.todoapptask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
    EditText todoTitle;
    CheckBox checkBox;
    Button back, next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TodosDatabase database = new TodosDatabase(this);
        String title = getIntent().getStringExtra("title");
        Boolean completed = getIntent().getBooleanExtra("isCompleted", false);
        String name = getIntent().getStringExtra("name");

        todoTitle = findViewById(R.id.todoTitle);
        checkBox = findViewById(R.id.todoCheckbox);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        todoTitle.setText(title);
        checkBox.setChecked(completed);
        if(name == null){
            next.setVisibility(View.GONE);
            todoTitle.setEnabled(false);
            checkBox.setClickable(false);
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              int id = getIntent().getIntExtra("id", 0);
              database.updateTodo(String.valueOf(id), todoTitle.getText().toString(), checkBox.isChecked());
              InfoActivity.this.finish();

            }
        });





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoActivity.this.finish();
            }
        });




    }
}