package com.leonardociocan.androidkarma;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by leo on 28/08/14.
 */
public class KarmaPreferencesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new KarmaPreferenceFragment()).commit();


    }

}
