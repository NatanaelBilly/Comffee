package com.example.comffee

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val activity: Activity) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private lateinit var tv_item_name: TextView
    private lateinit var tv_hist_date: TextView
    private lateinit var tv_item_description: TextView
    private lateinit var tv_item_price: TextView
    private lateinit var cv_item_hist: CardView

    var listHist = ArrayList<History>()
        set(listNotes) {
            if(listNotes.size > 0) {
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        tv_item_title = view.findViewById(R.id.tv_item_title)
        tv_item_date = view.findViewById(R.id.tv_item_date)
        tv_item_description = view.findViewById(R.id.tv_item_description)
        cv_item_note = view.findViewById(R.id.cv_item_note)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int {
        return this.listNotes.size
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            with(itemView) {
                tv_item_title.text = note.title
                tv_item_date.text = note.date
                tv_item_description.text = note.description

                cv_item_note.setOnClickListener(CustomOnItemClickListener(
                    adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                        override fun onItemClicked(view: View, position: Int) {
                            val intent = Intent(activity, NoteAddUpdateActivity::class.java)

                            intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
                            intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
                            activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE)
                        }
                    }))
            }
        }
    }

    fun addItem(note: Note) {
        this.listNotes.add(note)
        notifyItemInserted(this.listNotes.size - 1)
    }

    fun updateItem(position: Int, note: Note) {
        this.listNotes[position] = note
        notifyItemChanged(position, note)
    }

    fun removeItem(position: Int) {
        this.listNotes.removeAt(position)
        notifyItemChanged(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }
}