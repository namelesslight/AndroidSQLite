package com.example.androidsqlite;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidsqlite.pojo.Book;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class SecondTestActivity extends AppCompatActivity {
    Button createBtn;
    Button insertBtn;
    Button deleteBtn;
    Button updateBtn;
    Button queryBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_test);
        createBtn=findViewById(R.id.createBtn);
        createBtn.setOnClickListener(view -> {
            Connector.getDatabase();
            Log.d("SecondTest","success");
        });

        insertBtn=findViewById(R.id.insertBtn);
        insertBtn.setOnClickListener(view -> {
            Book book1=new Book();
            book1.setName("1");
            book1.setAuthor("rick");
            book1.setPages(114514);
            book1.setPrice(19198.10);
            book1.save();
            Toast.makeText(SecondTestActivity.this,"succcess",Toast.LENGTH_SHORT).show();
        });

        updateBtn=findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(view -> {
            Book book=new Book();
            book.setPrice(10.10);
            book.updateAll("name=?","rick");
            Toast.makeText(SecondTestActivity.this,"succcess",Toast.LENGTH_SHORT).show();
        });

        deleteBtn=findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(view -> {
            LitePal.deleteAll(Book.class,"author=?","rick");
            Toast.makeText(SecondTestActivity.this,"succcess",Toast.LENGTH_SHORT).show();
        });

        queryBtn=findViewById(R.id.queryBtn);
        queryBtn.setOnClickListener(view -> {
            List<Book> list=LitePal.findAll(Book.class);
            for (Book book: list) {
                Log.d("SecondTestActivity",book.getName());
                Log.d("SecondTestActivity",book.getAuthor());
                Log.d("SecondTestActivity", String.valueOf(book.getPages()));
                Log.d("SecondTestActivity", String.valueOf(book.getPrice()));
            }
            Toast.makeText(SecondTestActivity.this,"succcess",Toast.LENGTH_SHORT).show();
        });
    }
}
