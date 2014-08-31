package com.leonardociocan.androidkarma;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.leonardociocan.androidkarma.Habit.Habit;
import com.leonardociocan.androidkarma.Habit.HabitHubFragment;
import com.leonardociocan.androidkarma.Reward.Reward;
import com.leonardociocan.androidkarma.Reward.RewardHubFragment;
import com.leonardociocan.androidkarma.Todo.Todo;
import com.leonardociocan.androidkarma.Todo.TodoHubFragment;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity {

    TabAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    boolean HideClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent tutorial_start = new Intent();
        tutorial_start.setClass(this , TutorialActivity.class);
        //startActivity(tutorial_start);

        setContentView(R.layout.activity_main);

        boolean tablet = getResources().getBoolean(R.bool.isTablet);

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

        if(!tablet) {
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mDemoCollectionPagerAdapter);
            final ActionBar actionBar = getActionBar();

            final int[] colors = new int[]{ R.color.gray , R.color.green , R.color.blue , R.color.orange };

            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }
                int last_pos = 0;
                @Override
                public void onPageSelected(int position) {
                    actionBar.setSelectedNavigationItem(position);

                    //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(colors[position])));
                    //actionBar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(colors[position])));

                    ColorDrawable dr = new ColorDrawable(getResources().getColor(colors[last_pos]));
                    ColorDrawable dr1 = new ColorDrawable(getResources().getColor(colors[position]));
                    TransitionDrawable tr = new TransitionDrawable(new Drawable[]{dr,dr1});

                    last_pos = position;
                    ColorDrawable xdr = new ColorDrawable(getResources().getColor(colors[last_pos]));
                    ColorDrawable xdr1 = new ColorDrawable(getResources().getColor(colors[position]));
                    TransitionDrawable tr2 = new TransitionDrawable(new Drawable[]{xdr,xdr1});

                    actionBar.setBackgroundDrawable(tr);
                    actionBar.setStackedBackgroundDrawable(tr2);

                    tr.startTransition(400);
                    tr2.startTransition(400);
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
                    //HideClear = tab.getPosition() != 0;
                    //if(HideClear) invalidateOptionsMenu();

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
        else{
            getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));
            //LinearLayout first = (LinearLayout)findViewById(R.id.first_col);
            getSupportFragmentManager().beginTransaction().add(R.id.first_col , new MainTab()).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.second_col , new HabitHubFragment()).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.third_col , new TodoHubFragment()).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.fourth_col , new RewardHubFragment()).commit();

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        menu.getItem(0).setVisible(HideClear);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, KarmaPreferencesActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        else if(id == R.id.clear_recents){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure?").setMessage("This will delete all logs/karma transactions including the graph below")
                    .setPositiveButton("Delete",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for(Log l : Core.Logs){
                                Core.source.deleteLog(l.id);
                            }
                            Core.Logs.clear();
                            Core.triggerChanged();
                        }
                    }).setNegativeButton("Cancel",null).show();
        }


        return super.onOptionsItemSelected(item);
    }
}



