package com.example.androidsqlite.databasehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * 此处为数据库帮助类
 * @author ZCL
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    /**
     * sqlite创建语句
     */
    private static final String createBookTable="create table book("+
            "id integer primary key autoincrement,"+
            "author text,"+
            "price real,"+
            "pages integer,"+
            "name text)";
    private Context mContext;

    /**
     * 初始化
     * @param context
     * @param name
     * @param factory
     * @param version 每次更新数据库时需输入更大值
     */
    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    /**
     * 此处创建数据库可创建多次
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createBookTable);
    }

    /**
     * 增强数据库，可在此更改代码来更新数据库，需在构造方法的version输入比原先更大的数字才可更新，如把1改成2
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists book");
        onCreate(sqLiteDatabase);
    }
}
