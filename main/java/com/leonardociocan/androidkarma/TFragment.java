package com.leonardociocan.androidkarma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

// Instances of this class are fragments representing a single
// object in our collection.
public  class TFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.tfragment, container, false);
        Bundle args = getArguments();
        ((RadioButton) rootView.findViewById(R.id.radioButton)).setText(
                Integer.toString(args.getInt(ARG_OBJECT)));
        return rootView;
    }
}