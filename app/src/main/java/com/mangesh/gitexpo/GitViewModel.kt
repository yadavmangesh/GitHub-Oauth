package com.mangesh.gitexpo

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mangesh.gitexpo.Pojo.Contributor

class GitViewModel(application: Application) :AndroidViewModel(application) {

    var publicUserList:LiveData<List<Contributor>>? =null

    var contributorList:MutableLiveData<MutableList<Contributor>> = MutableLiveData()

    private val repository:Repository = Repository(UserDataBase.getAppDataBase(application)?.userDao())

    init {
        repository.getListofUser(1,200)
    }

     fun getList(){
         publicUserList=repository.getUserList()
     }

    fun getContributorsList(ownerName:String?,repoName:String?){
       contributorList = repository.getContributorList(ownerName,repoName)
    }

    fun search(p0: String?) {
        publicUserList=repository.getFilterUser(p0)
    }


}