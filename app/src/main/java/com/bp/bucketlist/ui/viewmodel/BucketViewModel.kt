package com.bp.bucketlist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bp.bucketlist.adapter.BucketRepository
import com.bp.bucketlist.data.BucketItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BucketViewModel(
    private val repository: BucketRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<BucketItem>>(emptyList())
    val items: StateFlow<List<BucketItem>> = _items

    private var showingCompleted = false

    fun loadActive() {
        showingCompleted = false
        viewModelScope.launch {
            _items.value = repository.getActive()
        }
    }

    fun loadCompleted() {
        showingCompleted = true
        viewModelScope.launch {
            _items.value = repository.getCompleted()
        }
    }

    fun add(item: BucketItem) {
        viewModelScope.launch {
            repository.add(item)
            refresh()
        }
    }

    fun update(item: BucketItem) {
        viewModelScope.launch {
            repository.update(item)
            refresh()
        }
    }

    fun markCompleted(id: Long) {
        viewModelScope.launch {
            repository.complete(id)
            refresh()
        }
    }

    fun delete(item: BucketItem) {
        viewModelScope.launch {
            repository.delete(item)
            refresh()
        }
    }

    private fun refresh() {
        if (showingCompleted) loadCompleted() else loadActive()
    }
}
