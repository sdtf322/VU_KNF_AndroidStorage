package com.example.vu_knf_note_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import java.text.DateFormat

class NoteAdapter(var context: Context, var notesList: RealmResults<Note>) :
    RecyclerView.Adapter<NoteAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = notesList[position]

        holder.titleOutput.setText(note!!.title)
        holder.descriptionOutput.setText(note.description)

        val formatedTime = DateFormat.getDateTimeInstance().format(note.createdTime)

        holder.timeOutput.text = formatedTime
        holder.itemView.setOnLongClickListener { v ->
            val menu = PopupMenu(context, v)
            menu.menu.add("DELETE")
            menu.setOnMenuItemClickListener { item ->
                if (item.title == "DELETE") {
                    val realm = Realm.getDefaultInstance()
                    realm.beginTransaction()
                    note.deleteFromRealm()
                    realm.commitTransaction()
                    Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
                }
                true
            }
            menu.show()
            true
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleOutput: TextView
        var descriptionOutput: TextView
        var timeOutput: TextView

        init {
            titleOutput = itemView.findViewById(R.id.titleoutput)
            descriptionOutput = itemView.findViewById(R.id.descriptionoutput)
            timeOutput = itemView.findViewById(R.id.timeoutput)
        }
    }
}