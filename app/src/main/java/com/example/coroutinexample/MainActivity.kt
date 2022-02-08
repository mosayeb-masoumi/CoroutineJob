package com.example.coroutinexample

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var job: Job
    var count = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_scopes.setOnClickListener {
            startActivity(Intent(this@MainActivity , ScopeActivity::class.java ))
        }


        btn_start.setOnClickListener {

            if (::job.isInitialized) {   // to prevent multiple start
                if (job.isActive)
                    job.cancel()

                count = 0
                CoroutineScope(IO).launch {
                    showInText(count)
                }
            }
            startCounting()
        }



        btn_cancel.setOnClickListener {
            if (::job.isInitialized)
                job.cancel()

            count = 0
            CoroutineScope(IO).launch {
                showInText(count)
            }

        }





    }




    private fun startCounting() {

        job = Job()
        CoroutineScope(IO + job).launch {
            val count = withContext(IO) { getCount() }
            showInText(count)
        }

    }

    private suspend fun getCount(): Int {
        delay(1000)
        startCounting()
        return count++
    }

    private suspend fun showInText(count: Int) {

        withContext(Main) {
            txt.text = count.toString()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (job.isActive)
            job.cancel()
    }
}