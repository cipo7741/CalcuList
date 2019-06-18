package org.cipo.calcu_list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode
import java.text.DecimalFormat

class EntryListAdapter internal constructor(
    context: Context,
//    private val clickListener: (Entry) -> Unit,
    private val buttonListener: (Entry) -> Unit)
: RecyclerView.Adapter<EntryListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var entries = emptyList<Entry>() // Cached copy of entries

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemViewLeft: TextView = itemView.findViewById(R.id.textView_recyclerview_item_left)
        private val itemViewRight: TextView = itemView.findViewById(R.id.textView_recyclerview_item_right)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.button_delete)

        fun bind(item: Entry, btnListener: (Entry) -> Unit) = with(itemView) {
            itemViewLeft.text = item.word
            itemViewRight.text = decodeIntAsString(item.value!!, 2)
//            setOnClickListener { listener(item) }
            deleteButton.setOnClickListener { btnListener(item) }
//            deleteButton.setOnClickListener{
//                entries.drop(itemCount - itemView.id.toInt())
//                Log.e("e", itemView.id.toString())
//            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)

        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        // Populate ViewHolder with data that corresponds to the position in the list
        // which we are told to load
        (holder as WordViewHolder).bind(entries[position], buttonListener)
//        (holder as WordViewHolder).bind(entries[position], clickListener, buttonListener)
    }

    internal fun setEntries(entries: List<Entry>) {
        this.entries = entries
        notifyDataSetChanged()
    }

    override fun getItemCount() = entries.size



}