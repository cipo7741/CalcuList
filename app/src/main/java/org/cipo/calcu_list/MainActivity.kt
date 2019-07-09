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
import org.cipo.calcu_list.db.Entry
import org.cipo.calcu_list.db.decodeIntAsString
import org.cipo.calcu_list.db.encodeDoubleStringAsInt
import java.lang.NumberFormatException


class MainActivity : AppCompatActivity() {
    private lateinit var entryViewModel: EntryViewModel

    private lateinit var editTextValue: EditText

    enum class Operation {
        total,mean,min,max,sd
    }

    private var results: Results = Results(0,0,0,0,0)
    private var countSelected: Int = 0
    private var countAll: Int = 0

    val fragManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = EntryListAdapter(this) { entry: Entry -> itemClicked(entry) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        entryViewModel = ViewModelProviders.of(this).get(EntryViewModel::class.java)
        entryViewModel.allEntries.observe(this, Observer { entries ->
            entries?.reversed().let { adapter.setEntries(it as List<Entry>) }
            countAll = adapter.itemCount
            if (countAll == 0) {
                // TODO
//                val textView = findViewById<TextView>(R.id.textViewResult)
//                textView.text = decodeIntAsString(0, 2)
                results = Results(0,0,0,0,0)

                val calculationFragment = CalculationFragment("Total", decodeIntAsString(results.total))
                fragManager.beginTransaction().add(R.id.calculation_container,calculationFragment).commit()
            }

        })

        /*load the count of items done from the database*/
        entryViewModel.countSelected.observe(
            this, Observer { count ->
                count?.let {
                    countSelected = it
                }
            }
        )

        editTextValue = findViewById(R.id.editTextValue)
        val textViewWord = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextViewName)

        /* keyboard input "Enter" */
        editTextValue.setOnEditorActionListener { _, actionId, _ ->
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


        /*load the total of the values from the database*/
        entryViewModel.total.observeForever { sum ->
            sum?.let {
                results.total = it
                // TODO

                val calculationFragment = CalculationFragment("Total", decodeIntAsString(results.total))
                fragManager.beginTransaction()
                    .add(R.id.calculation_container,calculationFragment)
                    .commit()
//                val textView = findViewById<TextView>(R.id.textViewResult)
//                textView.text = decodeIntAsString(total, 2)
            }
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
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_delete_sweep -> {
                clearSelectedWithApproval()
                return true
            }
            R.id.action_select_all -> {
                allEntriesSelected()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun allEntriesSelected() {
        if (countAll == countSelected) {
            entryViewModel.updateSelectedAll()
        } else {
            entryViewModel.updateSelectedAllTrue()
        }
    }

    private fun clearSelectedWithApproval() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.dialog_delete_all_selected_items)
            .setMessage(R.string.dialog_explanation)
            .setPositiveButton(
                R.string.dialog_yes
            ) { _, _ ->
                clearSelected()
                Toast.makeText(applicationContext, getString(R.string.deleted), Toast.LENGTH_LONG).show()
            }
            .setNegativeButton(
                R.string.dialog_no
            ) { _, _ ->
                Toast.makeText(applicationContext, getString(R.string.nothing_happend), Toast.LENGTH_LONG).show()
            }
            .show()
    }

    private fun clearSelected() {
        entryViewModel.deleteSelected()
    }

    private fun itemClicked(entry: Entry) {
        Toast.makeText(this, "Total: ${results.total}", Toast.LENGTH_SHORT).show()
        entryViewModel.updateSelected(entry.id)
    }

}


