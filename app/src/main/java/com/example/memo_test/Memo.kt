package com.example.memo_test

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class Memo : RealmObject() {

    var name : String = ""
}