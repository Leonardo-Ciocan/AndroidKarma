package com.leonardociocan.androidkarma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by leo on 28/08/14.
 */
public class MainTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.main_page, container, false);
        final TextView tv = (TextView)rootView.findViewById(R.id.karmaLabel);
        tv.setText(Core.getKarma() + " karma");
        Core.addKarmaEventListener(new KarmaChangedListener() {
            @Override
            public void OnKarmaChanged() {
                tv.setText(Core.getKarma() + " karma");
            }
        });
        return rootView;
    }
}