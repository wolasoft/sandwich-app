package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject sandwichObject = new JSONObject(json);
            sandwich = new Sandwich();
            sandwich.setMainName(sandwichObject.getJSONObject("name").getString("mainName"));
            sandwich.setPlaceOfOrigin(sandwichObject.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichObject.getString("description"));
            sandwich.setImage(sandwichObject.getString("image"));

            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray alsoKnownAsArray = sandwichObject.getJSONObject("name").getJSONArray("alsoKnownAs");
            for (int i=0; i<alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownAs);

            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsArray = sandwichObject.getJSONArray("ingredients");
            for (int i=0; i<ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }
            sandwich.setIngredients(ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
