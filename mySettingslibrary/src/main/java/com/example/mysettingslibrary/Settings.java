package com.example.mysettingslibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.mysettingslibrary.model.CategorySettingsClass;
import com.example.mysettingslibrary.model.ItemSettingsClass;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_BASIC;
import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_LIST;
import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_MULTI_SELECT_LIST;
import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_SWITCH;

public class Settings {

    /**
     * create array list of settings
     * @param settingsObjects
     * @return array
     */
    public static ArrayList<CategorySettingsClass> createSettingsArray(CategorySettingsClass... settingsObjects) {
        ArrayList<CategorySettingsClass> array = new ArrayList<>(settingsObjects.length);
        Collections.addAll(array, settingsObjects);
        return array;
    }

    /**
     * save settings to shared preference to pass to fragment
     * @param context
     * @param settingsList
     */
    public static void initializeSettings(Context context,
                                          ArrayList<CategorySettingsClass> settingsList) {
        String array_sp_name = context.getString(R.string.spMainArraySettings);
        SharedPreferences prefs = retrieveSettingsSharedPrefs(context);

        SharedPreferences.Editor editor = prefs.edit();
        for (CategorySettingsClass settObj : settingsList) {
            String key;
            Object defaultValue;
            ArrayList<ItemSettingsClass> itemsArrayList = settObj.getCategoryItemsSettings();
            for (ItemSettingsClass itemSett : itemsArrayList) {
                key = itemSett.getKey();

                defaultValue = itemSett.getDefaultValue();

                itemSett.setValue(defaultValue);

                if (prefs.contains(key) == true) {
                    switch (itemSett.getType()) {
                        case SETTINGS_TYPE_BASIC:
                            //no actual value to save
                            break;
                        case SETTINGS_TYPE_SWITCH:
                            editor.putBoolean(key, (boolean) defaultValue);
                            Log.d("pttt", "initializeSettings: " + itemSett.getValue());
                            break;
                    case SETTINGS_TYPE_LIST:
                        editor.putString(key, (String) defaultValue);
                        break;
                    case SETTINGS_TYPE_MULTI_SELECT_LIST:
                        editor.putStringSet(key, (Set<String>) defaultValue);
                        break;
                        default:
                            break;
                    }
                }
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(settingsList);

        editor.putString(array_sp_name, json);
        editor.commit();

        editor.apply();
    }

    /**
     * retrieve data that can change from sp to inform the user in SettingsActivity
     * @param context
     * @return SP
     */
    public static SharedPreferences retrieveSettingsSharedPrefs(Context context) {
        String name = context.getString(R.string.sharedPreferencesSettingsName);
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

}


