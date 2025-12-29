package com.bp.bucketlist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BucketItemDao {

    @Insert
    suspend fun insert(item: BucketItem)

    @Update
    suspend fun update(item: BucketItem)

    @Delete
    suspend fun delete(item: BucketItem)

    @Query("SELECT * FROM bucket_items WHERE status = 0 ORDER BY priority DESC")
    suspend fun getActiveItems(): List<BucketItem>

    @Query("SELECT * FROM bucket_items WHERE status = 1 ORDER BY priority DESC")
    suspend fun getCompletedItems(): List<BucketItem>

    @Query("UPDATE bucket_items SET status = 1, completedAt = :completedAt WHERE id = :id")
    suspend fun markCompleted(id: Long, completedAt: String)

}

