package com.bbo.todoapptask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    TextView todoTitle, todoDesc;
    CheckBox checkBox;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        Boolean isCompleted = getIntent().getBooleanExtra("isCompleted", false);
        todoTitle = findViewById(R.id.todoTitle);
        todoDesc = findViewById(R.id.todoDesc);
        checkBox = findViewById(R.id.todoCheckbox);
        back = findViewById(R.id.back);
        todoTitle.setText(title);
        todoDesc.setText(desc);
        checkBox.setChecked(isCompleted);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoActivity.this.finish();
            }
        });




    }
}