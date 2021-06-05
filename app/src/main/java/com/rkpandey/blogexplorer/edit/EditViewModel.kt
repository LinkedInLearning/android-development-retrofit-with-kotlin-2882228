package com.rkpandey.blogexplorer.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkpandey.blogexplorer.api.RetrofitInstance
import com.rkpandey.blogexplorer.models.Post
import kotlinx.coroutines.launch
import java.lang.Exception

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
            try {
                _post.value = null
                _currentStatus.value = ResultStatus.WORKING
                val updatedPost = RetrofitInstance.api.updatePost(postId, newPostData)
                Log.i(TAG, "updated post $updatedPost")
                _post.value = updatedPost
                _currentStatus.value = ResultStatus.SUCCESS
            } catch (e: Exception) {
                _currentStatus.value = ResultStatus.ERROR
            }
        }
    }

    fun patchPost(postId: Int, title: String, body: String) {
        viewModelScope.launch {
            try {
                _post.value = null
                _currentStatus.value = ResultStatus.WORKING
                val patchedPost = RetrofitInstance.api.patchPost(postId, mapOf("title" to title, "body" to body))
                Log.i(TAG, "patched post $patchedPost")
                _post.value = patchedPost
                _currentStatus.value = ResultStatus.SUCCESS
            } catch (e: Exception) {
                _currentStatus.value = ResultStatus.ERROR
            }
        }
    }

    fun deletePost(postId: Int) {
        // TODO: send DELETE request
    }
}