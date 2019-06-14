package com.mangesh.gitexpo.Activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mangesh.gitexpo.GitViewModel
import com.mangesh.gitexpo.Login
import com.mangesh.gitexpo.Pojo.AuthToken
import com.mangesh.gitexpo.R
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {

    private lateinit var gitViewModel: GitViewModel

    private val clientId="042fa1b603e399e9a21f"

    private val clientSecret="7856437f467ce50b4435f3a2d1a58f236e166cfe"

    private val callbackUrl="demo://callback"

    var sharedPref:SharedPreferences?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        sharedPref=this.getSharedPreferences(resources.getString(R.string.app_name),Context.MODE_PRIVATE)

        gitViewModel=ViewModelProviders.of(this).get(GitViewModel::class.java)

          if (sharedPref?.getString("token",null) != null){
              startActivity(Intent(this@LoginActivity,MainActivity::class.java))
              finish()
          }

        btLogin.setOnClickListener {
            openGithub()
        }

    }

    private fun openGithub(){

        val intent=Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com/login/oauth/authorize?client_id=$clientId&scope=repo&redirect_uri=$callbackUrl"))
        startActivity(intent)

    }

    override fun onResume() {
        super.onResume()

        val uri=intent.data
          if (uri!=null)
          {
             Log.d("intent", "onResume: $uri")
               var code=uri.getQueryParameter("code")
               Log.d("","onResume: "+code)
               val builder =Retrofit.Builder()
                  .baseUrl("https://github.com/")
                  .addConverterFactory(GsonConverterFactory.create())

              val retrofit =builder.build()

              val loginApi=retrofit.create(Login::class.java)

              val acessToken= loginApi.accessToken(clientId,clientSecret,code)

              acessToken.enqueue(object :Callback<AuthToken>{
                  override fun onResponse(call: Call<AuthToken>, response: Response<AuthToken>) {

                      val authToken=response.body()

                      Log.d("acessToken","onResponse: $authToken")

                        if (authToken?.accessToken!=null){
                              with(sharedPref?.edit()){
                                  this?.putString("token",authToken.accessToken)
                                  this?.apply()
                              }
                        }

                      startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                           finish()


                  }

                  override fun onFailure(call: Call<AuthToken>, t: Throwable) {
                      Log.d("AuthToken","onFailure: ${t.message}")
                  }

              })
          }
        Log.d("uri","onResume: $uri")
    }
}
