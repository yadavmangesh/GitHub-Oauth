package com.mangesh.gitexpo

import com.mangesh.gitexpo.Pojo.Contributor
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("/users")
    fun getUsers(@Query("since")id:Int,
                 @Query("per_page") limit:Int):Call<MutableList<Contributor>>
    @GET("/repos/{ownerName}/{repoName}/contributors")
    fun getListofContributors(@Path("ownerName") ownerName:String?, @Path("repoName") repoName:String?):Call<MutableList<Contributor>>
}