package com.bbo.todoapptask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView todoList;
    TodoAdapter adapter;
    ArrayList<Todo> todos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoList = findViewById(R.id.todo_List);
        todos = new ArrayList<>();
        todos.add(new Todo("title 1", "desc 1", false));
        todos.add(new Todo("title 2", "desc 2", false));
        todos.add(new Todo("title 3", "desc 3", false));
        todos.add(new Todo("title 4", "desc 4", true));
        todos.add(new Todo("title 5", "desc 5", false));
        todos.add(new Todo("title 6", "desc 6", false));
        todos.add(new Todo("title 7", "desc 7", true));
        todos.add(new Todo("title 8", "desc 8", false));
        todos.add(new Todo("title 9", "desc 9", false));
        todos.add(new Todo("title 10", "desc 10", false));
        todos.add(new Todo("title 11", "desc 11", true));
        adapter = new TodoAdapter(this, todos);
        todoList.setAdapter(adapter);

        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("title", todos.get(position).getTitle());
                intent.putExtra("desc", todos.get(position).getDesc());
                intent.putExtra("isCompleted", todos.get(position).getCompleted());
                startActivity(intent);
            }
        });

        todoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle(getString(R.string.deleteitem));
                alertDialog.setCancelable(true);
                alertDialog.setMessage(getString(R.string.message));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       todos.remove(position);
                       adapter.notifyDataSetChanged();

                    }

                });
                alertDialog.show();
                return true;
            }
        });


    }
}