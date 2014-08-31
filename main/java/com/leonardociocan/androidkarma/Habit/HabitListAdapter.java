package com.leonardociocan.androidkarma.Habit;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.leonardociocan.androidkarma.Core;
import com.leonardociocan.androidkarma.R;

import java.util.ArrayList;

class HabitListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Habit> data;
    private static LayoutInflater inflater = null;

    public HabitListAdapter(Context context) {
        this.context = context;
        this.data = Core.Habits;
        notifyDataSetChanged();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    int lastPosition = 0;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final View vi = inflater.inflate(R.layout.habit_row, null);
        //if (vi == null)
          //  vi = inflater.inflate(R.layout.habit_row, null);

        /*
        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        vi.startAnimation(animation);
        lastPosition = position;*/

        vi.setBackgroundColor(context.getResources().getColor(data.get(position).Positive ? R.color.green : R.color.red));

        TextView text = (TextView) vi.findViewById(R.id.habit_list_row_text);
        text.setText(data.get(position).getName());

        TextView vtext = (TextView) vi.findViewById(R.id.habit_list_row_value);
        vtext.setText(data.get(position).getValue().toString());


        vi.setTag(data.get(position));

        Button doneBtn = (Button)vi.findViewById(R.id.habit_done);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Core.addKarma( ((Habit)vi.getTag()).Name, ((Habit)vi.getTag()).Value  * (((Habit)vi.getTag()).Positive ? 1 : -1));

            }
        });



        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.setMinimumHeight((int)(view.getHeight() * 2));
                final View v =((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.habit_dialog,null);

                EditText name = (EditText) v.findViewById(R.id.habit_dialog_name);
                name.setText(((Habit) vi.getTag()).Name);

                EditText value = (EditText) v.findViewById(R.id.habit_dialog_value);
                value.setText(((Habit)vi.getTag()).Value.toString());

                Switch positive = (Switch)v.findViewById(R.id.habit_dialog_positive);
                positive.setChecked(((Habit)vi.getTag()).Positive);

                final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                final AlertDialog diag = dialog.setView((v))
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText name = (EditText) v.findViewById(R.id.habit_dialog_name);
                                ((Habit)vi.getTag()).Name = name.getText().toString();

                                EditText value = (EditText) v.findViewById(R.id.habit_dialog_value);
                                ((Habit)vi.getTag()).Value = Integer.parseInt(value.getText().toString());

                                Switch positive = (Switch)v.findViewById(R.id.habit_dialog_positive);
                                ((Habit)vi.getTag()).Positive = positive.isChecked();

                                Habit h = ((Habit)vi.getTag());

                                Core.source.updateItem(h.getID() , h.getName() , h.getValue() , h.getPositive() , "habit");
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).setTitle("Edit habit").show();

                int w = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, context.getResources().getDimension(R.dimen.dialog_width), context.getResources().getDisplayMetrics());
                if(w < 0) w = -1;
                diag.getWindow().setLayout(w, -2);


                Button deleteBtn = (Button)v.findViewById(R.id.delete_habit);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder dialog2 = new AlertDialog.Builder(context);

                        dialog2.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                data.remove(vi.getTag());
                                diag.cancel();
                                Core.source.delete(((Habit)vi.getTag()).getID());
                                notifyDataSetChanged();

                            }
                        }).setNegativeButton("Cancel",null).setTitle("Are you sure?").setMessage("Deleting this item cannot be undone")
                                .show();
                    }
                });


            }
        });
        return vi;
    }
}
