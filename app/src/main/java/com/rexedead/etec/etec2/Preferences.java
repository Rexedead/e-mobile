package com.rexedead.etec.etec2;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
//        final Preference customPref = findPreference("customPref");

//        customPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//
//                    public boolean onPreferenceClick(Preference preference) {
//
//                        SharedPreferences customSharedPreference = getSharedPreferences("myCustomSharedPrefs", Activity.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = customSharedPreference.edit();
//                        editor.putString("myCustomPref","The preference has been clicked");
//                        editor.commit();
//
////                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Preferences.this);
////                        String ssid = prefs.getString("ssid", "");
////                        String passw = prefs.getString("pw", "");
////                        String link = prefs.getString("weburl", "google.ru");
////                        Toast.makeText(getBaseContext(),ssid+"\n"+passw+"\n"+link, Toast.LENGTH_LONG).show();
//
//                        return true;
//                    }
//
//                });
    }

}
