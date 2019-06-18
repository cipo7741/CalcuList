package org.cipo.calcu_list

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.content_main.*
import java.lang.NumberFormatException


class MainActivity : AppCompatActivity() {
    //, NoticeDialogFragment.NoticeDialogListener
    private lateinit var entryViewModel: EntryViewModel

    private lateinit var editTextValue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        /* load the recycler view with entries from the database*/
//        val adapter = EntryListAdapter(this, { entry : Entry -> itemClicked(entry)}, { entry : Entry -> itemDeleteClick(entry)})
        val adapter = EntryListAdapter(this, { entry : Entry -> itemDeleteClick(entry)})
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        entryViewModel = ViewModelProviders.of(this).get(EntryViewModel::class.java)
        entryViewModel.allEntries.observe(this, Observer { entries ->
            // Update the cached copy of the words in the adapter.
            entries?.reversed().let { adapter.setEntries(it as List<Entry>)}

        })



        /* show correct sum after loading default data */
//        var sum = 0
//        entryViewModel.allEntries.observe(this, Observer { entries ->
//            // Update the cached copy of the words in the adapter.
//            entries?.forEach { it -> sum += it.value!! }
//
//        })
//        val textView = findViewById<TextView>(R.id.textViewResult)
//        textView.text = sum.toString()

        editTextValue = findViewById(R.id.editTextValue)
        val textViewWord = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewName)

        /* keyboard input "Enter" */
        editTextValue.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendMessage()
                editTextValue.text.clear()
                textViewWord.text.clear()
                textViewWord.requestFocus()
                handled = true
            }
            handled
        }

    }

    private fun sendMessage() {
        /* show message if entry empty */
        if (TextUtils.isEmpty(editTextValue.text)) {
            Toast.makeText(applicationContext, R.string.toast_no_input, Toast.LENGTH_SHORT).show()
        } else {
            /* get entries for word and value */
            val entryLeft = autoCompleteTextViewName.text.toString()
            val entryRight = editTextValue.text.toString()
            try {
                /* fill databate */

                val value = encodeDoubleStringAsInt(entryRight, 2)
                entryViewModel.insert(Entry(entryLeft, value))

                /* calculate sum */
                var sum = value
                entryViewModel.allEntries.observe(this, Observer { entries ->
                    // Update the cached copy of the words in the adapter.
                    entries?.forEach { it -> sum += it.value!! }
                })
                /* fill result text view */
                val textView = findViewById<TextView>(R.id.textViewResult)
                textView.text = decodeIntAsString(sum, 2)
            } catch (e: NumberFormatException) {
                val msg = "String \"$entryRight\" is not an acceptable number"
                Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
                Log.e("e", msg)
            }
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
            R.id.action_delete -> {
                showdialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun clearList(): Boolean {
        entryViewModel.deleteAll()
        val textView = findViewById<TextView>(R.id.textViewResult)
        textView.text = "0"
        return entryViewModel.allEntries.value?.isEmpty()!!
    }

    fun showdialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.dialog_delete_all_items)
            .setMessage(R.string.dialog_delete_all_items_explanation)
            .setPositiveButton(R.string.dialog_delete_all_items_yes
            ) { dialog, i ->
                clearList()
                Toast.makeText(applicationContext, "List cleared!", Toast.LENGTH_LONG).show()
            }
            //set negative button
            .setNegativeButton(
                R.string.dialog_delete_all_items_no
            ) { dialogInterface, i ->
                Toast.makeText(applicationContext, "Nothing Happened", Toast.LENGTH_LONG).show()
            }
            .show()
    }

    private fun itemDeleteClick(entry: Entry) {
        Toast.makeText(this, "Cleared: ${entry.word}", Toast.LENGTH_SHORT).show()
        entryViewModel.delete(entry)
    }

//    private fun itemClicked(entry : Entry) {
//        Toast.makeText(this, "Clicked: ${entry.word}", Toast.LENGTH_LONG).show()
////        entryViewModel.delete(entry)
//    }

}


