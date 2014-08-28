package com.leonardociocan.androidkarma.Reward;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.leonardociocan.androidkarma.Core;
import com.leonardociocan.androidkarma.KarmaChangedListener;
import com.leonardociocan.androidkarma.R;
import com.leonardociocan.androidkarma.Reward.Reward;

import java.util.ArrayList;

public class RewardHubFragment extends Fragment{

    ListView listView;
    ArrayList<Reward> list = new ArrayList<Reward>();
    RewardListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.reward_hub_fragment, container, false);
        Bundle args = getArguments();
        //((RadioButton) rootView.findViewById(R.id.radioButton)).setText(
        //       Integer.toString(args.getInt(ARG_OBJECT)));



        final EditText text = (EditText) rootView.findViewById(R.id.newRewardBox);
        text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;

                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    Toast.makeText(getActivity() ,"hello",Toast.LENGTH_LONG).show();

                    int value  = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("default_value" , "150"));
                    Reward reward = new Reward(text.getText().toString() , value, true);
                    Core.Rewards.add(reward);
                    Core.source.addItem(reward.getName() , reward.getValue() , reward.getPositive() , "reward");
                    adapter.notifyDataSetChanged();
                    text.setText("");
                }
                return false;
            }
        });

        listView = (ListView)rootView.findViewById(R.id.reward_list);
        adapter =new RewardListAdapter(getActivity());
        Core.addKarmaEventListener(new KarmaChangedListener() {
            @Override
            public void OnKarmaChanged() {
                adapter.notifyDataSetChanged();
            }
        });
        listView.setAdapter(adapter);
        return rootView;
    }
}
