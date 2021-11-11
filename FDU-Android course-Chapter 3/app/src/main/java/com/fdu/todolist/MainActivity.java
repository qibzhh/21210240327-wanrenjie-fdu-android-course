package com.fdu.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private  SearchAdapter mSearchAdapter=new SearchAdapter();

    SQLiteOpenHelper mDbHelper = new FeedReaderDbHelper(MainActivity.this);
    private List<Note> outputList=new ArrayList<>();
    private  Button btn_delete;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), NewListActivity.class);
                startActivity(intent);
            }
        });


         List<Note> notes=query();

        mRecyclerView=findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(mSearchAdapter);
        mSearchAdapter.setOnItemClickListener(new SearchAdapter.onItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                getNew();
                Toast.makeText(MainActivity.this,"onClick"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                getNew();
                Toast.makeText(MainActivity.this,"onLongClick"+position,Toast.LENGTH_SHORT).show();
            }

        });
        mSearchAdapter.notifyItems(notes);

      //  mSearchAdapter.notifyItems(items);

        final Handler handler = new Handler();
         Runnable runnable = new Runnable() {
            public void run() {
                handler.postDelayed(this, 1000) ;// 间隔120秒
            }
        };
         runnable.run();
    }

    public  void getNew(){

//        List<Note> notes=query();

        List<Note> notes=query();
//        outputList.clear();
//        for (int i=0;i<notes.size();i++){
//            outputList.add(notes.get(i));
//
//        }
        mSearchAdapter.notifyItems(notes);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.update) {
            getNew();
            return true;
        }
        if (id == R.id.delete_database) {
            deleteDatabase("FeedReader.db");
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  List<Note> query() {
        String msg="";
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        String[] projection={
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_CHECK,
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        };
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE+" = ?";
        String[] selectionArgs={""};
        String sortOrder= FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE+" DESC";
        Cursor cursor=db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
       // List itemIds=new ArrayList<>();
        List<Note> notes=new ArrayList<>();
        while (cursor.moveToNext()){
            Note note=new Note();

           int id = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry._ID));
            int check = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_CHECK));
            String title = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
            String subtitle = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE));
    ;      note.id=id;
            note.title=title;
            note.subtitle=subtitle;
            note.check=check;
            notes.add(note);
            msg+=note.id+note.subtitle+note.title+ note.check;
        }
        cursor.close();
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
        return  notes;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNew();
    }


}
