package com.mangesh.gitexpo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.mangesh.gitexpo.Pojo.Contributor

@Dao
interface UserDao {

    @Query("select * from Contributor")
    fun getUerList():LiveData<List<Contributor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(list: MutableList<Contributor>?)

    @Query("select * from Contributor where login like :name limit 10")
    fun searchUser(name:String?):LiveData<List<Contributor>>
}