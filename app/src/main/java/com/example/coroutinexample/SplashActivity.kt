package com.example.coroutinexample

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coroutinexample.coroutin_example1.CoroutinJobActivity
import com.example.coroutinexample.coroutine_retrofit.CoroutineRetrofitActivity
import com.example.coroutinexample.coroutine_step_by_step.CoroutneActivity
import com.example.coroutinexample.databinding.ActivitySplashBinding
import com.example.coroutinexample.exception_handling.ExceptionHandlingActivity


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

        binding.btnCoroutineActivity.setOnClickListener {
            startActivity(Intent(this@SplashActivity , CoroutneActivity::class.java))
        }


        binding.btnCoroutineRetrofit.setOnClickListener {
            startActivity(Intent(this@SplashActivity , CoroutineRetrofitActivity::class.java))
        }


        binding.btnExceptionHandling.setOnClickListener {
            startActivity(Intent(this@SplashActivity , ExceptionHandlingActivity::class.java))
        }


        binding.btnKotlinScopes.setOnClickListener {
            startActivity(Intent(this@SplashActivity , KotlinScopesActivity::class.java))
        }

    }
}