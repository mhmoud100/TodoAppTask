package com.bbo.todoapptask;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class TodosDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todos.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "todos_data";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TODO_TITLE = "title";
    private static final String COLUMN_TODO_COMPLETED = "completed";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TODO_TITLE + " TEXT NOT NULL, " +
            COLUMN_TODO_COMPLETED + " TEXT NOT NULL)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public TodosDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
    public long insertNewTodo(String title, Boolean completed){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TODO_TITLE, title);

        contentValues.put(COLUMN_TODO_COMPLETED, (completed)? 1:0);
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }
    public int updateTodo(String id, String title, Boolean completed){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TODO_TITLE, title);
        contentValues.put(COLUMN_TODO_COMPLETED, (completed)? 1 : 0);
        return sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{id});
    }
    public int deleteTodo(String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
    }
    public ArrayList<Todo> getTodosData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID, COLUMN_TODO_TITLE, COLUMN_TODO_COMPLETED};
        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
        ArrayList<Todo> todos = new ArrayList();
        while(cursor.moveToNext()) {
            todos.add(new Todo(cursor.getString(1), Integer.parseInt(cursor.getString(0)),
                    (cursor.getInt(2) == 1)? true : false));
        }
        cursor.close();
        return todos;
    }   
}