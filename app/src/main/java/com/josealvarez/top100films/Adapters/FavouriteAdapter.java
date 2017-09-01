package com.josealvarez.top100films.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.josealvarez.top100films.Models.Film;
import com.josealvarez.top100films.R;

import java.util.ArrayList;

/**
 * Created by alvar on 01/09/2017.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteAdapterViewHolder>{

    private ArrayList<Film> filmList;
    private Context context;

    public FavouriteAdapter(ArrayList<Film> filmList, Context context){
        this.filmList = filmList;
        this.context = context;
    }

    public class FavouriteAdapterViewHolder extends RecyclerView.ViewHolder{

        ImageView filmPicture;
        TextView filmName;
        TextView directorName;
        TextView filmPrice;
        Button addFavourites;
        Button seeDetails;

        public FavouriteAdapterViewHolder(View itemView) {
            super(itemView);

            filmPicture = itemView.findViewById(R.id.iv_picture_film_list);
            filmName = itemView.findViewById(R.id.tv_name_film_list);
            directorName = itemView.findViewById(R.id.tv_director_film_list);
            filmPrice = itemView.findViewById(R.id.tv_price_film_list);
            addFavourites = itemView.findViewById(R.id.bt_add_favourite);
            seeDetails = itemView.findViewById(R.id.bt_detail);
        }

    }

    @Override
    public FavouriteAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_list_view,parent,Boolean.FALSE);
        return new FavouriteAdapter.FavouriteAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteAdapterViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }
}
