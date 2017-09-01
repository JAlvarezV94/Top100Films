package com.josealvarez.top100films.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.josealvarez.top100films.Adapters.FilmAdapter;
import com.josealvarez.top100films.Models.Film;
import com.josealvarez.top100films.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_filmlist)
    RecyclerView rvFilmList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Film> filmsList;
    private String[] titleAndDirector;
    private String completeTittle,title,director,sinopsis,category,picture,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        filmsList = new ArrayList<>();
        titleAndDirector = new String[2];

        downloadFilms();
    }

    public void downloadFilms(){
        String url = "https://itunes.apple.com/es/rss/topmovies/limit=100/json";

        RequestQueue queue = Volley.newRequestQueue(this);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            //STEP 1: Get the array with the 100 films
                            JSONObject feed = response.getJSONObject("feed");
                            JSONArray entry = feed.getJSONArray("entry");

                            //STEP 2: Obtaining every data that we want for the 100 films
                            for(int i = 1; i <= entry.length(); i++){

                                //STEP 3: Getting the current film
                                JSONObject currentFilm = entry.getJSONObject(i-1);

                                //Obtaining and separating tittle and director
                                JSONObject titleJson = currentFilm.getJSONObject("title");
                                completeTittle = titleJson.getString("label");
                                titleAndDirector = completeTittle.split("-");

                                //Saving the name
                                title = titleAndDirector[0].trim();

                                //Saving the director
                                director = titleAndDirector[1].trim();

                                //Obtaining the sinopsis
                                JSONObject summary = currentFilm.getJSONObject("summary");
                                sinopsis = summary.getString("label");

                                //Obtaining the category
                                JSONObject categoryJson = currentFilm.getJSONObject("category");
                                JSONObject attributesCategory = categoryJson.getJSONObject("attributes");
                                category = attributesCategory.getString("label");

                                //Obtaining the picture
                                JSONArray imageArray = currentFilm.getJSONArray("im:image");
                                JSONObject imageJson = imageArray.getJSONObject(2);
                                picture = imageJson.getString("label");

                                //Obtaining the price (obtained like string cause it's a string in the Json)
                                JSONObject rentalPrice = currentFilm.getJSONObject("im:price");
                                price = rentalPrice.getString("label");

                                //Adding the last one to the list
                                filmsList.add(new Film(i,title,director,category,sinopsis,picture,price));
                            }

                            //STEP 4: When the list is complete generating the recyclerView
                            generateFilmList();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("NOT FOUND","NOT FOUND");
            }
        }
        ) {

            /*AND THIS METHOD IS TO ADD PARAMS FOR THE GET, BUT VOLLEY HAS A BUG WITH THAT,
              SO I HAVE IMPLEMENT THE PARAMS FOR THE SEARCH IN THE URL.*/
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("limit","100");
                return params;
            }
        };

        queue.add(request);
    }

    public void generateFilmList(){
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,Boolean.FALSE);
        rvFilmList.setLayoutManager(layoutManager);
        adapter = new FilmAdapter(filmsList,getApplicationContext());
        rvFilmList.setAdapter(adapter);
    }
}
