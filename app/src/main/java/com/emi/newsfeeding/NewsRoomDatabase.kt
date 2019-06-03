package com.emi.newsfeeding

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [NewsFeed::class], version = 1)
abstract class NewsRoomDatabase : RoomDatabase() {


    abstract fun newsDao() : NewsFeedDao

    companion object{
        @Volatile
        private var INSTANCE : NewsRoomDatabase?=null
        fun getDatabase(context : Context, scope : CoroutineScope) : NewsRoomDatabase{
            return INSTANCE?: synchronized(this){

                 val instance = Room.databaseBuilder(
                     context.applicationContext,
                     NewsRoomDatabase::class.java,
                     context.getString(R.string.database_name)
                 )
                     .fallbackToDestructiveMigration()
                     .addCallback(NewsRoomDatabaseCallback(scope))
                     .build()

                INSTANCE = instance
                instance
            }
        }

        class NewsRoomDatabaseCallback(var scope : CoroutineScope) : RoomDatabase.Callback(){

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.let {
                    database ->
                    scope.launch(Dispatchers.IO){
                        populateDatabase(database.newsDao())
                    }
                }
            }
        }

        private fun populateDatabase(newsDao : NewsFeedDao){
            newsDao.deleteAll()
        }
    }

}