package com.jungle.app.shoppinglist;

import android.database.Cursor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017/4/23.
 */

public class CursorHelper {
    private Cursor cursor;
    private Map<String,Integer> map = new HashMap<>();
    public CursorHelper(Cursor cursor) {
        this.cursor = cursor;
       String[] names = cursor.getColumnNames();
        for(String name : names){
            map.put(name,cursor.getColumnIndex(name));
        }
    }

    public String getString(String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        if(columnIndex == -1){
            return null;
        }
        return cursor.getString(columnIndex);
    }
    public byte[] getBlob(String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        if(columnIndex == -1){
            return null;
        }
        return cursor.getBlob(columnIndex);
    }
    public double getDouble(String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        if(columnIndex == -1){
            return 0;
        }
        return cursor.getDouble(columnIndex);
    }
    public float getFloat(String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        if(columnIndex == -1){
            return 0;
        }
        return cursor.getFloat(columnIndex);
    }
    public int getInt(String columnName){
        int columnIndex = cursor.getColumnIndex(columnName);
        if(columnIndex == -1){
            return 0;
        }
        return cursor.getInt(columnIndex);
    }
}
