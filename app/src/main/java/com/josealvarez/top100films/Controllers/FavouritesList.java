package com.josealvarez.top100films.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.josealvarez.top100films.Adapters.FavouriteAdapter;
import com.josealvarez.top100films.DAO.FavouritesDAO;
import com.josealvarez.top100films.Models.Film;
import com.josealvarez.top100films.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouritesList extends AppCompatActivity {

    @BindView(R.id.tv_not_favourites_added)
    TextView notFavourite;
    @BindView(R.id.rv_favouritelist)
    RecyclerView rvFavouriteList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    FavouritesDAO favouritesDAO;
    ArrayList<Film> listFavourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_list);
        ButterKnife.bind(this);

        favouritesDAO = new FavouritesDAO(getApplicationContext());

        listFavourites = favouritesDAO.readFavoutires();

        if(!listFavourites.isEmpty()){
            rvFavouriteList.setVisibility(View.VISIBLE);
            notFavourite.setVisibility(View.GONE);

            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,Boolean.FALSE);
            rvFavouriteList.setLayoutManager(layoutManager);
            adapter = new FavouriteAdapter(listFavourites,getApplicationContext());
            rvFavouriteList.setAdapter(adapter);
        }else{
            rvFavouriteList.setVisibility(View.GONE);
            notFavourite.setVisibility(View.VISIBLE);
        }
    }
}
