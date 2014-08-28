package com.leonardociocan.androidkarma;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.leonardociocan.androidkarma.Habit.Habit;
import com.leonardociocan.androidkarma.Reward.Reward;
import com.leonardociocan.androidkarma.Todo.Todo;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity {

    TabAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Core.context = this;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Core.setKarma( sharedPreferences.getInt("karma", 0));
        getActionBar().setTitle(Core.getKarma() + " karma");

        Core.source = new CoreDataSource(this);
        Core.source.open();
        Core.Habits = new ArrayList<Habit>();
        Core.Habits = Core.source.GetHabits();

        Core.Rewards = new ArrayList<Reward>();
        Core.Rewards = Core.source.GetRewards();

        Core.Todos = new ArrayList<Todo>();
        Core.Todos = Core.source.GetTodos();


        Core.Logs = new ArrayList<Log>();
        Core.Logs = Core.source.GetLogs();


        mDemoCollectionPagerAdapter =
                new TabAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);


        final ActionBar actionBar = getActionBar();

        final int[] colors = new int[]{ R.color.gray , R.color.green , R.color.blue , R.color.orange };

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(colors[position])));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        Core.addKarmaEventListener(new KarmaChangedListener() {
            @Override
            public void OnKarmaChanged() {
                actionBar.setTitle(Core.getKarma() +" karma");
            }
        });

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
                mViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

            }
        };

        String[] names = new String[]{"Info" , "Habits" , "ToDos" , "Rewards"};
        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 4; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(names[i])
                            .setTabListener(tabListener));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



