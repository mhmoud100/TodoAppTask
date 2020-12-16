package com.bbo.todoapptask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoAdapter  extends BaseAdapter {
    private ArrayList<Todo> todoItems;
    private Context context;
    private String name;
    TodosDatabase database;


    public TodoAdapter(Context context, ArrayList<Todo> todoItems, String name) {
        this.context = context;
        this.todoItems = todoItems;
        this.name = name;
    }

    @Override
    public int getCount() {
        return todoItems.size();
    }

    @Override
    public Object getItem(int position) {
        return todoItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.todo_layout, null,false);

        TextView title = convertView.findViewById(R.id.todoTitle);
        CheckBox completed = convertView.findViewById(R.id.todoCheckbox);
        Todo todo = todoItems.get(position);
        title.setText(todo.getTitle());
        completed.setChecked(todo.getCompleted());

            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(name.equals("Sql")) {
                        database = new TodosDatabase(context);
                        database.updateTodo(String.valueOf(todo.getId()), todo.getTitle(), isChecked);
                        todo.setCompleted(isChecked);
                    }else {
                        todo.setCompleted(isChecked);
                    }
                    Log.i("tag", todo.getCompleted().toString());
                }
            });

        return convertView;
    }
}
