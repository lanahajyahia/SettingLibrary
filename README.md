# SettingLibrary

[![](https://jitpack.io/v/lanahajyahia/SettingLibrary.svg)](https://jitpack.io/#lanahajyahia/SettingLibrary)

Android Library that arranges Settings Page based on user passed information, using Preference android Library. The settings screen will contain a Preference hierarchy.
The Settings page will contain Preference Categories and inside each category a Preference such as: Preference/ switch Preference/ Edit Text Preference/
List Preference/ Multi-Seelect List Preference. dependes on your choice.

##Setup
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
	        implementation 'com.github.lanahajyahia:SettingLibrary:Tag'
	}
  ```
