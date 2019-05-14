package org.cipo.calcu_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<EntryListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var entries = emptyList<Entry>() // Cached copy of entries

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemViewLeft: TextView = itemView.findViewById(R.id.textView_recyclerview_item_left)
        val itemViewRight: TextView = itemView.findViewById(R.id.textView_recyclerview_item_right)


//        fun bind(item: Entry, listener: (Entry) -> Unit) = with(itemView) {
//            itemViewLeft.text = item.word
//            itemViewRight.text = item.value
//            setOnClickListener { listener(item) }
//        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = entries[position]
        holder.itemViewLeft.text = current.word
        holder.itemViewRight.text = current.value.toString()
    }

    internal fun setEntries(entries: List<Entry>) {
        this.entries = entries
        notifyDataSetChanged()
    }

    override fun getItemCount() = entries.size

}