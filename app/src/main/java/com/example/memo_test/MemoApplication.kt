package com.example.memo_test
import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}