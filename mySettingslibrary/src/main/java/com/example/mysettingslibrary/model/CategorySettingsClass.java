package com.example.mysettingslibrary.model;

import android.content.Context;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CategorySettingsClass {
    private String key;
    @Nullable
    private String title;
    private ArrayList<ItemSettingsClass> categoryItemsSettings = new ArrayList<>();

    public CategorySettingsClass() {
    }

    public CategorySettingsClass(String key, String title, ArrayList<ItemSettingsClass> categoryItemsSettings) {
        this.key = key;
        this.title = title;
        this.categoryItemsSettings = categoryItemsSettings;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ItemSettingsClass> getCategoryItemsSettings() {
        return categoryItemsSettings;
    }

    public void setCategoryItemsSettings(ArrayList<ItemSettingsClass> categoryItemsSettings) {
        this.categoryItemsSettings = categoryItemsSettings;
    }
}
