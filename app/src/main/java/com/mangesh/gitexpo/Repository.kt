package com.mangesh.gitexpo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.mangesh.gitexpo.Pojo.Contributor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(val userDao: UserDao?) {

    private  var api:Api?

    private var liveContributorList:MutableLiveData<MutableList<Contributor>> = MutableLiveData()


    init {
        api= RetroClient.getClient()
    }

    fun getListofUser(id:Int,limit:Int){

        api?.getUsers(id,limit)?.enqueue(object :retrofit2.Callback<MutableList<Contributor>>{
            override fun onFailure(call: Call<MutableList<Contributor>>, t: Throwable) {
               Log.d("Repository ","getUsers onFailure")
            }

            override fun onResponse(call: Call<MutableList<Contributor>>, response: Response<MutableList<Contributor>>) {


                val list=response.body()
                Log.d("","onResponse: ${list?.size}")
                Thread(Runnable {
                    userDao?.insertUser(list)
                }).start()

            }

        })
    }

    fun getContributorList(ownerName:String?,repoName:String?):MutableLiveData<MutableList<Contributor>>{

        api?.getListofContributors(ownerName,repoName)?.enqueue(object :Callback<MutableList<Contributor>>{
            override fun onFailure(call: Call<MutableList<Contributor>>, t: Throwable) {

                Log.d("Repository ","getListofContributors onFailure")
            }

            override fun onResponse(call: Call<MutableList<Contributor>>, response: Response<MutableList<Contributor>>) {

                val list=response.body()
                liveContributorList.value=list

                Log.d("Repository ","getListofContributors onResponse")
            }

        })

        return liveContributorList
    }

    fun getUserList(): LiveData<List<Contributor>>? {
       return userDao?.getUerList()
    }

    fun getFilterUser(p0: String?): LiveData<List<Contributor>>? {
        return userDao?.searchUser(p0)
    }
}