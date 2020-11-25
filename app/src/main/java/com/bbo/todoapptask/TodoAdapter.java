package com.bbo.todoapptask;

import android.content.Context;
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


    public TodoAdapter(Context context, ArrayList<Todo> todoItems) {
        this.context = context;
        this.todoItems = todoItems;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.todo_layout, null,false);

        TextView textView = convertView.findViewById(R.id.todoTitle);
        TextView desc = convertView.findViewById(R.id.todoDesc);
        CheckBox checkBox = convertView.findViewById(R.id.todoCheckbox);
        Todo todo = todoItems.get(position);
        textView.setText(todo.getTitle());
        desc.setText(todo.getDesc());
        if (todo.getCompleted()){
            checkBox.setChecked(true);
        }else {
        checkBox.setChecked(false);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                todo.setCompleted(isChecked);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
