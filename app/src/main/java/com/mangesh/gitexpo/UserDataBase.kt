package com.mangesh.gitexpo

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.mangesh.gitexpo.Pojo.Contributor

@Database(entities = [Contributor::class],version = 2)
abstract class UserDataBase : RoomDatabase() {

   abstract fun userDao():UserDao

    companion object{

        var INSTANCE:UserDataBase?=null

        fun getAppDataBase(context: Context):UserDataBase?{
            if (INSTANCE==null){

                synchronized(UserDataBase::class.java){
                    INSTANCE=Room.databaseBuilder(context.applicationContext,
                                                   UserDataBase::class.java,
                                             "GitDb").build()
                }
            }

            return INSTANCE
        }

        fun distroyDbInstance(){
            INSTANCE==null
        }

    }


}