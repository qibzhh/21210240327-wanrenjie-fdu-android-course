package com.fdu.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewListActivity extends AppCompatActivity {
    SQLiteOpenHelper mDbHelper = new FeedReaderDbHelper(NewListActivity.this);
    Button btn_add_list;
    EditText edit_text_add_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list);
        btn_add_list =(Button)findViewById(R.id.btn_add_list);
        edit_text_add_list=findViewById(R.id.edit_text_add_list);
        btn_add_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg= edit_text_add_list.getText().toString();
                Note note=new Note();
                note.title=msg;
                note.subtitle="";
                note.check=0;
               insert(note);
               String appPath = getApplicationContext().getExternalFilesDir("").getAbsolutePath();
               System.out.println(appPath);
             //  ("",appPath);
                Toast.makeText(btn_add_list.getContext(),appPath, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public  void insert(Note note){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CHECK,note.check);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,note.title);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE,note.subtitle);
        long newRowId=db.insert(FeedReaderContract.FeedEntry.TABLE_NAME,null,values);
        db.close();

    }
}
