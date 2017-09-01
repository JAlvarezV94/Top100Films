package com.josealvarez.top100films.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.josealvarez.top100films.Helpers.DBHelper;
import com.josealvarez.top100films.Models.Film;

import java.util.ArrayList;

/**
 * Created by alvar on 01/09/2017.
 */

public class FavouritesDAO {

    //Const
    static final int VERSION = 1;
    static final String FAVOUTIRES_TABLE= "Favourites";
    static final String QUERY_FAVOUTIRES= "SELECT * FROM Favourites";

    //Attributes
    DBHelper dbHelper;
    SQLiteDatabase db;
    Context context;

    public FavouritesDAO(Context context){
        this.context = context;
        this.dbHelper = new DBHelper(context,"FilmsDB",null,VERSION);
    }


    public boolean insertFavourites(Film currentFilm){
        boolean inserted;
        ContentValues register;


        try{
            db = dbHelper.getWritableDatabase();
            register = new ContentValues();

            register.put("id",currentFilm.getIdFilm());
            register.put("title",currentFilm.getTittle());
            register.put("director",currentFilm.getDirector());
            register.put("category",currentFilm.getCategory());
            register.put("summary",currentFilm.getSinopsis());
            register.put("picture",currentFilm.getPicture());
            register.put("price",currentFilm.getPrice());
            db.insert(FAVOUTIRES_TABLE,null,register);
            inserted = true;
        }catch (SQLException e){
            inserted = false;
        }finally {
            if (db.isOpen())
                db.close();
        }

        return inserted;
    }

    public ArrayList<Film> readFavoutires(){
        ArrayList<Film> listFilm = new ArrayList<>();
        Cursor cursor;
        try{

            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(QUERY_FAVOUTIRES,null);

            if(cursor.moveToFirst()){
                do{
                    listFilm.add(new Film(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6)
                            ));

                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (SQLException e){

        }finally {
            if (db.isOpen())
                db.close();
        }

        return listFilm;
    }
}
