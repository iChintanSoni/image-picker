package com.chintansoni.imagepicker_facebook

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.chintansoni.imagepicker_facebook.exception.FacebookLoginCancelledException
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.gson.Gson
import timber.log.Timber


object FacebookHelper {

    init {
        Timber.plant(Timber.DebugTree())
    }

    val mutableLiveData = MutableLiveData<List<DataItem>>()

    private val readPermissionList = listOf("public_profile, email, user_photos")
    private var mCallbackManager: CallbackManager? = null

    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }

    fun login(
        fragment: Fragment,
        onSuccess: (LoginResult) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        mCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logInWithReadPermissions(fragment, readPermissionList)
        LoginManager.getInstance().registerCallback(mCallbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResults: LoginResult) {
                    onSuccess.invoke(loginResults)
                }

                override fun onCancel() {
                    onFailure.invoke(FacebookLoginCancelledException)
                }

                override fun onError(e: FacebookException) {
                    onFailure.invoke(e)
                }
            })
    }

    fun getAlbums() {
        val request = GraphRequest.newGraphPathRequest(
            AccessToken.getCurrentAccessToken(),
            "/me/albums"
        ) {
            Timber.d(it.rawResponse)
            val albumsResponse = Gson().fromJson(it.rawResponse, AlbumsResponse::class.java)
            getAlbumCoverPhoto(albumsResponse?.data?.get(0)?.id ?: "")
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,cover_photo,count")
        request.parameters = parameters
        request.executeAsync()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    fun getAlbumCoverPhoto(id: String) {
        val params = Bundle()
        params.putString("type", "thumbnail")
        params.putBoolean("redirect", false)
/* make the API call */
        GraphRequest(
            AccessToken.getCurrentAccessToken(),
            "/${id}/picture",
            params,
            HttpMethod.GET,
            GraphRequest.Callback {
                Timber.d("Album picture: " + it.rawResponse)
                val albumPictureResponse =
                    Gson().fromJson(it.rawResponse, AlbumPictureResponse::class.java)
            }
        ).executeAsync()
    }
}