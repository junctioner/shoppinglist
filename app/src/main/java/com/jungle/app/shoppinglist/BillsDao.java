package com.jungle.app.shoppinglist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2017/4/23.
 */

public class BillsDao {
    private SchemaHelper myDBHelper;
    public String tag = "BillsDao.class";

    //在new出来的时候就实现myDBHelper初始化
    public BillsDao(Context context) {
        myDBHelper = new SchemaHelper(context);
    }

    //增加
    public void addPerson(String name, String billID) {
        SQLiteDatabase database = myDBHelper.getWritableDatabase();
        //先判断数据库是否可用
        if (database.isOpen()) {
            //执行插入操作
            //database.execSQL("insert into person (name,phone) values('"+name+"','"+phone+"')");

            //推荐如下写法
            database.execSQL("insert into bills (name,bill_id) values(?,?)", new Object[]{name, billID});
            database.close();
        }
    }

    //查找
    public boolean findPerson(String phone) {
        boolean result = false;
        SQLiteDatabase database = myDBHelper.getReadableDatabase();
        if (database.isOpen()) {
            //database.execSQL("select * from phone='"+phone+"'");

            Cursor cursor = database.rawQuery("select * from bills where phone=?", new String[]{phone});
            if (cursor.moveToFirst()) {//游标是否移动到下一行,如果是,那说明有数据返回
                Log.d(tag, "count:" + cursor.getColumnCount());
                int nameIndex = cursor.getColumnIndex("name");

                Log.d(tag, "name:" + cursor.getString(nameIndex));
                cursor.close();
                result = true;
            } else {
                result = false;

            }
            database.close();
        }
        return result;
    }

    //删除一条数据
    public void deletePerson(String phone) {
        SQLiteDatabase database = myDBHelper.getWritableDatabase();
        if (database.isOpen()) {
            database.execSQL("delete from person where billID=?", new Object[]{phone});
        }
        database.close();
    }

    //更新一条数据
    public void updatePerson(String billID, String newName) {
        SQLiteDatabase database = myDBHelper.getWritableDatabase();
        if (database.isOpen()) {
            database.execSQL("update person set name=? where billID=?", new Object[]{newName, billID});
        }
        database.close();
    }

    //查找所有person
    public List<Bill> findAllPerson(){
        List<Bill>  personList = new ArrayList<Bill>();
        SQLiteDatabase database = myDBHelper.getReadableDatabase();
        if(database.isOpen()){
            Cursor cursor = database.rawQuery("select * from bills ", null);
            while(cursor.moveToNext()){
                int nameIndex = cursor.getColumnIndex("name");
                int phoneIndex = cursor.getColumnIndex("bill_id");
                String name = cursor.getString(nameIndex);
                String billID = cursor.getString(phoneIndex);

                Bill  person = new Bill();
                person.setName(name);
                person.setBillID(billID);
                Log.d(tag,person.toString());

                personList.add(person);
            }

        }
        database.close();
        return personList;
    }
}
