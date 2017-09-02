package com.josealvarez.top100films.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.josealvarez.top100films.DAO.FavouritesDAO;
import com.josealvarez.top100films.Models.Film;
import com.josealvarez.top100films.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class detailedFilm extends AppCompatActivity {


    //Const
    static final String ADD_FAVOURITES_MESSAGE = "Added to favourites";
    static final String REMOVE_FAVOURITES_MESSAGE = "Removed to favourites";
    static final byte ADD_FILM = 1;
    static final byte REMOVE_FILM = 0;

    //Attributes
    FavouritesDAO favouritesDAO;
    Film currentFilm;
    boolean updated;
    byte favourite;


    @BindView(R.id.tv_name_film_detailed)
    TextView tvTitle;
    @BindView(R.id.tv_director_film_detailed)
    TextView tvDirector;
    @BindView(R.id.tv_category_film_detailed)
    TextView tvCategory;
    @BindView(R.id.tv_summary_film)
    TextView tvSummary;
    @BindView(R.id.bt_add_favourite)
    Button btFavourite;
    @BindView(R.id.bt_remove_favourite)
    Button btRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_film);
        ButterKnife.bind(this);

        //Rescue the DB
        favouritesDAO = new FavouritesDAO(getApplicationContext());

        //STEP 1: recover the film sent
        currentFilm = getIntent().getParcelableExtra("film");

        //STEP 2: Filling the film
        fillFilm();

        //STEP 3: Verify if the film is added to favorite to change the button
        favourite = favouritesDAO.verifyFavourites(currentFilm);

        if(favourite==0){
            btFavourite.setVisibility(View.VISIBLE);
            btRemove.setVisibility(View.GONE);
        }
        else{
            btFavourite.setVisibility(View.GONE);
            btRemove.setVisibility(View.VISIBLE);
        }

        btFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Saving the current film into the favourite table
                updated = favouritesDAO.addFavourites(currentFilm,ADD_FILM);

                if(updated){
                    Toast.makeText(getApplicationContext(),ADD_FAVOURITES_MESSAGE,Toast.LENGTH_SHORT).show();
                    btFavourite.setVisibility(View.GONE);
                    btRemove.setVisibility(View.VISIBLE);
                }

            }
        });

        btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Saving the current film into the favourite table
                updated = favouritesDAO.addFavourites(currentFilm,REMOVE_FILM);

                if(updated){
                    Toast.makeText(getApplicationContext(),REMOVE_FAVOURITES_MESSAGE,Toast.LENGTH_SHORT).show();
                    btFavourite.setVisibility(View.VISIBLE);
                    btRemove.setVisibility(View.GONE);
                }
            }
        });

    }

    public void fillFilm(){

        //Filling the title
        tvTitle.setText(getString(R.string.tv_film_name) +" "+ currentFilm.getTittle());
        //Filling the director
        tvDirector.setText(getString(R.string.tv_film_director) +" "+ currentFilm.getDirector());
        //Filling the category
        tvCategory.setText(getString(R.string.tv_film_category) +" "+ currentFilm.getCategory());
        //Filling the summary
        tvSummary.setText(getString(R.string.tv_summary) +" "+ currentFilm.getSinopsis());
    }
}
