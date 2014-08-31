package com.leonardociocan.androidkarma.Reward;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.leonardociocan.androidkarma.Core;
import com.leonardociocan.androidkarma.Reward.Reward;
import com.leonardociocan.androidkarma.R;

import java.util.ArrayList;


class RewardListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Reward> data;
    private static LayoutInflater inflater = null;

    public RewardListAdapter(Context context) {
        this.context = context;
        this.data = Core.Rewards;
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
        final View vi = inflater.inflate(R.layout.reward_row, null);
        //if (vi == null)
        //  vi = inflater.inflate(R.layout.Reward_row, null);


        TextView text = (TextView) vi.findViewById(R.id.reward_list_row_text);
        text.setText(data.get(position).getName());

        TextView vtext = (TextView) vi.findViewById(R.id.reward_list_row_value);
        vtext.setText(data.get(position).getValue().toString());


        vi.setTag(data.get(position));

        Button doneBtn = (Button)vi.findViewById(R.id.reward_done);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Core.getKarma() - ((Reward) vi.getTag()).Value >= 0 ) {
                    Core.addKarma(((Reward) vi.getTag()).Name, ((Reward) vi.getTag()).Value * -1);
                }
                else{
                    AlertDialog.Builder diag = new AlertDialog.Builder(context);
                    diag.setMessage("You will need to earn more karma before you can afford this reward.")
                            .setTitle("Nope").setPositiveButton("Got it",null).show();
                }

                //Core.setKarma(Core.getKarma()  + ((Reward)vi.getTag()).Value  * (((Reward)vi.getTag()).Positive ? 1 : -1));
            }
        });



        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.setMinimumHeight((int)(view.getHeight() * 2));
                final View v =((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.reward_dialog,null);

                EditText name = (EditText) v.findViewById(R.id.reward_dialog_name);
                name.setText(((Reward) vi.getTag()).Name);

                EditText value = (EditText) v.findViewById(R.id.reward_dialog_value);
                value.setText(((Reward)vi.getTag()).Value.toString());

                final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                final AlertDialog diag = dialog.setView((v))
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText name = (EditText) v.findViewById(R.id.reward_dialog_name);
                                ((Reward)vi.getTag()).Name = name.getText().toString();

                                EditText value = (EditText) v.findViewById(R.id.reward_dialog_value);
                                ((Reward)vi.getTag()).Value = Integer.parseInt(value.getText().toString());



                                Reward h = ((Reward)vi.getTag());

                                Core.source.updateItem(h.getID() , h.getName() , h.getValue() , true , "reward");
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).setTitle("Edit Reward").show();
                int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, context.getResources().getDimension(R.dimen.dialog_width), context.getResources().getDisplayMetrics());
                if(w < 0) w = -1;
                diag.getWindow().setLayout(w, -2);


                Button deleteBtn = (Button)v.findViewById(R.id.delete_reward);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder dialog2 = new AlertDialog.Builder(context);

                        dialog2.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                data.remove(vi.getTag());
                                diag.cancel();
                                Core.source.delete(((Reward)vi.getTag()).getID());
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
