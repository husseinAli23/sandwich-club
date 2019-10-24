package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
public class JsonUtils {

    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_IMAGE= "image";
    public static final String KEY_DESCRIPTION= "description";
    public static final String KEY_INGREDIENTS= "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich n = null;
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject jsonobject = jsonObject.getJSONObject("name");

            String mainName = jsonobject.getString(KEY_MAIN_NAME).trim();

            ArrayList<String> alsoKnownAsObj;
            alsoKnownAsObj = new ArrayList<>();
            for (int i = 0; i < jsonobject.getJSONArray(KEY_ALSO_KNOW_AS).length(); i++) {
                alsoKnownAsObj.add(""+jsonobject.getJSONArray(KEY_ALSO_KNOW_AS).get(i));
            }

            ArrayList<String> ingredientsObj;
            ingredientsObj = new ArrayList<>();
            for (int i = 0; i < jsonObject.getJSONArray(KEY_INGREDIENTS).length(); i++) {
                ingredientsObj.add(""+jsonObject.getJSONArray(KEY_INGREDIENTS).get(i));
            }

            String placeOfOrigin = jsonObject.getString(KEY_PLACE_OF_ORIGIN);
            String description = jsonObject.getString(KEY_DESCRIPTION);
            String image = jsonObject.getString(KEY_IMAGE);

            n = new Sandwich(mainName,alsoKnownAsObj,placeOfOrigin,description,image,ingredientsObj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return n;

    }
}
