package com.example.mysettingslibrary.model;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * the type of a default value of each settings item (preference) (String/ string set/ boolean)
 *
 * @param <V>
 */
public class ItemSettingsClass<V> {
    // not null parameters
    int type;
    String key;
    String title;
    private V defaultValue;

    // a param that not all preferences will have
    @Nullable
    String summary;
    @Nullable
    @DrawableRes
    int icon;
    private V value;
    @Nullable
    private String title_boolean_value_off;
    @Nullable
    private String list_dialog_title;
    @Nullable
    private ArrayList<String> entries;
    @Nullable
    private CharSequence[] entriesValues = new CharSequence[3];
    @Nullable
    private int edt_txt_input_type;
    private int layout_id;

    /**
     * empty constructor
     */
    public ItemSettingsClass() {
    }

    /**
     * MULTI SELECT LIST + LIST BOX PREFERENCE CONSTRUCTOR
     *
     * @param type
     * @param key
     * @param defaultValue
     * @param title
     * @param summary
     * @param icon
     * @param list_dialog_title
     * @param entries
     */
    public ItemSettingsClass(int type, String key, V defaultValue, String title, @Nullable String summary, int icon, String list_dialog_title, ArrayList<String> entries,int layout_id) {
        this.key = key;
        this.title = title;
        this.type = type;
        this.summary = summary;
        this.icon = icon;
        this.defaultValue = defaultValue;
        this.entries = entries;
        this.list_dialog_title = list_dialog_title;
        this.layout_id = layout_id;
    }

    /**
     * SWITCH + CHECK BOX PREFERENCE CONSTRUCTOR
     *
     * @param type
     * @param key
     * @param defaultValue
     * @param title
     * @param title_boolean_value_off
     * @param summary
     * @param icon
     */
    public ItemSettingsClass(int type, String key, V defaultValue, String title, @Nullable String title_boolean_value_off, @Nullable String summary, int icon,int layout_id) {
        this.key = key;
        this.title = title;
        this.type = type;
        this.summary = summary;
        this.icon = icon;
        this.defaultValue = defaultValue;
        this.title_boolean_value_off = title_boolean_value_off;
        this.layout_id = layout_id;
    }

    /**
     * BASIC PREFERENCE CONSTRUCTOR
     * @param type
     * @param key
     * @param title
     * @param summary
     * @param icon
     */
    public ItemSettingsClass(int type, String key, String title, @Nullable String summary, int icon, int layout_id) {
        this.key = key;
        this.title = title;
        this.type = type;
        this.summary = summary;
        this.icon = icon;
        this.layout_id = layout_id;
    }

    /**
     * EDIT TEXT PREFERENCE CONSTRUCTOR
     *
     * @param type
     * @param key
     * @param defaultValue
     * @param title
     * @param summary
     * @param edt_txt_input_type
     * @param icon
     */
    public ItemSettingsClass(int type, String key, V defaultValue, String title, @Nullable String summary, int edt_txt_input_type, int icon,int layout_id) {
        this.key = key;
        this.title = title;
        this.defaultValue = defaultValue;
        this.type = type;
        this.summary = summary;
        this.edt_txt_input_type = edt_txt_input_type;
        this.icon = icon;
        this.layout_id = layout_id;
    }

    public int getLayout_id() {
        return layout_id;
    }


    @Nullable
    public int getEdt_txt_input_type() {
        return edt_txt_input_type;
    }

    @Nullable
    public String getList_dialog_title() {
        return list_dialog_title;
    }

    @Nullable
    public ArrayList<String> getEntries() {
        return entries;
    }


    @Nullable
    public CharSequence[] getEntriesValues() {
        return entriesValues;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public V getDefaultValue() {
        return defaultValue;
    }


    public int getType() {
        return type;
    }


    @Nullable
    public String getSummary() {
        return summary;
    }


    @Nullable
    @DrawableRes
    public int getIcon() {
        return icon;
    }

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }


    public String getTitle_boolean_value_off() {
        return title_boolean_value_off;
    }


}
