package com.example.androidsqlite.provider;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.androidsqlite.databasehelper.MyDatabaseHelper;

public class DataBaseProvider extends ContentProvider {
    public static final int BOOK_DIR=0;
    public static final int BOOK_ITEM=1;
    public static final String AUTHORITY="com.example.androidsqlite.provider";
    public static UriMatcher uriMatcher;
    private MyDatabaseHelper databaseHelper;

    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",BOOK_DIR);
        uriMatcher.addURI(AUTHORITY,"book/#",BOOK_ITEM);
    }

    public DataBaseProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        int deleteRow=0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                deleteRow=db.delete("book",selection,selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId=uri.getPathSegments().get(1);
                deleteRow=db.delete("book","id=?",new String[]{bookId});
                break;
        }
        return deleteRow;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                return "vnd.android.cursor.dir/vnd.com.example.androidsqlite.provider.book";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Uri returnUri=null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBookId=db.insert("book",null,values);
                returnUri=Uri.parse("content://"+AUTHORITY+"/book/"+newBookId);
                break;
        }
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        databaseHelper=new MyDatabaseHelper(getContext(),"Library.db",null,1);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                cursor=db.query("book",projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;
            case BOOK_ITEM:
                String bookId=uri.getPathSegments().get(1);
                cursor=db.query("book", projection, "id=?",
                        new String[]{bookId}, null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        int updateRows=0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                updateRows=db.update("book",values,selection,selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId=uri.getPathSegments().get(1);
                updateRows=db.update("book",values,"id=?",new String[]{bookId});
                break;
        }
        return updateRows;
    }
}