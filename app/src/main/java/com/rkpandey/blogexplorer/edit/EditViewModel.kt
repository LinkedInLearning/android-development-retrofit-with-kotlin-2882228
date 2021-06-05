package com.rkpandey.blogexplorer.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkpandey.blogexplorer.api.RetrofitInstance
import com.rkpandey.blogexplorer.models.Post
import kotlinx.coroutines.launch

private const val TAG = "EditViewModel"
class EditViewModel : ViewModel() {
    private val _post: MutableLiveData<Post?> = MutableLiveData()
    val post: LiveData<Post?>
        get() = _post

    private val _currentStatus = MutableLiveData<ResultStatus>(ResultStatus.IDLE)
    val currentStatus: LiveData<ResultStatus>
        get() = _currentStatus

    fun updatePost(postId: Int, newPostData: Post) {
        viewModelScope.launch {
            _post.value = null
            val updatedPost = RetrofitInstance.api.updatePost(postId, newPostData)
            Log.i(TAG, "updated post $updatedPost")
            _post.value = updatedPost
        }
    }

    fun patchPost(postId: Int, title: String, body: String) {
        // TODO: send PATCH request
    }

    fun deletePost(postId: Int) {
        // TODO: send DELETE request
    }
}