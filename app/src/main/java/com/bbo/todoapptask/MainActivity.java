package com.bbo.todoapptask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ListView todoList;
    TodoAdapter adapter;
    ArrayList<Todo> todos;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoList = findViewById(R.id.todo_List);
        add = findViewById(R.id.add);


        TodoJsonFetch fetch = new TodoJsonFetch();
        try {
            todos = fetch.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        adapter = new TodoAdapter(MainActivity.this, todos, "Main");
        todoList.setAdapter(adapter);
        String title = getIntent().getStringExtra("addedTitle");
        Boolean completed = getIntent().getBooleanExtra("addedCompleted", false);
        if(!(title == null)) {
            Todo todo = new Todo(title, todos.get(todos.size() - 1).getId() + 1, completed);
            todos.add(0, todo);
            adapter.notifyDataSetChanged();
        }
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("title", todos.get(position).getTitle());
                intent.putExtra("isCompleted", todos.get(position).getCompleted());
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
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

    private class TodoJsonFetch extends AsyncTask<Void, Void, ArrayList<Todo>> {

        @Override
        protected ArrayList<Todo> doInBackground(Void... voids) {
            try {
                URL url = new URL("https://jsonplaceholder.typicode.com/todos");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder output = new StringBuilder();
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String line = reader.readLine();

                    while (line != null) {
                        output.append(line);
                        line = reader.readLine();
                    }
                }
                String jsonResponse = output.toString();
                if (urlConnection != null) urlConnection.disconnect();
                if (inputStream != null) inputStream.close();
                JSONArray baseJsonResponse = new JSONArray(jsonResponse);
                todos = new ArrayList<>();
                for (int i = 0; i < baseJsonResponse.length(); i++) {
                    JSONObject first = baseJsonResponse.getJSONObject(i);
                    int id = first.getInt("id");
                    String title = first.getString("title");
                    Boolean completed = first.getBoolean("completed");
                    todos.add(new Todo(title, id, completed));
                }
                return todos;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }


}
