# SettingLibrary

[![](https://jitpack.io/v/lanahajyahia/SettingLibrary.svg)](https://jitpack.io/#lanahajyahia/SettingLibrary)

Android Library that arranges Settings Page based on user passed information, using Preference android Library. The settings screen will contain a Preference hierarchy.
The Settings page will contain Preference Categories and inside each category a Preference dependes on your choice.

## Setup
Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency:
```
dependencies {
	        implementation 'com.github.lanahajyahia:SettingLibrary:1.00.01'
	}
  ```

## Availbe Settings
each Category Prefernce can contain any of:
 1. Basic Preference
 2. Switch Preference
 3. Edit Text Preference
 4. List Preference
 5. Multi-Select List Prefernce 
 
 ## How to Use the Library
 First of, you need to create activity Settings. and Arraylists of ItemSettingsClass and CategorySettingsClass dependes on the kind of settings prefernce you want
 to iclude your settings page with.
 ```
 ArrayList<ItemSettingsClass> categorySettingsItemsArrayList1 = new ArrayList<>();
 ArrayList<ItemSettingsClass> categorySettingsItemsArrayList2 = new ArrayList<>();
       
categorySettingsItemsArrayList1.add(new ItemSettingsClass(SETTINGS_TYPE_SWITCH, SWITCH_PREFERENCE_KEY2, false, "Private Account", null, null, R.drawable.padlock,0));
categorySettingsItemsArrayList2.add(new ItemSettingsClass(SETTINGS_TYPE_SWITCH, SWITCH_PREFERENCE_KEY1, true, "turn on post notification", "turn off notification", "notification manger", R.drawable.notification,0));
categorySettingsItemsArrayList2.add(new ItemSettingsClass(SETTINGS_TYPE_LIST, LIST_PREFERENCE_KEY1, mentionsTitles.get(0), "Mentions", null, R.drawable.contact, "Allow @mentions From", mentionsTitles,0));
categorySettingsItemsArrayList2.add(new ItemSettingsClass(SETTINGS_TYPE_LIST, LIST_PREFERENCE_KEY2, likesTitles.get(2), "Likes", null, 0, "Allow Likes From", likesTitles,0));

 categorySettingsClassArrayList = Settings.createSettingsArray(
 new CategorySettingsClass(CATEGORY_PREFERENCE_KEY1, "Account Privacy", categorySettingsItemsArrayList1),
 new CategorySettingsClass(CATEGORY_PREFERENCE_KEY2, "Interactions", categorySettingsItemsArrayList2));

Settings.initializeSettings(this, categorySettingsClassArrayList);
 ```
 Then, inside your Settings activity layout you create a FrameLayout:
 
```
<androidx.fragment.app.FragmentContainerView
        android:id="@+id/settings_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    </androidx.fragment.app.FragmentContainerView>
```
then, you write this code inside OnCreate Settings Activity
```
  getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new MainSettingsFragment())
                .commit();
```
