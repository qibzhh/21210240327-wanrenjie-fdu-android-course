package com.fdu.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

public class TextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    SQLiteOpenHelper mDbHelper;
    private CheckBox mCheckBox;
    private TextView mTextView;
    private TextView mTextView2;
    private  Button btn_delete;
    public TextViewHolder(@NonNull View itemView){
        super(itemView);
        mTextView=itemView.findViewById(R.id.text);
        mCheckBox=itemView.findViewById(R.id.checkbox);
        mTextView2=itemView.findViewById(R.id.text2);
     //   mTextView.setHeight(50);
        //itemView.setOnClickListener(this);

        btn_delete =itemView.findViewById(R.id.btn_delete);
        mDbHelper = new FeedReaderDbHelper(btn_delete.getContext());


    }


    public void bind(final Note note){

        mTextView.setText(note.title);
        mTextView2.setText(note.subtitle);
      //  mCheckBox.setChecked(true);

        if (note.check==1){
            mCheckBox.setChecked(true);
        }else{
            mCheckBox.setChecked(false);
        }
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete( note);
            //    Toast.makeText(btn_delete.getContext(),"已删除", Toast.LENGTH_SHORT).show();
            }
        });
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    update(note,isChecked);

            }
        });
    }

    @Override
    public void onClick(View v) {


//        Intent intent=new Intent(v.getContext(),NewListActivity.class);
//        intent.putExtra("extra",mTextView.getText());
//        v.getContext().startActivity(intent);
    }
    public  void delete( Note note){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        String selection = FeedReaderContract.FeedEntry._ID+"  = ?";

        String theId=""+note.id;
        String[] selectionArgs={theId};
        int deleteRows=db.delete(FeedReaderContract.FeedEntry.TABLE_NAME,selection,selectionArgs);
    }
    public  void update(Note note,boolean isChecked){
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
       // String title="check";
        ContentValues values=new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CHECK,isChecked);
        String selection = FeedReaderContract.FeedEntry._ID+"  = ?";
        String theId=""+note.id;
        String[] selectionArgs={theId};
        int count=db.update(FeedReaderContract.FeedEntry.TABLE_NAME,
                values ,
                selection,
                selectionArgs);
    }
}
