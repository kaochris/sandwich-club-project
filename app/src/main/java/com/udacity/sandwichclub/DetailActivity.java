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

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mPlaceOfOriginTextView;
    TextView mAlsoKnownAsTextView;
    TextView mIngredientsTextView;
    TextView mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPlaceOfOriginTextView = findViewById(R.id.detail_place_of_origin_text);
        mAlsoKnownAsTextView = findViewById(R.id.detail_also_known_as_text);
        mIngredientsTextView = findViewById(R.id.detail_ingredients_text);
        mDescriptionTextView = findViewById(R.id.detail_description_text);
        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        setStringListInTextView(mAlsoKnownAsTextView, sandwich.getAlsoKnownAs());
        setStringListInTextView(mIngredientsTextView, sandwich.getIngredients());
        mDescriptionTextView.setText(sandwich.getDescription());
    }

    private void setStringListInTextView(TextView view, List<String> list) {
        if (list.size() > 0) {
            for (String item : list) {
                view.append(item + "\n");
            }
        }
    }
}
