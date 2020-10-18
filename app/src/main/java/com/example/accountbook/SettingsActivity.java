package com.example.accountbook;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


import java.util.Properties;

public class SettingsActivity extends AppCompatActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private Preference auto_keep_account,auto_alarm,auto_backup;
    private Preference password_open,fingerprint_password,text_password,graph_password;
    private Preference email_setting,data_output,data_input;
    private Preference clear_all_data;
    private String auto_keep_account_key,auto_alarm_key,auto_backup_key,password_open_key;
    private String text_password_key,graph_password_key,fingerprint_password_key;
    private String email_setting_key,data_output_key,data_input_key ,clear_all_data_key;
//    PreferenceActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initPreferences();
    }

    private void initPreferences() {
        //get the preferences key
        auto_keep_account_key = getResources().getString(R.string.auto_keep_account_key);
        auto_alarm_key = getResources().getString(R.string.auto_alarm_key);
        auto_backup_key = getResources().getString(R.string.auto_backup_key);
        password_open_key = getResources().getString(R.string.password_open_key);
        text_password_key = getResources().getString(R.string.text_password_key);
        graph_password_key = getResources().getString(R.string.graph_password_key);
        fingerprint_password_key = getResources().getString(R.string.fingerprint_password_key);
        email_setting_key = getResources().getString(R.string.email_setting_key);
        data_output_key = getResources().getString(R.string.data_output_key);
        data_input_key = getResources().getString(R.string.data_input_key);
        clear_all_data_key = getResources().getString(R.string.clear_all_data_key);

        //initialize auto task preferences
//        PreferenceManager m = new PreferenceManager();
//        auto_backup = (Preference) m.findPreference(auto_backup_key);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

        }
    }
}