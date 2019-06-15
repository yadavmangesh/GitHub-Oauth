package com.mangesh.gitexpo.Activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.mangesh.gitexpo.Adapter.PublicRepoAdapter
import com.mangesh.gitexpo.GitViewModel
import com.mangesh.gitexpo.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.view.MenuItemCompat.getActionView
import android.content.Context.SEARCH_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.app.SearchManager
import android.view.MenuInflater
import android.content.Context
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() {

    private lateinit var gitViewModel: GitViewModel

    private  var publicRepoAdapter: PublicRepoAdapter

    var searchItem:MenuItem?=null

    var searchView: SearchView? = null

    init {
        publicRepoAdapter=PublicRepoAdapter(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         setSupportActionBar(toolbar)

        rvChat.apply {
            layoutManager=LinearLayoutManager(this@MainActivity)
            adapter=publicRepoAdapter
        }

        gitViewModel=ViewModelProviders.of(this).get(GitViewModel::class.java)

         gitViewModel.getList()

        gitViewModel.publicUserList?.observe(this, Observer {
            list->list?.let {
            publicRepoAdapter.setData(it) }
        })

    }

   override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(com.mangesh.gitexpo.R.menu.main, menu)

       searchItem = menu.findItem(com.mangesh.gitexpo.R.id.action_search)

        val searchManager = this@MainActivity.getSystemService(Context.SEARCH_SERVICE) as SearchManager


        if (searchItem != null) {
            searchView = searchItem!!.getActionView() as SearchView

            searchView?.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    publicRepoAdapter.filter.filter(p0)
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    publicRepoAdapter.filter.filter(p0)
                    return false
                }

            })
        }
        if (searchView != null) {
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(this@MainActivity.componentName))
        }
        return super.onCreateOptionsMenu(menu)
    }
}
