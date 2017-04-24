package com.jungle.app.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by thinkpad on 2017/4/23.
 */


public class SchemaHelper extends SQLiteOpenHelper {
    private static final String TAG = SchemaHelper.class.getSimpleName();
    // 数据库的名称
    private static final String name = "SHOPPINGLIST2";


    // 数据库的版本
    private static final int version = 2;

    public SchemaHelper(Context context) {
        super(context, name, null, version);

        Log.v(TAG, "构造器.....");
    }


    // 当数据 库第一次创建的时候 执行的方法
    @Override
    public void onCreate(SQLiteDatabase db) {

        // execSQL来挨靠 sql语句
        db.execSQL("create table bills(billItem_id integer primary key autoincrement,name varchar(20),bill_id varchar(16),date date)");

        Log.v(TAG, "onCreate...创建执行第一次。");
    }

    // 当版本发生变化则执行此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("alter table bills add account integer");

        Log.v(TAG, "每次更新时都执行.....");
    }

    public void delete(long id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String where = "billItem_id = ?";

        String[] whereValue = {Long.toString(id)};

        db.delete("bills", where, whereValue);
    }
}