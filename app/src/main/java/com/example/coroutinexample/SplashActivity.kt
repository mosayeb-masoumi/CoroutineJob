package com.example.coroutinexample

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coroutinexample.databinding.ActivitySplashBinding


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnCoroutineJobActivity.setOnClickListener {
            startActivity(Intent(this@SplashActivity , CoroutinJobActivity::class.java))
        }

        binding.btnKotlinScopes.setOnClickListener {
            startActivity(Intent(this@SplashActivity , KotlinScopesActivity::class.java))
        }
    }
}