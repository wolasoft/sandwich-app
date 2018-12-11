package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.Iterator;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView originTV;
    private TextView alsoKnownAsTv;
    private TextView ingredientTv;
    private TextView descriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTV = findViewById(R.id.origin_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_as_tv);
        ingredientTv = findViewById(R.id.ingredients_tv);
        descriptionTv = findViewById(R.id.description_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        originTV.setText(sandwich.getPlaceOfOrigin());

        Iterator<String> namesIterator = sandwich.getAlsoKnownAs().iterator();
        while (namesIterator.hasNext()) {
            alsoKnownAsTv.append(namesIterator.next());
            if (namesIterator.hasNext()) {
                alsoKnownAsTv.append(", ");
            }
        }

        Iterator<String> ingredientsIterator = sandwich.getIngredients().iterator();
        while (ingredientsIterator.hasNext()) {
            ingredientTv.append(ingredientsIterator.next());
            if (ingredientsIterator.hasNext()) {
                ingredientTv.append(", ");
            }
        }

        descriptionTv.setText(sandwich.getDescription());
    }
}
