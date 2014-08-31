package com.leonardociocan.androidkarma;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.leonardociocan.androidkarma.Habit.Habit;
import com.leonardociocan.androidkarma.Reward.Reward;
import com.leonardociocan.androidkarma.Todo.Todo;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<Log> listItemList = new ArrayList<Log>();
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem() {
        listItemList.clear();
        Core.context = context;

        Core.source = new CoreDataSource(context);
        Core.source.open();

        Core.Logs = new ArrayList<Log>();
        Core.Logs = Core.source.GetLogs();


        for(int x = Core.Logs.size() - 1; x >= 0;x--){
            Log l = Core.Logs.get(x);
            listItemList.add(l);
        }


    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        int r = listItemList.size();
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*
    *Similar to getView of Adapter where instead of View
    *we return RemoteViews
    *
    */
    @Override
    public RemoteViews getViewAt(int position) {
        android.util.Log.i( "inf",String.valueOf(position) );
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.log_row);
        Log listItem = listItemList.get(position);
        remoteView.setTextViewText(R.id.log_name, listItem.getName());
        remoteView.setTextViewText(R.id.log_date, listItem.getTime());
        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, remoteView);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }
}