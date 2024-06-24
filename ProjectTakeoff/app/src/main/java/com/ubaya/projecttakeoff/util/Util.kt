package com.ubaya.projecttakeoff.util

import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ubaya.projecttakeoff.model.TakeoffDatabase

val DB_NAME = "takeoffdb"

fun buildDb(context: Context): TakeoffDatabase {
    val db = TakeoffDatabase.buildDatabase(context)
    return db
}

//MIGRATIONS HERE

//EXAMPLE

//val MIGRATION_1_2 = object: Migration(1, 2){
//    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 NOT NULL")
//    }
//
//}