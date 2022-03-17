package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataBaseNews::class], version = 11)

abstract class NewsDataBase:RoomDatabase() {

    abstract val newsDataBaseDao:DataBaseDao

    companion object{
        @Volatile
        private var INSTANCE:NewsDataBase? = null

        fun getDatabase(context: Context): NewsDataBase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDataBase::class.java,
                        "news_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}