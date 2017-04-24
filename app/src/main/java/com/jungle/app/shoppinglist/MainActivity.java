package com.jungle.app.shoppinglist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
//import com.jungle.app.shoppinglist.SlideCutListView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    // These are the Contacts rows that we will retrieve
    static final String[] PROJECTION = new String[] {ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME};

    // This is the select criteria
    static final String SELECTION = "((" +
            ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND (" +
            ContactsContract.Data.DISPLAY_NAME + " != '' ))";
    private SimpleCursorAdapter  la;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final SlideCutListView view = (SlideCutListView) findViewById(R.id.list);
        final SchemaHelper myDBHelper =  new SchemaHelper(this);
        final MainActivity self = this;
        final SQLiteDatabase db = myDBHelper.getWritableDatabase();
        //Cursor cursor = db.rawQuery("select * from bills",new String[]{});
        final Cursor cursor = db.query("bills",new String[]{"billItem_id as _id","name"},null,null,null,null,null);

        view.setRemoveListener(new SlideCutListView.RemoveListener(){
            @Override
            public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
                switch (direction) {
                    case RIGHT:
                        myDBHelper.delete(la.getItemId(position));
                        cursor.requery();
                        break;
                    case LEFT:
                        Toast.makeText(self, "向左删除  "+ position, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
        setSupportActionBar(toolbar);

//       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        //startManagingCursor(cursor);
        ListView list = (ListView)findViewById(R.id.list);
        la =  new SimpleCursorAdapter(this,android.R.layout.simple_expandable_list_item_1,cursor,new String[]{"name"},new int[]{android.R.id.text1},SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        //list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData()));
        list.setAdapter(la);
        final EditText goods = (EditText)findViewById(R.id.goods);
        goods.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
                    addOperation(goods, db, cursor);
                }
                return false;
            }
        });
        Button ok = (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               addOperation(goods,db,cursor);
            }
        });
        //getLoaderManager().initLoader(0, null, this);
    }
    private void addOperation(EditText goods,SQLiteDatabase db,Cursor cursor){
        String name = goods.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("bill_id","1");
        cv.put("date",new Date().getTime());
        db.insert("bills",null,cv);
        cursor.requery();
        //Snackbar.make(view, "输入商品名称："+name, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        goods.setText("");
    }
    private List<String> getData(){

        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");

        return data;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Called when a new Loader needs to be created
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.

        return new CursorLoader(this, ContactsContract.Data.CONTENT_URI,
                PROJECTION, SELECTION, null, null);
    }

    // Called when a previously created loader has finished loading
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        la.swapCursor(data);
    }

    // Called when a previously created loader is reset, making the data unavailable
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        la.swapCursor(null);
    }
}
