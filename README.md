# Android使用SQLite

> 需要android11(API等级30)
>
> 官网下载android studio
>
> ([Download Android Studio and SDK tools  | Android Developers (google.cn)](https://developer.android.google.cn/studio))

## 项目的创建

> 本项目实现了
>
> 1. Android使用sqlite实现本地缓存
> 2. litepal的使用
>
> 3. 使用provider内容提供器来让其他可调用本项目的sqlite的本地数据

### 一、sqlite的使用

#### 1.1 新建项目

新建一个android项目,项目类型为Empty Activity，命名为AndroidSQLite

#### 1.2 创建MyDatabaseHelper

在gardle构建完成后在项目的app/src/java/com.example.androidsqlite的目录下创建一个helper包,在包中创建一个数据库帮助类MyDatabaseHelper类,MyDatabaseHelper类代码如下

```java
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
```

#### 1.3 创建数据接收类

在app/src/java/com.example.androidsqlite下再创建一个pojo包，在pojo包中创建一个Book类,Book类代码如下

```java
/**
 * @author ZCL
 * 数据接收类
 */
public class Book{
    private int id;
    private String name;
    private String author;
    private double price;
    private int pages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
```

#### 1.4创建布局

在app/src/java/res/layout目录下修改activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/createBtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:text="create"/>

    <Button
        android:id="@+id/insertBtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:text="insert"/>

    <Button
        android:id="@+id/updateBtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:text="update"/>
    <Button
        android:id="@+id/deleteBtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:text="delete"/>
    <Button
        android:id="@+id/queryBtn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:text="query"/>
</LinearLayout>
```

#### 1.5 实现代码

将app/src/java/com.example.androidsqlite包的MainActivity修改成如下代码

```java
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

import com.example.androidsqlite.helper.MyDatabaseHelper;

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
        dbHelper=new MyDatabaseHelper(this,"Library.db",null,1);
        //创建数据库
        createBtn.setOnClickListener(view -> dbHelper.getWritableDatabase());
        //添加数据
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
        //更新数据
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
        //删除数据
        deleteBtn.setOnClickListener(view -> {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            db.delete("Book",null,null);
            Log.d("delete","success");
        });
        //查询数据
        queryBtn.setOnClickListener(view -> {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            Cursor cursor=db.query("book",null,null,null,null,null,null);
            if (cursor.moveToFirst()){
                while (cursor.moveToNext()){
                    @SuppressLint("Range") String name =cursor
                        .getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") String author =cursor
                        .getString(cursor.getColumnIndex("author"));
                    @SuppressLint("Range") int pages =cursor
                        .getInt(cursor.getColumnIndex("pages"));
                    @SuppressLint("Range") double price =cursor
                        .getDouble(cursor.getColumnIndex("price"));
                    Log.d("MainActivity","book name is"+name);
                    Log.d("MainActivity","book author is"+author);
                    Log.d("MainActivity","book pages is"+pages);
                    Log.d("MainActivity","book price is"+price);
                }
            }
            cursor.close();
        });
    }
}
```

这是Android使用sqlite的基本代码

###二、litepal的使用

#### 2.1 导入java包

在app目录下的build.gradle文件的dependencies下添加如下代码后重新构建gradle

```
implementation 'org.litepal.android:java:3.0.0'
```

#### 2.2 继承LitePalSupport

让pojo包下的Book继承LitePalSupport

```java
package com.example.androidsqlite.pojo;

import org.litepal.crud.LitePalSupport;

/**
 * @author ZCL
 * 数据表接收类
 * ps:此处继承的LitePalSupport是需要使用litepal框架时必须继承的，正常使用sqlite并不需要
 */
public class Book extends LitePalSupport {
    private int id;
    private String name;
    private String author;
    private double price;
    private int pages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
```



#### 2.3 在res/layout新建一个second_test.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <Button
        android:id="@+id/createBtn"
        android:layout_gravity="center_horizontal"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="CREATE DATA"/>
    <Button
        android:id="@+id/insertBtn"
        android:layout_gravity="center_horizontal"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="INSERT DATA"/>
    <Button
        android:id="@+id/deleteBtn"
        android:layout_gravity="center_horizontal"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="DELETE DATA"/>
    <Button
        android:id="@+id/updateBtn"
        android:layout_gravity="center_horizontal"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="UPDATE DATA"/>
    <Button
        android:id="@+id/queryBtn"
        android:layout_gravity="center_horizontal"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="QUERY DATA"/>
</LinearLayout>
```

#### 2.4创建实现类

在com.example.androidsqlite目录下创建一个SecondTestActivity并在AndroidManifest.xml中注册

SecondTestActivity类

```java
import java.util.List;

/**
 * @author ZCL
 * 此处为使用litepal框架的数据增删改查
 */
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
```

在AndroidManifest.xml中注册

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.androidsqlite">
    <application
        android:name="org.litepal.LitePalApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AndroidSQLite">
        <provider
            android:name=".provider.DataBaseProvider"
            android:authorities="com.example.androidsqlite.provider"
            android:enabled="true"
            android:exported="true"></provider>
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name=".SecondTestActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

这样就可以使用litepal了，它与原先Android使用sqlite相比不需要DataBaseHelper类，使用更加方便

### 三、使用provider

> provider是内容提供者，可以使其他应用访问你的应用在本地的文件

#### 3.1 创建一个provider类

在在com.example.androidsqlite目录下创建一个provider包，在包下新建一个DatabaseProvider类代码如下

```java
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.example.androidsqlite.helper.MyDatabaseHelper;

/**
 * @author ZCL
 * 内容提供器,用于提供给其他应用用于远程读取数据
 */
public class DataBaseProvider extends ContentProvider {
    public static final int BOOK_DIR=0;
    public static final int BOOK_ITEM=1;
    //匹配url
    public static final String AUTHORITY="com.example.androidsqlite.provider";
    //在UriMatcher中添加uri和匹配成功后的返回值
    public static UriMatcher uriMatcher;
    private MyDatabaseHelper databaseHelper;
    //book表示数据表,无后缀可查询所有，有#表示查询单个数据
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

    /**
     * 初始化databaseHelper
     */
    @Override
    public boolean onCreate() {
        databaseHelper=new MyDatabaseHelper(getContext(),"Library.db",null,1);
        return true;
    }

    /**
     * 用于获取URI对象对应的MIME类型，所有的提供器必须提供
     * 格式:1.必须以vnd开头
     *     2.如果内容URI以路径结尾，则后接android.cursor.dir/,如果内容URI以id结尾,则后接android.cursor.item/
     *     3.最后接上vnd.<authority>.<path>
     * @param uri
     * @return
     */
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                return "vnd.android.cursor.dir/vnd.com.example.androidsqlite.provider.book";
        }
        return null;
    }
}
```

#### 3.2 注册Provider

在AndroidManifest.xml的application标签中注册你的provider

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.androidsqlite">

    <application
        android:name="org.litepal.LitePalApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.AndroidSQLite">
        <provider
            android:name=".provider.DataBaseProvider"
            android:authorities="com.example.androidsqlite.provider"
            android:enabled="true"
            android:exported="true"></provider>
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name=".SecondTestActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

然后其他项目就可以访问你的本地数据库了
测试provider的项目目录 https://github.com/namelesslight/TestAndroidProvider
