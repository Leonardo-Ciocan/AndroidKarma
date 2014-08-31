package com.leonardociocan.androidkarma;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LogAdapter extends ArrayAdapter<Log> {

    public  LogAdapter(Context c , ArrayList<Log> ls){
        super(c, R.layout.log_row, ls);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        position = Core.Logs.size() - position - 1;
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.log_row,parent,false);

        TextView name = (TextView)convertView.findViewById(R.id.log_name);
        name.setText(getItem(position).getName());
        name.setTextColor( getContext().getResources().getColor(!getItem(position).Positive ? R.color.red : R.color.green) );

        TextView date = (TextView)convertView.findViewById(R.id.log_date);
        date.setText(getItem(position).getTime());
        date.setTextColor( getContext().getResources().getColor(!getItem(position).Positive ? R.color.red : R.color.green) );



        return convertView;
    }
}
