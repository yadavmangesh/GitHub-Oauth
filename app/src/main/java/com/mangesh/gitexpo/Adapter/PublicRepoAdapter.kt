package com.mangesh.gitexpo.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mangesh.gitexpo.Pojo.Contributor
import com.mangesh.gitexpo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repo_item.view.*

class PublicRepoAdapter(val context: Context): RecyclerView.Adapter<PublicRepoAdapter.MyViewHolder>() {

    var publicRepoList:List<Contributor> = arrayListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.repo_item,p0,false))
    }

    override fun getItemCount(): Int {
      return publicRepoList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
       val user= publicRepoList[p1]

        p0.itemView.tvOwnerName.text=user.login
        Picasso.get().load(user?.avatarUrl).into(p0.itemView.ivOwnerImage)
    }

    fun setData(list: List<Contributor>){
        publicRepoList=list
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}