package com.josealvarez.top100films.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.josealvarez.top100films.Controllers.detailedFilm;
import com.josealvarez.top100films.Models.Film;
import com.josealvarez.top100films.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alvar on 01/09/2017.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmAdapterViewHolder>{

    private ArrayList<Film> filmList;
    private Context context;

    public FilmAdapter(ArrayList<Film> filmList, Context context){
        this.filmList = filmList;
        this.context = context;
    }


    public class FilmAdapterViewHolder extends RecyclerView.ViewHolder{

        ImageView filmPicture;
        TextView filmName;
        Button seeDetails;

        public FilmAdapterViewHolder(View itemView) {
            super(itemView);

            filmPicture = itemView.findViewById(R.id.iv_picture_film_list);
            filmName = itemView.findViewById(R.id.tv_name_film_list);
            seeDetails = itemView.findViewById(R.id.bt_detail);
        }

    }

    @Override
    public FilmAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_list_view,parent,Boolean.FALSE);
        return new FilmAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmAdapterViewHolder holder, final int position) {

        Picasso.with(context).load(filmList.get(position).getPicture()).into(holder.filmPicture);
        holder.filmName.setText(filmList.get(position).getTittle());

        //Listener for the button
        holder.seeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,detailedFilm.class);
                intent.putExtra("film",filmList.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }
}
