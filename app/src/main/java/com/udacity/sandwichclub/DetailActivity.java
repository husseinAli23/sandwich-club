package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView originTv, descriptionTv, ingredientsTv,alsoKnownTv,placeOfOriginLabel ,alsoKnownLabel,ingredientsLabel;

    Sandwich sandwich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        descriptionTv = findViewById(R.id.description_tv);
        originTv = findViewById(R.id.origin_tv);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        alsoKnownLabel = findViewById(R.id.also_known_label);
        placeOfOriginLabel = findViewById(R.id.place_of_origin_label);
        ingredientsLabel = findViewById(R.id.ingredients_label);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];


        sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI( );
        Picasso.with(this)
                .load(sandwich.getImage()).placeholder(R.drawable.image)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        descriptionTv.append(sandwich.getDescription());
        if(sandwich.getPlaceOfOrigin().isEmpty()){
            originTv.setVisibility(View.GONE);
            placeOfOriginLabel.setVisibility(View.GONE);
        }else{

            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        List<String> ingredientsObj;
        ingredientsObj = sandwich.getIngredients();
        if(ingredientsObj.isEmpty()){
            ingredientsTv.setVisibility(View.GONE);
            ingredientsLabel.setVisibility(View.GONE);
        }else{
            for (int i = 0; i < ingredientsObj.size(); i++) {
                ingredientsTv.append(ingredientsObj.get(i) + "\n");
            }
        }


        List<String> alsoKnownAsObj;
        alsoKnownAsObj = sandwich.getAlsoKnownAs();
        if(alsoKnownAsObj.isEmpty()){
            alsoKnownTv.setVisibility(View.GONE);
            alsoKnownLabel.setVisibility(View.GONE);
        }else {
            for (int i = 0; i < alsoKnownAsObj.size(); i++) {
                alsoKnownTv.append(alsoKnownAsObj.get(i) + "\n");
            }
        }
    }
}
