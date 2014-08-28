package com.leonardociocan.androidkarma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.leonardociocan.androidkarma.Habit.HabitHubFragment;
import com.leonardociocan.androidkarma.Reward.RewardHubFragment;
import com.leonardociocan.androidkarma.Todo.TodoHubFragment;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class TabAdapter extends FragmentStatePagerAdapter {
    public TabAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0){
            return new MainTab();
        }
        if(i == 1){
            return new HabitHubFragment();
        }

        if(i == 2) return new TodoHubFragment();

        if(i==3){
            return new RewardHubFragment();
        }



        Fragment fragment = new TFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(TFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
