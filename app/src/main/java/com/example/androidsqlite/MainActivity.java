package com.example.androidsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidsqlite.databasehelper.MyDatabaseHelper;

public class MainActivity extends AppCompatActivity {
    Button createBtn;
    Button insertBtn;
    Button updateBtn;
    Button deleteBtn;
    Button queryBtn;
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createBtn=findViewById(R.id.createBtn);
        insertBtn=findViewById(R.id.insertBtn);
        updateBtn=findViewById(R.id.updateBtn);
        deleteBtn=findViewById(R.id.deleteBtn);
        queryBtn=findViewById(R.id.queryBtn);
        dbHelper=new MyDatabaseHelper(this,"BookStore.db",null,3);
        createBtn.setOnClickListener(view -> dbHelper.getWritableDatabase());
        insertBtn.setOnClickListener(view -> {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues value=new ContentValues();
            value.put("name","SAN HEN JING");
            value.put("author","TIAN SUO HAO ER");
            value.put("pages",114514);
            value.put("price",19198.10);
            db.insert("Book",null,value);
            value.clear();
            value.put("name","NEVER GONNA GIVE YOU UP");
            value.put("author","RICK");
            value.put("pages",100000);
            value.put("price",42.0);
            db.insert("Book",null,value);
            Toast.makeText(this, "insert success", Toast.LENGTH_SHORT).show();
        });
        updateBtn.setOnClickListener(view -> {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("price",10.0);
            db.update("Book"
                    ,values
                    ,"name=?"
                    ,new String[]{"NEVER GONNA GIVE YOU UP"});
            Log.d("update","success");
        });

        deleteBtn.setOnClickListener(view -> {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            db.delete("Book",null,null);
            Log.d("delete","success");
        });
        queryBtn.setOnClickListener(view -> {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            Cursor cursor=db.query("Book",null,null,null,null,null,null);
            if (cursor.moveToFirst()){
                do{
                    @SuppressLint("Range") String name =cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") String author =cursor.getString(cursor.getColumnIndex("author"));
                    @SuppressLint("Range") int pages =cursor.getInt(cursor.getColumnIndex("pages"));
                    @SuppressLint("Range") double price =cursor.getDouble(cursor.getColumnIndex("price"));
                    Log.d("MainActivity","book name is"+name);
                    Log.d("MainActivity","book author is"+author);
                    Log.d("MainActivity","book pages is"+pages);
                    Log.d("MainActivity","book price is"+price);
                }while (cursor.moveToNext());
            }
            cursor.close();
        });
    }
}