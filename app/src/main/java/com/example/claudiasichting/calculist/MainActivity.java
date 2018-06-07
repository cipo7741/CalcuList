package com.example.claudiasichting.calculist;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends ListActivity {


    //private List exampleListItemList;

    //public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    ArrayList<Pair<String, String>> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        EditText editText = (EditText) findViewById(R.id.editTextValue);

        System.out.println("Hello ");

        ListView listView = (ListView) findViewById(android.R.id.list);


        TextView textView = findViewById(R.id.textViewResult);

        int sum = 0;

        try {
            FileInputStream in = openFileInput("clacu_list_table_file.txt");
            entries.clear();
            Reader reader = new InputStreamReader(in, Charset.defaultCharset());
            // buffer for efficiency
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            while ((line = buffer.readLine()) != null) {

                System.out.println("Do something with " + line);
                String[] values = line.split(",");
                entries.add(new Pair<String, String>(values[0], values[1]));
                sum += 100 * Float.parseFloat(values[1]);
            }
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            Log.d("e","FileNotFoundException");
        } catch (IOException e) {
            Log.d("e","IOException");
        }

        EntryAdapter adapter1 = new EntryAdapter(this, entries);
        setListAdapter(adapter1);
        textView.setText(String.valueOf(sum/100.0));

        editText.setOnKeyListener(new EditText.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // If the event is a key-down event on the "enter" button
                if (((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) || (keyCode == EditorInfo.IME_ACTION_DONE) ) {
                    // Perform action on key press

                    sendMessage(v);
                    return true;
                }
                return false;
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("e","Enter pressed");
                }
                return false;
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                  /* Write your logic here that will be executed when user taps next button */

                    sendMessage(v);
                    handled = true;
                }
                return handled;
            }
        });

    }



    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
        EditText editTextKey = (EditText) findViewById(R.id.editTextKey);
        EditText editTextValue = (EditText) findViewById(R.id.editTextValue);

        try {
            Float.parseFloat(editTextValue.getText().toString());

            entries.add(0, Pair.create(editTextKey.getText().toString(), editTextValue.getText().toString()));

            float sum = 0;
            for (Pair<String, String> entry : entries.subList(0, entries.size())) {
                sum += Float.parseFloat(entry.second);
            }

            TextView textView = findViewById(R.id.textViewResult);
            textView.setText(String.valueOf(sum));

            EntryAdapter adapter1 = new EntryAdapter(this, entries);
            ListView listView = (ListView) findViewById(android.R.id.list);
            setListAdapter(adapter1);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please input valid number like 4.02 or 4,02", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Pair<String, String> item = entries.get(position);
        Toast.makeText(this, item.first + " " + item.second + " selected", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

        String filename = "clacu_list_table_file.txt";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            PrintStream printStream = new PrintStream(outputStream);
            for(Pair<String, String> entry : entries){
                printStream.print(entry.first);
                printStream.print(",");
                printStream.println(entry.second);
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



//        String filename = "myfile";
//        String fileContents = "Hello world!";
//        FileOutputStream outputStream;
//
//        try {
//            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//            outputStream.write(fileContents.getBytes());
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }



//        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        for (int i = 0; i < entries.size(); i++) {
//            Set<String> stringSet = new TreeSet<String>(Arrays.asList(entries.get(i).first, entries.get(i).second));
//            editor.putStringSet(Integer.toString(i), stringSet);
//            editor.apply();
//        }

    }


}

