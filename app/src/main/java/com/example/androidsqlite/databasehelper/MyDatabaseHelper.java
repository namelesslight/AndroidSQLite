package com.example.androidsqlite.databasehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String createBookTable="create table Book("+
            "id integer primary key autoincrement,"+
            "author text,"+
            "price real,"+
            "pages integer,"+
            "name text)";
    private static final String createCategoryTable="create table Category("+
            "id integer primary key autoincrement,"+
            "category_name text,"+
            "category_code integer)";
    private Context mContext;
    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createBookTable);
        sqLiteDatabase.execSQL(createCategoryTable);
        Toast.makeText(mContext, "create table success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Book");
        sqLiteDatabase.execSQL("drop table if exists Category");
        onCreate(sqLiteDatabase);
    }
}
