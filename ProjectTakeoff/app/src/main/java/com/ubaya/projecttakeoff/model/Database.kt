package com.ubaya.projecttakeoff.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubaya.projecttakeoff.util.DB_NAME
import com.ubaya.projecttakeoff.util.MIGRATION_1_2

@Database(entities = arrayOf(Article::class, User::class), version = 2)
abstract class TakeoffDatabase: RoomDatabase(){
    abstract fun articleDAO(): ArticleDAO
    abstract fun userDAO(): UserDAO

    companion object{
        @Volatile private var instance: TakeoffDatabase?= null
        private val LOCK = Any()

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TakeoffDatabase::class.java,
                DB_NAME
                // MIGRATIONS
            ).addMigrations(MIGRATION_1_2)
            .build()

        operator fun invoke(context: Context){
            if(instance !=null){
                synchronized(LOCK){
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}