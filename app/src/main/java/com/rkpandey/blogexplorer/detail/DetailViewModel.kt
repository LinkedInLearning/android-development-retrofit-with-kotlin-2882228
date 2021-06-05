package com.rkpandey.blogexplorer.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rkpandey.blogexplorer.models.Post
import com.rkpandey.blogexplorer.models.User

private const val TAG = "DetailViewModel"
class DetailViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post>
        get() = _post

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun getPostDetails(postId: Int) {
        // TODO: fill this in
    }
}