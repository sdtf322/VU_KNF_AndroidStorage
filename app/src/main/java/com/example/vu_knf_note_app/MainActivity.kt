package com.example.vu_knf_note_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addNoteBtn = findViewById<MaterialButton>(R.id.addnewnotebtn)

        addNoteBtn.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    AddNoteActivity::class.java
                )
            )
        }

        Realm.init(applicationContext)
        val realm = Realm.getDefaultInstance()

        val notesList: RealmResults<Note> =
            realm.where(Note::class.java).findAll()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val noteAdapter = NoteAdapter(applicationContext, notesList)
        recyclerView.adapter = noteAdapter

        notesList.addChangeListener(RealmChangeListener { noteAdapter.notifyDataSetChanged() })

    }
}