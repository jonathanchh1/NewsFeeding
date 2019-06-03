package com.emi.newsfeeding

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface NewsFeedDao {
    @Query("SELECT * FROM table_news ORDER BY title ASC")
     fun getDatabaseNews() : LiveData<List<NewsFeed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news : NewsFeed)

    @Query("DELETE FROM table_news")
    fun deleteAll()

}