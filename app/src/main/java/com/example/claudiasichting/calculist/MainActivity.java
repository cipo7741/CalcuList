package com.example.claudiasichting.calculist;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends ListActivity {


    //private List exampleListItemList;

    //public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    ArrayList<Pair<String,String>> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = (EditText) findViewById(R.id.editTextValue);

        //entries.add(Pair.create("Sum", "0"));

        EntryAdapter adapter1 = new EntryAdapter(this, entries);
        ListView listView = (ListView) findViewById(android.R.id.list);
        setListAdapter(adapter1);

        TextView textView = findViewById(R.id.textViewResult);
        textView.setText(String.valueOf(0.0));

        editText.setOnKeyListener(new EditText.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press

                    sendMessage(v);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
        EditText editTextKey = (EditText) findViewById(R.id.editTextKey);
        EditText editTextValue = (EditText) findViewById(R.id.editTextValue);

        entries.add(0, Pair.create(editTextKey.getText().toString(), editTextValue.getText().toString()));

        float sum = 0;
        for(Pair<String, String> entry :entries.subList(0, entries.size())){
            sum += Float.parseFloat(entry.second);
        }

        //entries.set(entries.size()-1,Pair.create( getString(R.string.operation), String.valueOf(sum)));

        TextView textView = findViewById(R.id.textViewResult);
        textView.setText(String.valueOf(sum));

        Toast.makeText(MainActivity.this, editTextKey.getText() + " " + editTextValue.getText().toString(), Toast.LENGTH_LONG).show();

        EntryAdapter adapter1 = new EntryAdapter(this, entries);
        ListView listView = (ListView) findViewById(android.R.id.list);
        setListAdapter(adapter1);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Pair<String,String> item = entries.get(position);
        Toast.makeText(this, item.first + " " + item.second + " selected", Toast.LENGTH_LONG).show();
    }

}
