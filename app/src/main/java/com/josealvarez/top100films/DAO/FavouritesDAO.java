package com.josealvarez.top100films.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.josealvarez.top100films.Helpers.DBHelper;
import com.josealvarez.top100films.Models.Film;

import java.util.ArrayList;

/**
 * Created by alvar on 01/09/2017.
 */

public class FavouritesDAO {

    //Const
    static final int VERSION = 1;
    static final String FILMS_TABLE= "Films";
    static final String QUERY_FAVOUTIRES= "SELECT * FROM Films WHERE favourite = 1";
    static final String WHERE_CLAUSE = "title = '";
    //Attributes
    DBHelper dbHelper;
    SQLiteDatabase db;
    Context context;

    public FavouritesDAO(Context context){
        this.context = context;
        this.dbHelper = new DBHelper(context,"FilmsDB",null,VERSION);
    }


    public boolean verifyUpdates(ArrayList<Film> listFilm){
        boolean updatedTable;
        Cursor cursor = null;
        String currentQuery;
        ArrayList<Film> newFilms;

        updatedTable = false;
        newFilms = new ArrayList<>();

        try{

            db = dbHelper.getReadableDatabase();

            //STEP 1: across the arrayList look for any film that is not in local DB
            for(Film currentFilm : listFilm){
                currentQuery = "SELECT * FROM Films WHERE title = '"+currentFilm.getTittle()+"'";
                cursor = db.rawQuery(currentQuery,null);

                //STEP 2: If the film doesn't exit I'll add to DB
                if(!cursor.moveToFirst()){
                    updatedTable = true;
                    newFilms.add(currentFilm);
                }


            }
            if(cursor != null)
                cursor.close();
        }catch (SQLException e){
            e.getStackTrace();
        }finally {
            if(db != null)
                if(db.isOpen())
                    db.close();
            addFilms(newFilms);
        }

        return updatedTable;
    }

    public void addFilms(ArrayList<Film> filmsToAdd){
        ContentValues register;

        try{
            db = dbHelper.getWritableDatabase();

            for (Film currentFilm: filmsToAdd) {

                register = new ContentValues();
                Log.e("ID",String.valueOf(currentFilm.getIdFilm()));
                Log.e("Title",String.valueOf(currentFilm.getTittle()));

                register.put("id",currentFilm.getIdFilm());
                register.put("title",currentFilm.getTittle());
                register.put("director",currentFilm.getDirector());
                register.put("category",currentFilm.getCategory());
                register.put("summary",currentFilm.getSinopsis());
                register.put("picture",currentFilm.getPicture());
                register.put("price",currentFilm.getPrice());
                register.put("favourite",0);

                db.insert(FILMS_TABLE,null,register);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(db != null)
                if(db.isOpen())
                    db.close();
        }
    }

    public boolean addFavourites(Film currentFilm, byte addOrRemove){
        boolean updated;
        ContentValues register;


        try{
            //STEP 1: Opening the writable DB
            db = dbHelper.getWritableDatabase();
            register = new ContentValues();

            //STEP 2: Changing favourite field to 1 for true
            register.put("favourite",addOrRemove);

            //STEP 3: Executing the sentence
            db.update(FILMS_TABLE,register,WHERE_CLAUSE+currentFilm.getTittle()+"'",null);
            updated = true;
        }catch (SQLException e){
            updated = false;
        }finally {
            if (db.isOpen())
                db.close();
        }

        return updated;
    }

    public byte verifyFavourites(Film currentFilm){
        byte favourite;
        Cursor cursor;
        String query;

        favourite = 0;
        try{
            query = "SELECT favourite FROM Films WHERE title = '"+currentFilm.getTittle()+"'";
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query,null);

            if(cursor.moveToFirst())
                favourite = (byte)cursor.getInt(0);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(db != null)
                if(db.isOpen())
                    db.close();
        }

        return favourite;
    }

    public ArrayList<Film> searchFilms(String filmToSearch){
        String query;
        ArrayList<Film> listFilms;
        Cursor cursor;

        listFilms = new ArrayList<>();
        try{
            query = "SELECT * FROM Films WHERE title Like '"+filmToSearch+"%'";
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(query,null);

            if(cursor.moveToFirst()){
                do{
                    listFilms.add(new Film(cursor.getInt(0),
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
            e.printStackTrace();
        }finally {
            if(db!=null)
                if (db.isOpen())
                    db.close();
        }

        return listFilms;
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
            if(db!=null)
                if (db.isOpen())
                    db.close();
        }

        return listFilm;
    }
}
