package com.leonardociocan.androidkarma.Todo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.leonardociocan.androidkarma.Core;
import com.leonardociocan.androidkarma.R;

import java.util.ArrayList;

public class TodoHubFragment extends Fragment{

    ListView listView;
    ArrayList<Todo> list = new ArrayList<Todo>();
    TodoListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.todo_hub_fragment, container, false);
        Bundle args = getArguments();
        //((RadioButton) rootView.findViewById(R.id.radioButton)).setText(
        //       Integer.toString(args.getInt(ARG_OBJECT)));



        final EditText text = (EditText) rootView.findViewById(R.id.newTodoBox);
        text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;

                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    Toast.makeText(getActivity() ,"hello",Toast.LENGTH_LONG).show();
                    Todo td = new Todo(text.getText().toString());
                    Core.Todos.add(td);
                    Core.source.addItem(td.getName() , td.getValue() , td.getPositive() , "todo");
                    adapter.notifyDataSetChanged();
                    text.setText("");
                }
                return false;
            }
        });

        listView = (ListView)rootView.findViewById(R.id.todo_list);
        adapter =new TodoListAdapter(getActivity());
        listView.setAdapter(adapter);
        return rootView;
    }
}
