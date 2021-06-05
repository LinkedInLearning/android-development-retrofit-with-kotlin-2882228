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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        // Coroutine style
        viewModelScope.launch {
            _isLoading.value = true
            val fetchedPost = api.getPost(postId)
            val fetchedUser = api.getUser(fetchedPost.userId)
            Log.i(TAG, "Fetched user $fetchedUser")
            _post.value = fetchedPost
            _user.value = fetchedUser
            _isLoading.value = false
        }

        // Callback style (alternative)
        // fetchDataCallbackStyle(postId)
    }

    private fun fetchDataCallbackStyle(postId: Int) {
        val api = RetrofitInstance.api
        _isLoading.value = true
        api.getPostViaCallback(postId).enqueue(object: Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e(TAG, "onFailure $t")
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val fetchedPost = response.body()!!
                    Log.i(TAG, "onResponse post $fetchedPost")
                    api.getUserViaCallback(fetchedPost.userId).enqueue(object: Callback<User> {
                        override fun onFailure(call: Call<User>, t: Throwable) {
                            Log.e(TAG, "onFailure $t")
                        }

                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            if (response.isSuccessful) {
                                val fetchedUser = response.body()!!
                                _post.value = fetchedPost
                                _user.value = fetchedUser
                                _isLoading.value = false
                                Log.i(TAG, "onResponse user $fetchedUser")
                            }
                            Log.e(TAG, "response unsuccessful, code ${response.code()}")
                        }
                    })
                } else {
                    Log.e(TAG, "response unsuccessful, code ${response.code()}")
                }
            }
        })
    }
}