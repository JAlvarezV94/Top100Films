package com.josealvarez.top100films.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alvar on 01/09/2017.
 */

public class Film implements Parcelable{

    //Attributes
    private int idFilm;
    private String tittle;
    private String director;
    private String category;
    private String sinopsis;
    private String picture;
    private String price;

    //Constructors
    public Film(){}

    public Film(int id,String tittle, String director, String category, String sinopsis,String picture, String price){
        this.idFilm = id;
        this.tittle = tittle;
        this.director = director;
        this.category = category;
        this.sinopsis = sinopsis;
        this.picture = picture;
        this.price = price;
    }

    //Getters and Setters


    protected Film(Parcel in) {
        idFilm = in.readInt();
        tittle = in.readString();
        director = in.readString();
        category = in.readString();
        sinopsis = in.readString();
        picture = in.readString();
        price = in.readString();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idFilm);
        parcel.writeString(tittle);
        parcel.writeString(director);
        parcel.writeString(category);
        parcel.writeString(sinopsis);
        parcel.writeString(picture);
        parcel.writeString(price);
    }
}
