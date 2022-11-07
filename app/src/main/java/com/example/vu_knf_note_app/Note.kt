package com.example.vu_knf_note_app

import io.realm.RealmObject

open class Note : RealmObject() {
    var title: String? = null
    var description: String? = null
    var createdTime: Long = 0
}