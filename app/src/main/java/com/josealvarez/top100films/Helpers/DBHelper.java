package com.josealvarez.top100films.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alvar on 01/09/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    private Context context;
    private String createTables;


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        createTables = "CREATE TABLE Films(" +
                "id INTEGER NOT NULL," +
                "title TEXT NOT NULL," +
                "director TEXT NOT NULL," +
                "category TEXT NOT NULL," +
                "summary TEXT NOT NULL," +
                "picture TEXT NOT NULL," +
                "price TEXT NOT NULL," +
                "favourite INTEGER," +
                "CONSTRAINT PKFavourites PRIMARY KEY (id)," +
                "CONSTRAINT UQFavourites UNIQUE (title,director,summary))";

        sqLiteDatabase.execSQL(createTables);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
