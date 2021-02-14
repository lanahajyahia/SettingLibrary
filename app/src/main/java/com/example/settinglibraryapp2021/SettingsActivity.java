package com.example.settinglibraryapp2021;


import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysettingslibrary.MainSettingsFragment;
import com.example.mysettingslibrary.Settings;
import com.example.mysettingslibrary.interfaces.SettingsClickEvent;
import com.example.mysettingslibrary.model.CategorySettingsClass;
import com.example.mysettingslibrary.model.ItemSettingsClass;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Set;

import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_BASIC;
import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_EDIT_TEXT;
import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_LIST;
import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_MULTI_SELECT_LIST;
import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_SWITCH;


public class SettingsActivity extends AppCompatActivity {

    public static final String SWITCH_PREFERENCE_KEY1 = "switch1";
    public static final String SWITCH_PREFERENCE_KEY2 = "switch2";
    public static final String BASIC_PREFERENCE_KEY1 = "basic1";
    public static final String BASIC_PREFERENCE_KEY2 = "basic2";
    public static final String EDIT_TEXT_PREFERENCE_KEY1 = "editText1";
    public static final String LIST_PREFERENCE_KEY1 = "list1";
    public static final String LIST_PREFERENCE_KEY2 = "list2";
    public static final String MULTI_SELECT_LIST_PREFERENCE_KEY1 = "multiList1";
    public static final String CATEGORY_PREFERENCE_KEY1 = "category1";
    public static final String CATEGORY_PREFERENCE_KEY2 = "category2";
    public static final String CATEGORY_PREFERENCE_KEY3 = "category3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // create arrayLists of the data you want the settings library to arrange for you
        ArrayList<String> mentionsTitles = new ArrayList<>();
        mentionsTitles.add("Everyone");
        mentionsTitles.add("People You Follow");
        mentionsTitles.add("No one");


        ArrayList<String> likesTitles = new ArrayList<>();
        likesTitles.add("Off");
        likesTitles.add("From People I Follow");
        likesTitles.add("From Everyone");

        ArrayList<String> hideStoryTitles = new ArrayList<>();
        hideStoryTitles.add("user 1");
        hideStoryTitles.add("user 2");
        hideStoryTitles.add("user 3");
        hideStoryTitles.add("user 4");
        hideStoryTitles.add("user 5");
        hideStoryTitles.add("user 6");


        ArrayList<CategorySettingsClass> categorySettingsClassArrayList;
        ArrayList<ItemSettingsClass> categorySettingsItemsArrayList1 = new ArrayList<>();
        ArrayList<ItemSettingsClass> categorySettingsItemsArrayList2 = new ArrayList<>();
        ArrayList<ItemSettingsClass> categorySettingsItemsArrayList3 = new ArrayList<>();

        categorySettingsItemsArrayList1.add(new ItemSettingsClass(SETTINGS_TYPE_SWITCH, SWITCH_PREFERENCE_KEY2, false, "Private Account", null, null, R.drawable.padlock,0));

        categorySettingsItemsArrayList2.add(new ItemSettingsClass(SETTINGS_TYPE_SWITCH, SWITCH_PREFERENCE_KEY1, true, "turn on post notification", "turn off notification", "notification manger", R.drawable.notification,0));
        categorySettingsItemsArrayList2.add(new ItemSettingsClass(SETTINGS_TYPE_LIST, LIST_PREFERENCE_KEY1, mentionsTitles.get(0), "Mentions", null, R.drawable.contact, "Allow @mentions From", mentionsTitles,0));
        categorySettingsItemsArrayList2.add(new ItemSettingsClass(SETTINGS_TYPE_LIST, LIST_PREFERENCE_KEY2, likesTitles.get(2), "Likes", null, 0, "Allow Likes From", likesTitles,0));


        categorySettingsItemsArrayList3.add(new ItemSettingsClass(SETTINGS_TYPE_EDIT_TEXT, EDIT_TEXT_PREFERENCE_KEY1, null, "Your Age", "enter your age", InputType.TYPE_CLASS_NUMBER, 0,0));
        categorySettingsItemsArrayList3.add(new ItemSettingsClass(SETTINGS_TYPE_BASIC, BASIC_PREFERENCE_KEY2, "About", null, R.drawable.about,R.layout.basic_pref_layout));
        categorySettingsItemsArrayList3.add(new ItemSettingsClass(SETTINGS_TYPE_BASIC, BASIC_PREFERENCE_KEY1, "security", null, R.drawable.verified,0));
        categorySettingsItemsArrayList3.add(new ItemSettingsClass(SETTINGS_TYPE_MULTI_SELECT_LIST, MULTI_SELECT_LIST_PREFERENCE_KEY1, null, "Story", "Hide Story From", 0, "Hide story from", hideStoryTitles,0));


        categorySettingsClassArrayList = Settings.createSettingsArray(
                new CategorySettingsClass(CATEGORY_PREFERENCE_KEY1, "Account Privacy", categorySettingsItemsArrayList1),
                new CategorySettingsClass(CATEGORY_PREFERENCE_KEY2, "Interactions", categorySettingsItemsArrayList2),
                new CategorySettingsClass(CATEGORY_PREFERENCE_KEY3, "Account activities", categorySettingsItemsArrayList3)
        );

        Settings.initializeSettings(this, categorySettingsClassArrayList);

        init();
    }

    public void init() {
        //replace fragment with settings preference fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new MainSettingsFragment())
                .commit();
    }


    // used event bus library to pass data between library and activity
    @Subscribe
    public void onBasicSettingsClicked(SettingsClickEvent event) {
        if (event.getClickedSettingsObj()
                .getKey()
                .equals(BASIC_PREFERENCE_KEY1)) {
            Toast.makeText(this, event.getClickedSettingsObj().getTitle(), Toast.LENGTH_SHORT).show();
        } else if (event.getClickedSettingsObj()
                .getKey()
                .equals(SWITCH_PREFERENCE_KEY1)) {
            boolean prefValue = Settings.retrieveSettingsSharedPrefs(this)
                    .getBoolean(event.getClickedSettingsObj().getKey(),
                            false);
            Toast.makeText(this, "" + prefValue, Toast.LENGTH_SHORT).show();
        } else if (event.getClickedSettingsObj()
                .getKey()
                .equals(EDIT_TEXT_PREFERENCE_KEY1)) {
            Toast.makeText(this, event.getClickedSettingsObj().getSummary(), Toast.LENGTH_SHORT).show();

        } else if (event.getClickedSettingsObj()
                .getKey()
                .equals(LIST_PREFERENCE_KEY1)) {
            String entry = Settings.retrieveSettingsSharedPrefs(this)
                    .getString(event.getClickedSettingsObj()
                            .getKey(), "");
            Toast.makeText(this, entry, Toast.LENGTH_SHORT).show();
        } else if (event.getClickedSettingsObj()
                .getKey()
                .equals(MULTI_SELECT_LIST_PREFERENCE_KEY1)) {
            Set<String> entry_set = Settings.retrieveSettingsSharedPrefs(this)
                    .getStringSet(event.getClickedSettingsObj()
                            .getKey(), null);
            Toast.makeText(this, entry_set.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
