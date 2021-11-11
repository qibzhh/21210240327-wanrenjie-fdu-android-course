package com.fdu.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;


public  final class FeedReaderContract {
    public   FeedReaderContract(){}

    public static class FeedEntry implements BaseColumns{
        public  static  final String TABLE_NAME="entry";
        public  static final   String COLUMN_NAME_CHECK="false";
        public  static final   String COLUMN_NAME_TITLE="title";
        public static  final String COLUMN_NAME_SUBTITLE="subtitle";
    }



}
