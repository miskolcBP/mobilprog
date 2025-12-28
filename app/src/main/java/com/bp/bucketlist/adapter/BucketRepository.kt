package com.bp.bucketlist.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import com.bp.bucketlist.data.BucketItem
import com.bp.bucketlist.data.BucketItemDao
import java.time.LocalDate

class BucketRepository(
    private val dao: BucketItemDao
) {

    suspend fun getActive() = dao.getActiveItems()

    suspend fun getCompleted() = dao.getCompletedItems()

    suspend fun add(item: BucketItem) {
        dao.insert(item.copy(status = false))
    }

    suspend fun update(item: BucketItem) {
        dao.update(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun complete(id: Long) {
        val date = LocalDate.now().toString()
        dao.markCompleted(id, date)
    }

    suspend fun delete(item: BucketItem) {
        dao.delete(item)
    }
}
