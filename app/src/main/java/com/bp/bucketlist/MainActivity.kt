package com.bp.bucketlist

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.bp.bucketlist.ui.viewmodel.BucketViewModel
import com.bp.bucketlist.adapter.BucketRepository
import com.bp.bucketlist.data.BucketDatabase
import com.bp.bucketlist.ui.screens.MainScreen
import com.bp.bucketlist.ui.theme.BucketListTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = BucketDatabase.getInstance(applicationContext)
        val repository = BucketRepository(db.bucketItemDao())
        val viewModel = BucketViewModel(repository)

        setContent {
            BucketListTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
