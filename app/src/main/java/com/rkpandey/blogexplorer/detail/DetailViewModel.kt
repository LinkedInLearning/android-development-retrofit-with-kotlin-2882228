package com.rkpandey.blogexplorer.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkpandey.blogexplorer.api.RetrofitInstance
import com.rkpandey.blogexplorer.models.Post
import com.rkpandey.blogexplorer.models.User
import kotlinx.coroutines.launch

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
        val api = RetrofitInstance.api
        viewModelScope.launch {
            _isLoading.value = true
            val fetchedPost = api.getPost(postId)
            val fetchedUser = api.getUser(fetchedPost.userId)
            Log.i(TAG, "Fetched user $fetchedUser")
            _post.value = fetchedPost
            _user.value = fetchedUser
            _isLoading.value = false
        }
    }
}