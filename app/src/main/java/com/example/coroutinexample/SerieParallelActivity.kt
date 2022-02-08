package com.example.coroutinexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_serie_parallel.*
import kotlinx.coroutines.*
import java.io.IOException

class SerieParallelActivity : AppCompatActivity() {



    /**
    https://blog.mindorks.com/mastering-kotlin-coroutines-in-android-step-by-step-guide

    launch: fire and forget
    async: perform a task and return a result

    /** launch vs async*/
    The difference is that the launch{} does not return anything and the async{}returns  by await()


    When we use withContext, it will run in series instead of parallel. That is a major difference.
     **/





    private lateinit var jobParallelLaunch: Job
    private lateinit var jobParallelAsync: Job
    private lateinit var jobSerie: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serie_parallel)


        btn_serie_tasks.setOnClickListener {
            doSerieTasks()
        }


        btn_parallel_launch.setOnClickListener {
            doParallelLaunch()
        }

        btn_parallel_async.setOnClickListener {
            doParallelAsync()
        }


    }


    /***********************************  parallel async **************************************/

    // do tasks as parallel and wait for all results
    private fun doParallelAsync() {

        txt_serie_parallel.text = "0"

        jobParallelAsync = SupervisorJob()
        val handler = CoroutineExceptionHandler { _, _ -> }
        val scope = CoroutineScope(Dispatchers.IO + jobParallelAsync + handler)

        scope.launch {
            val result1 = async { fetchFirstData() }
            val result2 = async { fetchSecondData() }

            val result = result1.await() + result2.await()
            showInText(result)
        }


    }


    /***********************************  parallel launch **************************************/
    // do tasks as parallel and dosent wait all results
    private fun doParallelLaunch() {
        txt_serie_parallel.text = "0"


//        jobParallel1 = Job()   // Job() (if one child failed the other child will failed too)
        jobParallelLaunch =
            SupervisorJob()   // superiorJob() (if one child failed the other child will do its job)
        val handler = CoroutineExceptionHandler { _, _ -> }
        val scope = CoroutineScope(Dispatchers.IO + jobParallelLaunch + handler)

        //child1
        scope.launch {
            val result1 = withContext(Dispatchers.IO) { fetchFirstData() }
            showInText(result1)

        }


        //child2
        scope.launch {
            val result2 = withContext(Dispatchers.IO) { fetchSecondData() }
            showInText(result2)
        }


    }



    /***********************************  serie launch **************************************/
    /** When we use withContext, it will run in series instead of parallel. That is a major difference.**/
    // do task1 and wait for result one then do task2 then wait result2  (take time)
    private fun doSerieTasks() {

        txt_serie_parallel.text = "0"


        //        jobParallel1 = Job()   // Job() (if one child failed the other child will failed too)
        jobSerie = SupervisorJob()      // superiorJob() (if one child failed the other child will do its job)
        val handler = CoroutineExceptionHandler { _, _ -> }
        CoroutineScope(Dispatchers.IO + jobSerie + handler).launch {

           /** When we use withContext, it will run in series instead of parallel. That is a major difference.**/
            val result1 = withContext(Dispatchers.IO) { fetchFirstData() }
            val result2 = withContext(Dispatchers.IO) { fetchSecondData() }

            val total = result1 + result2
            showInText(total)

        }
    }














    private suspend fun fetchFirstData(): Int {
        delay(2000)
//        throw IOException()  // to create exception  and failed the return
        return 2
    }

    private suspend fun fetchSecondData(): Int {
        delay(4000)
        return 4
    }


    private suspend fun showInText(count: Int) {

        withContext(Dispatchers.Main) {
            txt_serie_parallel.text = count.toString()
        }
    }
}