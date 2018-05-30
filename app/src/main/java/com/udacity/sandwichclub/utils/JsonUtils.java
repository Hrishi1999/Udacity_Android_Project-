package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JsonUtils utils = new JsonUtils();

        //parse JSON
        JSONObject readJson = new JSONObject(json);

        JSONObject name  = readJson.getJSONObject("name");
        String mainName = name.getString("mainName");

        String placeOfOrigin = readJson.optString("placeOfOrigin");
        String description = readJson.optString("description");
        String imgUrl = readJson.optString("image");

        JSONArray aka = name.optJSONArray("alsoKnownAs");
        ArrayList<String> akaList = new ArrayList<>();
        for(int i=0; i<aka.length(); i++){
            akaList.add(aka.getString(i));
        }

        JSONArray jArray = readJson.optJSONArray("ingredients");
        ArrayList<String> ingredients = new ArrayList<>();
        for(int i=0; i<jArray.length(); i++){
            ingredients.add(jArray.getString(i));
        }

        return new Sandwich(mainName, akaList, placeOfOrigin, description, imgUrl, ingredients);
    }

}
