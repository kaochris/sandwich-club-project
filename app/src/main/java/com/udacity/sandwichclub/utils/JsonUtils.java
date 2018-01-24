package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwich = new JSONObject(json);
            JSONObject name = sandwich.getJSONObject("name");
            String mainName = name.getString("mainName");
            List<String> alsoKnownAs = parseJsonArray(name.getJSONArray("alsoKnownAs"));
            String placeOfOrigin = sandwich.getString("placeOfOrigin");
            String description = sandwich.getString("description");
            String image = sandwich.getString("image");
            List<String> ingredients = parseJsonArray(sandwich.getJSONArray("ingredients"));
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> parseJsonArray(JSONArray jsonArr) {
        List<String> strList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArr.length(); i++) {
                strList.add(jsonArr.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return strList;
    }
}
