package com.bbo.todoapptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText title;
    CheckBox  completed;
    Button next, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        title = findViewById(R.id.todoTitle);
        completed = findViewById(R.id.todoCheckbox);
        next = findViewById(R.id.next);
        back = findViewById(R.id.back);
        TodosDatabase database = new TodosDatabase(this);
        String name = getIntent().getStringExtra("name");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().trim().equals("")) {
                    Toast.makeText(AddActivity.this, "put some data", Toast.LENGTH_SHORT).show();
                } else {
                    if (!(name == null)){
                        database.insertNewTodo(title.getText().toString(), completed.isChecked());
                        AddActivity.this.finish();
                    } else {
                        Intent intent = new Intent(AddActivity.this, MainActivity.class);
                        intent.putExtra("addedTitle",title.getText().toString());
                        intent.putExtra("addedCompleted", completed.isChecked());
                        startActivity(intent);
                    }

            }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActivity.this.finish();
            }
        });

    }
}