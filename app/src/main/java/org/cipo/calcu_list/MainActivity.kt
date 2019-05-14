package org.cipo.calcu_list

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.TextView
import kotlinx.android.synthetic.main.content_main.*
import java.lang.NumberFormatException


class MainActivity : AppCompatActivity() {

    private lateinit var entryViewModel: EntryViewModel

    private lateinit var editWordView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = EntryListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        entryViewModel = ViewModelProviders.of(this).get(EntryViewModel::class.java)

        entryViewModel.allEntries.observe(this, Observer { entries ->
            // Update the cached copy of the words in the adapter.
            entries?.reversed().let { adapter.setEntries(it as List<Entry>) }
        })

        editWordView = findViewById(R.id.editTextValue)
        val textViewWord = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewName)

        editWordView.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                Log.d("d", "Action pressed: $actionId")
                sendMessage()
//                editWordView.clearFocus()
                editWordView.text.clear()
                textViewWord.text.clear()
                textViewWord.requestFocus()
                handled = true
            }
            handled
        }

    }

    private fun sendMessage() = if (TextUtils.isEmpty(editWordView.text)) {
        Toast.makeText(applicationContext, "No Input", Toast.LENGTH_SHORT).show()
    } else {
        val word = autoCompleteTextViewName.text.toString()
        val valueString = editWordView.text.toString()
        try {
            val value = valueString.toInt()
            entryViewModel.insert(Entry(word, value))

            var sum = value

            entryViewModel.allEntries.observe(this, Observer { entries ->
                // Update the cached copy of the words in the adapter.
                entries?.forEach { it -> sum += it.value!! }
            })

            val textView = findViewById<TextView>(R.id.textViewResult)
            textView.text = sum.toString()

        } catch (e: NumberFormatException) {
            val msg = "String \"$valueString\" is not an acceptable number"
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
            Log.e("e", msg)

        }
    }

    //      TODO: menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //      TODO: settings
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_delete -> clearList()
            R.id.action_sort -> sortList()
            else -> super.onOptionsItemSelected(item)
        }


    }

    private fun sortList(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun clearList(): Boolean {
        entryViewModel.deleteAll()
        val textView = findViewById<TextView>(R.id.textViewResult)
        textView.text = "0"
        return entryViewModel.allEntries.value?.isEmpty()!!
    }


}


