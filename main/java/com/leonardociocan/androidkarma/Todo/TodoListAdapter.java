package com.leonardociocan.androidkarma.Todo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.leonardociocan.androidkarma.Core;
import com.leonardociocan.androidkarma.Habit.Habit;
import com.leonardociocan.androidkarma.R;

import java.util.ArrayList;


class TodoListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Todo> data;
    private static LayoutInflater inflater = null;

    public TodoListAdapter(Context context) {
        this.context = context;
        this.data = Core.Todos;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final View vi = inflater.inflate(R.layout.todo_row, null);
        //if (vi == null)
        //  vi = inflater.inflate(R.layout.Todo_row, null);

        vi.setTag(data.get(position));

        final CheckBox positive = (CheckBox)vi.findViewById(R.id.todo_checkbox);
        positive.setChecked(((Todo)vi.getTag()).Positive);

        positive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Todo h =  ((Todo) vi.getTag());
                h.Positive = (positive.isChecked());
                Core.source.updateItem(h.getID() , h.getName() , h.getValue() , h.Positive , "todo");
                Core.setKarma(Core.getKarma()  + ((Todo)vi.getTag()).Value  * (((Todo)vi.getTag()).Positive ? 1 : -1));
            }
        });

        TextView text = (TextView) vi.findViewById(R.id.todo_list_row_text);
        text.setText(data.get(position).getName());

        TextView vtext = (TextView) vi.findViewById(R.id.todo_list_row_value);
        vtext.setText(data.get(position).getValue().toString());







        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.setMinimumHeight((int)(view.getHeight() * 2));
                final View v =((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.todo_dialog,null);

                EditText name = (EditText) v.findViewById(R.id.todo_dialog_name);
                name.setText(((Todo) vi.getTag()).Name);

                EditText value = (EditText) v.findViewById(R.id.todo_dialog_value);
                value.setText(((Todo) vi.getTag()).Value.toString());



                final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                final AlertDialog diag = dialog.setView((v))
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText name = (EditText) v.findViewById(R.id.todo_dialog_name);
                                ((Todo)vi.getTag()).Name = name.getText().toString();

                                EditText value = (EditText) v.findViewById(R.id.todo_dialog_value);
                                ((Todo)vi.getTag()).Value = Integer.parseInt(value.getText().toString());



                                Todo h = ((Todo)vi.getTag());

                                Core.source.updateItem(h.getID() , h.getName() , h.getValue() , true , "todo");
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).setTitle("Edit Todo").show();


                Button deleteBtn = (Button)v.findViewById(R.id.delete_todo);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder dialog2 = new AlertDialog.Builder(context);

                        dialog2.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                data.remove(vi.getTag());
                                diag.cancel();
                                Core.source.delete(((Todo)vi.getTag()).getID());
                                notifyDataSetChanged();

                            }
                        }).setNegativeButton("Cancel",null).setTitle("Are you sure?").setMessage("Deleting this item cannot be undone").show();
                    }
                });


            }
        });
        return vi;
    }
}
