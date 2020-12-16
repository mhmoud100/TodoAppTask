package com.bbo.todoapptask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SqlActivity extends AppCompatActivity {
    TodoAdapter adapter;
    ListView todoList;
    ArrayList<Todo> todos;
    FloatingActionButton add;
    TodosDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        todoList = findViewById(R.id.todo_list);
        add = findViewById(R.id.add);
        database = new TodosDatabase(this);
        todos = new ArrayList<>();
        todos = database.getTodosData();
        adapter = new TodoAdapter(this, todos, "Sql");
        todoList.setAdapter(adapter);


        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SqlActivity.this, InfoActivity.class);
                intent.putExtra("title", todos.get(position).getTitle());
                intent.putExtra("isCompleted", todos.get(position).getCompleted());
                intent.putExtra("id", todos.get(position).getId());
                intent.putExtra("name", "Sql");
                startActivity(intent);
            }
        });

        todoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final AlertDialog alertDialog = new AlertDialog.Builder(SqlActivity.this).create();
                alertDialog.setTitle(getString(R.string.deleteitem));
                alertDialog.setCancelable(true);
                alertDialog.setMessage(getString(R.string.message));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.deleteTodo(String.valueOf(todos.get(position).getId()));
                        todos.remove(position);
                        adapter.notifyDataSetChanged();

                    }

                });
                alertDialog.show();
                return true;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SqlActivity.this, AddActivity.class);
                intent.putExtra("name", "Sql");
                startActivity(intent);
            }
        });





    }

    @Override
    protected void onResume() {
        super.onResume();
        todos = database.getTodosData();
        adapter = new TodoAdapter(this, todos, "Sql");
        todoList.setAdapter(adapter);
    }
}
//database.updateTodo(String.valueOf(todos.get(position).getId()), todos.get(position).getTitle(), !todos.get(position).getCompleted());
//                todos.get(position).setCompleted(!todos.get(position).getCompleted());
//                adapter.notifyDataSetChanged();