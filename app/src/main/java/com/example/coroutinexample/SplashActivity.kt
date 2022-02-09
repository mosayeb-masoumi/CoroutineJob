package com.example.coroutinexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coroutinexample.coroutin_viewmodel.CoroutinViewModelActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        btn_timer.setOnClickListener {
            startActivity(Intent(this@SplashActivity , MainActivity::class.java))
        }

        btn_serie_parallel_activity.setOnClickListener {
            startActivity(Intent(this@SplashActivity , SerieParallelActivity::class.java))
        }


        btn_Coroutin_ViewModel_activity.setOnClickListener {
            startActivity(Intent(this@SplashActivity , CoroutinViewModelActivity::class.java))
        }



    }


}

