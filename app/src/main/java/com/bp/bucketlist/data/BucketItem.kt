package com.bp.bucketlist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bucket_items")
data class BucketItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val category: String,
    val targetDate: String,
    val priority: Int,
    val status: Boolean = false,
    val completedAt: String? = null
)