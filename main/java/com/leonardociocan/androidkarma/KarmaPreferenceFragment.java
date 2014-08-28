package com.leonardociocan.androidkarma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created by leo on 28/08/14.
 */
public class KarmaPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
        onSharedPreferenceChanged(getPreferenceScreen().getSharedPreferences(),"default_value");

        Preference button = findPreference("delete_all");
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you sure?").setMessage("This will delete all items , it cannot be undone.")
                        .setPositiveButton("Delete" , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Core.source.database.execSQL("delete from "+ DBHelper.TABLE);
                                Core.source.database.execSQL("delete from "+ "logs");
                                Core.Habits.clear();
                                Core.Logs.clear();
                                Core.Rewards.clear();
                                Core.Todos.clear();
                                Core.setKarma(0);
                                Core.triggerChanged();
                            }
                        })
                        .setNegativeButton("Cancel" , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();


                return true;
            }
        });

        Preference email = findPreference("email");
        email.setOnPreferenceClickListener( new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "leonardo.ciocan@outlook.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Karma for android feedback");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                return false;
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);

        if (pref instanceof EditTextPreference) {
            EditTextPreference listPref = (EditTextPreference) pref;
            pref.setSummary(listPref.getText());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
