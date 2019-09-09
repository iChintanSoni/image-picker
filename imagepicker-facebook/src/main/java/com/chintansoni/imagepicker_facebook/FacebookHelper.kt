package com.chintansoni.imagepicker_facebook

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import com.chintansoni.imagepicker_facebook.exception.FacebookLoginCancelledException
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.gson.Gson
import timber.log.Timber
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object FacebookHelper {

    init {
        Timber.plant(Timber.DebugTree())
    }

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

    fun getAlbums(onApiStatus: (AlbumApiStatus) -> Unit) {
        onApiStatus.invoke(AlbumApiStatus.Loading)
        val request = GraphRequest.newGraphPathRequest(
            AccessToken.getCurrentAccessToken(),
            "/me/albums"
        ) { graphResponse ->
            graphResponse.error?.exception?.let {
                onApiStatus.invoke(AlbumApiStatus.Failure(it))
            }
            graphResponse.rawResponse?.let {
                val albumsResponse =
                    Gson().fromJson(graphResponse.rawResponse, AlbumsResponse::class.java)
                onApiStatus.invoke(AlbumApiStatus.Success(albumsResponse))
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,count")
        request.parameters = parameters
        request.executeAsync()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    fun getAlbumCoverPhoto(dataItem: DataItem, onCoverApi: () -> Unit) {
        if (dataItem.albumCoverApiStatus == AlbumCoverApiStatus.Idle) {
            dataItem.albumCoverApiStatus = AlbumCoverApiStatus.Loading
            val params = Bundle()
            params.putString("type", "album")
            params.putBoolean("redirect", false)
            GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/${dataItem.id}/picture",
                params,
                HttpMethod.GET,
                GraphRequest.Callback { graphResponse ->
                    graphResponse.error?.exception?.let {
                        dataItem.albumCoverApiStatus = AlbumCoverApiStatus.Failure(it)
                    }
                    graphResponse.rawResponse?.let {
                        val albumPictureResponse =
                            Gson().fromJson(
                                graphResponse.rawResponse,
                                AlbumPictureResponse::class.java
                            )
                        dataItem.url = albumPictureResponse.data.url
                        dataItem.albumCoverApiStatus =
                            AlbumCoverApiStatus.Success(albumPictureResponse)
                        onCoverApi()
                    }
                }
            ).executeAsync()
        }
    }

    fun generateHashKey(context: Context) {
        try {
            val info = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }

    }
}