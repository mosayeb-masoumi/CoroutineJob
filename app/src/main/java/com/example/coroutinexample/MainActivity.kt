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

    private lateinit var jobParallel1: Job

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


        btn_serie_tasks.setOnClickListener {
            doSerieTasks()
        }




        btn_parallel_tasks.setOnClickListener {
            doParallelTasks()
        }



    }

    private fun doParallelTasks() {

//        jobParallel1 = Job()   // Job() (if one child failed the other child will failed too)
        jobParallel1 = SupervisorJob()   // superiorJob() (if one child failed the other child will do its job)
        val handler = CoroutineExceptionHandler { _, _ -> }
        val scope = CoroutineScope(IO + jobParallel1 + handler)

        //child1
        scope.launch {
            val result1 = withContext(IO) { fetchFirstData() }
            showInText(result1)
        }

        //child2
        scope.launch {
            val result2 = withContext(IO) { fetchSecondData() }
            showInText(result2)
        }


    }


    private fun doSerieTasks() {
        CoroutineScope(IO).launch {

            val result1 = withContext(IO) { fetchFirstData() }
            val result2 = withContext(IO) { fetchSecondData() }

            val total = result1 + result2
            var tt = total

        }
    }


    private suspend fun fetchFirstData(): Int {
        delay(4000)
        throw IOException()  // to create exception  and failed the return
        return 1
    }

    private suspend fun fetchSecondData(): Int {
        delay(8000)
        return 2
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