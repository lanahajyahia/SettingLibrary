package com.example.mysettingslibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.example.mysettingslibrary.Consts.Constants;
import com.example.mysettingslibrary.interfaces.SettingsClickEvent;
import com.example.mysettingslibrary.model.CategorySettingsClass;
import com.example.mysettingslibrary.model.ItemSettingsClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_BASIC;
import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_EDIT_TEXT;
import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_LIST;
import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_MULTI_SELECT_LIST;
import static com.example.mysettingslibrary.Consts.Constants.SETTINGS_TYPE_SWITCH;

public class MainSettingsFragment extends PreferenceFragmentCompat {
    Preference.OnPreferenceClickListener listener;

    SharedPreferences preferencesSP;
    SharedPreferences MySharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Context context = getPreferenceManager().getContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);
        preferencesSP = PreferenceManager.getDefaultSharedPreferences(getContext());

        String array_sp_name = context.getString(R.string.spMainArraySettings);
        MySharedPreferences = Settings.retrieveSettingsSharedPrefs(getContext());
        Gson gson = new Gson();

        String json = MySharedPreferences.getString(array_sp_name, Constants.BLANK);
        if (json.isEmpty()) {
            Toast.makeText(getContext(), "There is something error", Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<List<CategorySettingsClass>>() {
            }.getType();
            List<CategorySettingsClass> arrPackageData = gson.fromJson(json, type);
            for (CategorySettingsClass category : arrPackageData) {
                PreferenceCategory preferenceCategory = createCategoryPreference(category);
                screen.addPreference(preferenceCategory);

                for (ItemSettingsClass itemSettings : category.getCategoryItemsSettings()) {
                    switch (itemSettings.getType()) {
                        case SETTINGS_TYPE_BASIC:
                            Preference preference_index = createBasicPreference(itemSettings);
                            preferenceCategory.addPreference(preference_index);

                            preference_index.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                                @Override
                                public boolean onPreferenceClick(Preference preference) {
                                    EventBus.getDefault().post(new SettingsClickEvent(preference_index));
                                    return true;
                                }
                            });
                            break;

                        case SETTINGS_TYPE_SWITCH:
                            SwitchPreferenceCompat switch_preference_index = createSwitchPreference(itemSettings);
                            SharedPreferences.Editor editor = MySharedPreferences.edit();
                            preferenceCategory.addPreference(switch_preference_index);

                            switch_preference_index.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                                @Override
                                public boolean onPreferenceClick(Preference preference) {
                                    boolean is_on = preferencesSP.getBoolean(itemSettings.getKey(), false);
                                    editor.putBoolean(itemSettings.getKey(), is_on);
                                    editor.commit();
                                    switch_preference_index.setTitle(getTitleSwitchPreference(is_on, itemSettings.getTitle(), itemSettings.getTitle_boolean_value_off()));
                                    EventBus.getDefault().post(new SettingsClickEvent(switch_preference_index));
                                    return true;
                                }
                            });
                            break;
                        case SETTINGS_TYPE_EDIT_TEXT:
                            EditTextPreference edt_txt_preference_index = createEditTextPreference(itemSettings);

                            preferenceCategory.addPreference(edt_txt_preference_index);

                            edt_txt_preference_index.setOnPreferenceChangeListener((preference, newValue) -> {
                                if (newValue.toString().isEmpty() || newValue.toString() == null) {
                                    edt_txt_preference_index.setSummary(itemSettings.getSummary());
                                } else {
                                    edt_txt_preference_index.setSummary(newValue.toString());
                                }
                                EventBus.getDefault().post(new SettingsClickEvent(edt_txt_preference_index));
                                return true;
                            });
                            break;

                        case SETTINGS_TYPE_LIST:
                            ListPreference listPreference = createListPreference(itemSettings);
                            SharedPreferences.Editor editor_list = MySharedPreferences.edit();
                            preferenceCategory.addPreference(listPreference);
                            listPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                                editor_list.putString(itemSettings.getKey(), newValue.toString());
                                editor_list.commit();
                                editor_list.apply();
                                EventBus.getDefault().post(new SettingsClickEvent(listPreference));
                                return true;
                            });

                            break;
                        case SETTINGS_TYPE_MULTI_SELECT_LIST:
                            MultiSelectListPreference multiSelectListPreference = createMultiSelectListPreference(itemSettings);
                            SharedPreferences.Editor editor_multi = MySharedPreferences.edit();
                            preferenceCategory.addPreference(multiSelectListPreference);
                            multiSelectListPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                                editor_multi.putStringSet(itemSettings.getKey(), (Set<String>) newValue);
                                editor_multi.commit();
                                EventBus.getDefault().post(new SettingsClickEvent(multiSelectListPreference));
                                return true;
                            });
                        default:
                            break;
                    }
                }
            }
            setPreferenceScreen(screen);
        }
    }

    /**
     * creation o preference category and set values as the user chooses
     *
     * @param category
     * @return category
     */
    private PreferenceCategory createCategoryPreference(CategorySettingsClass category) {
        PreferenceCategory preferenceCategory = new PreferenceCategory(getContext());
        preferenceCategory.setKey(category.getKey());
        if (category.getTitle() != null) {
            preferenceCategory.setTitle(category.getTitle());
            preferenceCategory.setLayoutResource(R.layout.category_layout);
        }
        return preferenceCategory;
    }

    /**
     * creation of switch preference
     *
     * @param itemSettings
     * @return switch preference
     */
    private SwitchPreferenceCompat createSwitchPreference(ItemSettingsClass itemSettings) {
        SwitchPreferenceCompat switchPreferenceCompat = new SwitchPreferenceCompat(getContext());
        switchPreferenceCompat.setKey(itemSettings.getKey());
        if (itemSettings.getSummary() != null)
            switchPreferenceCompat.setSummary(itemSettings.getSummary());
        switchPreferenceCompat.setDefaultValue(itemSettings.getValue());
        if (itemSettings.getIcon() != 0) {
            switchPreferenceCompat.setIcon(itemSettings.getIcon());
        }
        boolean is_on = preferencesSP.getBoolean(itemSettings.getKey(), false);
        switchPreferenceCompat.setTitle(getTitleSwitchPreference(is_on, itemSettings.getTitle(), itemSettings.getTitle_boolean_value_off()));
        return switchPreferenceCompat;
    }

    /**
     * changes the title of switch preference in case the user requested two diifrent titles in each state (on/off)
     *
     * @param is_on
     * @param title_on
     * @param title_off
     * @return title to set
     */
    private String getTitleSwitchPreference(boolean is_on, String title_on, @Nullable String title_off) {
        if (title_off == null) {
            return title_on;
        } else {
            if (!is_on) {
                return title_on;
            } else {
                return title_off;
            }
        }
    }

    /**
     * creation of basic preference
     *
     * @param itemSettings
     * @return basic preference
     */
    private Preference createBasicPreference(ItemSettingsClass itemSettings) {
        Preference preference = new Preference(getContext());
        preference.setKey(itemSettings.getKey());
        preference.setTitle(itemSettings.getTitle());
        if (itemSettings.getLayout_id() != 0) {
            preference.setLayoutResource(itemSettings.getLayout_id());
        }
        if (itemSettings.getSummary() != null)
            preference.setSummary(itemSettings.getSummary());
        if (itemSettings.getIcon() != 0) {
            preference.setIcon(itemSettings.getIcon());
        }
        return preference;
    }

    /**
     * creation of edit text preference
     *
     * @param itemSettings
     * @return edit text preference
     */
    private EditTextPreference createEditTextPreference(ItemSettingsClass itemSettings) {
        EditTextPreference editTextPreference = new EditTextPreference(getContext());
        String user_input_summary = preferencesSP.getString(itemSettings.getKey(), Constants.BLANK);
        editTextPreference.setKey(itemSettings.getKey());
        editTextPreference.setTitle(itemSettings.getTitle());
        if (editTextPreference != null) {
            editTextPreference.setOnBindEditTextListener(
                    new EditTextPreference.OnBindEditTextListener() {
                        @Override
                        public void onBindEditText(@NonNull EditText editText) {
                            editText.setInputType(itemSettings.getEdt_txt_input_type());
                        }
                    });
        }

        if (user_input_summary.isEmpty() || user_input_summary == null) {
            editTextPreference.setSummary(itemSettings.getSummary());
        } else {
            editTextPreference.setSummary(user_input_summary);
        }
        if (itemSettings.getLayout_id() != 0) {
            editTextPreference.setLayoutResource(itemSettings.getLayout_id());
        }

        return editTextPreference;
    }

    /**
     * creation of list preference
     *
     * @param itemSettings
     * @return list preference
     */
    private ListPreference createListPreference(ItemSettingsClass itemSettings) {
        ListPreference listPreference = new ListPreference(getContext());
        listPreference.setKey(itemSettings.getKey());
        listPreference.setTitle(itemSettings.getTitle());
        listPreference.setDialogTitle(itemSettings.getList_dialog_title());
        if (itemSettings.getIcon() != 0) {
            listPreference.setIcon(itemSettings.getIcon());
        }
        listPreference.setDefaultValue(itemSettings.getValue());
        CharSequence[] charSequenceItems = (CharSequence[]) itemSettings.getEntries().toArray(new CharSequence[itemSettings.getEntries().size()]);
        listPreference.setEntries(charSequenceItems);
        listPreference.setEntryValues(charSequenceItems);
        listPreference.setSummaryProvider(new Preference.SummaryProvider() {
            @Override
            public CharSequence provideSummary(Preference preference) {
                return listPreference.getEntry();
            }
        });
        if (itemSettings.getLayout_id() != 0) {
            listPreference.setLayoutResource(itemSettings.getLayout_id());
        }

        return listPreference;
    }

    /**
     * creation of multi preference
     *
     * @param itemSettings
     * @return multi preference
     */
    private MultiSelectListPreference createMultiSelectListPreference(ItemSettingsClass itemSettings) {
        MultiSelectListPreference multiSelectListPreference = new MultiSelectListPreference(getContext());
        multiSelectListPreference.setKey(itemSettings.getKey());
        if (itemSettings.getIcon() != 0) {
            multiSelectListPreference.setIcon(itemSettings.getIcon());
        }
        multiSelectListPreference.setDefaultValue(itemSettings.getValue());
        multiSelectListPreference.setTitle(itemSettings.getTitle());
        multiSelectListPreference.setDialogTitle(itemSettings.getList_dialog_title());
        multiSelectListPreference.setSummary(itemSettings.getSummary());
        CharSequence[] charSequenceItems = (CharSequence[]) itemSettings.getEntries().toArray(new CharSequence[itemSettings.getEntries().size()]);
        multiSelectListPreference.setEntries(charSequenceItems);
        multiSelectListPreference.setEntryValues(charSequenceItems);

        if (itemSettings.getLayout_id() != 0) {
            multiSelectListPreference.setLayoutResource(itemSettings.getLayout_id());
        }
        return multiSelectListPreference;
    }


}