package com.e.marvelapiproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper{

    public static final String  _ID = "_id";
    public static final String  ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPITION = "descripition";
    public static final String PRICE = "price";
    public static final String PAGECOUNT = "pagecount";
    public static final String  URL = "url";

    public static final String DATABASE_NAME = "quadrinhosBD";
    public static final String PESSOAS_TABLE_NAME = "quadrinhos";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_DB_TABLE =
            " CREATE TABLE pessoas (_id INTEGER PRIMARY KEY AUTOINCREMENT,  title TEXT NOT NULL,  description TEXT NOT NULL, price TEXT, pagecount TEXT, url TEXT NOT NULL);";


        public Database(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  PESSOAS_TABLE_NAME);
            onCreate(db);
        }
    }

