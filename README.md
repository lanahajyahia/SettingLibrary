# SettingLibrary

[![](https://jitpack.io/v/lanahajyahia/SettingLibrary.svg)](https://jitpack.io/#lanahajyahia/SettingLibrary)

Android Library that arranges Settings Page based on user passed information, using Preference android Library. The settings screen will contain a Preference hierarchy.
The Settings page will contain Preference Categories and inside each category a Preference dependes on your choice.

![](file:///C:/Users/hajya/Desktop/andriod/gifSettings.gif)


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

## Available Settings
each Category Prefernce can contain any of:
 1. Basic Preference
 2. Switch Preference
 3. Edit Text Preference
 4. List Preference
 5. Multi-Select List Prefernce 
 
 ## How to Use the Library (see my sample app)
 First of, you need to create activity Settings. and Arraylists of <ItemSettingsClass> and <CategorySettingsClass> dependes on the kind of settings preference you want
 to iclude your settings page with. you must declare final Static String varibales for each Preference to set the key of it so the Library recognize
 each one of them. and save in shared preference.
 
 for Example
 ```
    public static final String SWITCH_PREFERENCE_KEY1 = "switch1";
    public static final String SWITCH_PREFERENCE_KEY2 = "switch2";
    public static final String BASIC_PREFERENCE_KEY1 = "basic1";
    public static final String BASIC_PREFERENCE_KEY2 = "basic2";
 ```
 and then,
 ```
 ArrayList<ItemSettingsClass> categorySettingsItemsArrayList1 = new ArrayList<>();
 ArrayList<ItemSettingsClass> categorySettingsItemsArrayList2 = new ArrayList<>();
  ```
  # Basic preference
 for basic prefernce you enter:
 SETTINGS_TYPE_BASIC, key(explained above), title, summary(optional can be null), summary(optional can be null), 
 icon(optional can be 0), layout_id(optional can be 0)
 ```
categorySettingsItemsArrayList1.add(new ItemSettingsClass(SETTINGS_TYPE_BASIC, BASIC_PREFERENCE_KEY2, "About", null, R.drawable.about,R.layout.basic_pref_layout));
 ```
  # Switch preference
for switch prefernce you enter:
SETTINGS_TYPE_SWITCH, key(explained above), defualt value, title, title in off case(optional can be null), summary(optional can be null), 
icon(optional can be 0), layout_id(optional can be 0)
  ```
categorySettingsItemsArrayList1.add(new ItemSettingsClass(SETTINGS_TYPE_SWITCH, SWITCH_PREFERENCE_KEY2, false, "Private Account", null, null, R.drawable.padlock,0));
 ```
 # Edit Text Preference
for Edit Text prefernce you enter:
SETTINGS_TYPE_EDIT_TEXT, key(explained above), defualt value(optional can be null) ,title ,summary(optional can be null), 
input type(Inputtype class), icon(optional can be 0), layout_id( optional can be 0)
 ```
   categorySettingsItemsArrayList2.add(new ItemSettingsClass(SETTINGS_TYPE_EDIT_TEXT, EDIT_TEXT_PREFERENCE_KEY1, null, "Your Age", "enter your age", InputType.TYPE_CLASS_NUMBER, 0,0));
 ```
 # List or Multi select List Preference
 for List/ Multi select List prefernce you enter:
 SETTINGS_TYPE_MULTI_SELECT_LIST or SETTINGS_TYPE_LIST , key(explained above), defualt value(optional can be null), title ,summary(optional can be null), 
 icon (optional can be 0), dialog title(optional can be null), Arraylist<String> enteries, layout_id(optional can be 0)
 entries array list example:
	
```
ArrayList<String> hideStoryTitles = new ArrayList<>();
hideStoryTitles.add("user 1");
hideStoryTitles.add("user 2");
hideStoryTitles.add("user 3");
hideStoryTitles.add("user 4");
hideStoryTitles.add("user 5");
hideStoryTitles.add("user 6");	

categorySettingsItemsArrayList2.add(new ItemSettingsClass(SETTINGS_TYPE_MULTI_SELECT_LIST, MULTI_SELECT_LIST_PREFERENCE_KEY1, null, "Story", "Hide Story From", 0, "Hide story from", hideStoryTitles,0));

```
then you call the create array from Settings class. it creates array of type CategorySettingsClass which has 3 parameters:
1. key 
2. title 
3. array list of items array list.
```
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
## if you want to do function while click on a specific preference do the following:
for example, while click on basic prefernce with title security A toast will show with the title.
I used the event bus Library to transfer clicked Preference between the Settings Library and Settings Activity.
```
    @Subscribe
    public void onBasicSettingsClicked(SettingsClickEvent event) {
        if (event.getClickedSettingsObj()
                .getKey()
                .equals(BASIC_PREFERENCE_KEY1)) {
            Toast.makeText(this, event.getClickedSettingsObj().getTitle(), Toast.LENGTH_SHORT).show();
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

```
