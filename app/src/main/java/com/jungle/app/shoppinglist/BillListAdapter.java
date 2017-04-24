package com.jungle.app.shoppinglist;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by thinkpad on 2017/4/24.
 */


public class BillListAdapter extends BaseAdapter {

    private Context mContext;

    private Cursor mCursor;

    public BillListAdapter(Context context,Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override

    public long    getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView mTextView = new TextView(mContext);
        mCursor.moveToPosition(position);
        mTextView.setText(mCursor.getString(1) + "___"+ mCursor.getString(2));
        return mTextView;
    }
}


