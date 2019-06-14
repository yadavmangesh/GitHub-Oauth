package com.mangesh.gitexpo

import com.mangesh.gitexpo.Pojo.AuthToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface Login {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    fun accessToken(@Field("client_id") clientId: String,
                    @Field("client_secret") clientSecret: String,
                    @Field("code") code:String): Call<AuthToken>
}