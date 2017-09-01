package com.josealvarez.top100films.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.josealvarez.top100films.Models.Film;
import com.josealvarez.top100films.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class detailedFilm extends AppCompatActivity {

    @BindView(R.id.iv_picture_film_detailed)
    ImageView ivPicture;
    @BindView(R.id.tv_name_film_detailed)
    TextView tvTitle;
    @BindView(R.id.tv_director_film_detailed)
    TextView tvDirector;
    @BindView(R.id.tv_price_film_detailed)
    TextView tvPrice;
    @BindView(R.id.tv_rent_film_detailed)
    TextView tvRent;
    @BindView(R.id.tv_summary_film)
    TextView tvSummary;
    @BindView(R.id.bt_buy)
    Button btBuy;
    @BindView(R.id.bt_rent)
    Button btRent;

    Film currentFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_film);
        ButterKnife.bind(this);

        //STEP 1: recover the film sent
        currentFilm = getIntent().getParcelableExtra("film");

        //STEP 2: Filling the film
        fillFilm();

    }

    public void fillFilm(){

        //Filling the picture
        Picasso.with(getApplicationContext()).load(currentFilm.getPicture()).into(ivPicture);
        //Filling the title
        tvTitle.setText(currentFilm.getTittle());
        //Filling the director
        tvDirector.setText(currentFilm.getDirector());
        //Filling the price of buy
        tvPrice.setText(currentFilm.getPrice());

        //Filling the price of rent

        //Filling the summary
        tvSummary.setText(currentFilm.getSinopsis());
    }
}
