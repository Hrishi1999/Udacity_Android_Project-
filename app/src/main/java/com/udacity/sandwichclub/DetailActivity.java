package com.udacity.sandwichclub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView aKa;
    private TextView origin;
    private TextView desc;
    private TextView ing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        aKa = findViewById(R.id.also_known_tv);
        origin = findViewById(R.id.origin_tv);
        desc = findViewById(R.id.description_tv);
        ing = findViewById(R.id.ingredients_tv);


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
        Sandwich sandwich = null;

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.color.colorAccent)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    private void populateUI(Sandwich sw) {

        origin.setText(sw.getPlaceOfOrigin());
        desc.setText(sw.getDescription());

        List<String> aka1 = sw.getAlsoKnownAs();
        if(!aka1.isEmpty())
        {
            aKa.setText(TextUtils.join(", ", aka1));
        }
        else
        {
            aKa.setVisibility(View.GONE);
        }


        List<String> ing2 = sw.getIngredients();
        if(!ing2.isEmpty())
        {
            ing.setText(TextUtils.join(", ", ing2));
        }
        else
        {
            ing.setVisibility(View.GONE);
        }

    }
}


