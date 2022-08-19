package com.example.coroutinexample.coroutine_step_by_step

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.coroutinexample.SplashActivity
import com.example.coroutinexample.databinding.ActivityCoroutneBinding
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

class CoroutneActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var binding: ActivityCoroutneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoroutneBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /************* example1 delay *************/
//        example1()

        /************* example2 suspend functions *************/
//        example2()

        /************* example3 coroutine contexts (Dispatchers) *************/
//        example3()


        /************* example4 run blocking *************/
//        example4()


        /************* example5 join job  *************/
//        example5()


        /************* example6 cancel job  *************/
//        example6()


        /************* example7 job can't be cancel  while have a long running task   *************/
//        example7()


        /************* example8 job can be cancel  while have a long running task *************/
//        example8()


        /************* example9 job can be cancel by using Timeout *************/
//        example9()


        /************* example10 async and await (do parallel task)*************/
//        example10()


        /************* example11 lifeCycleScope *************/
        example11()


    }


    private fun example1() {
        // GlobalScope will be cancel when app close
        // but if app be in background , it will do its job
        GlobalScope.launch {
            delay(5000L)
            Log.d(TAG, "Coroutine says hello from thread ${Thread.currentThread().name}")
        }

        Log.d(TAG, "hello from thread ${Thread.currentThread().name}")
    }


    private fun example2() {


        GlobalScope.launch {

            val network1 = doNetworkCall()
            val network2 = doNetworkCall2()
            Log.d(TAG, network1)
            Log.d(TAG, network2)
        }
    }


    private fun example3() {

        GlobalScope.launch(Dispatchers.IO) {

            Log.d(TAG, Thread.currentThread().name) // == io thread
            val result = doNetworkCall()

            //if we dont use  withContext(Dispatchers.Main) the app will crash
            //because inside Dispatchers.IO block we can not set ui . for set ui we must use Dispatchers.Main
            withContext(Dispatchers.Main) {

                Log.d(TAG, Thread.currentThread().name) // == main thread
                binding.txtResult.text = result
            }

        }
    }


    private fun example4() {
        // run blocking just block main thread not io thread
        Log.d(TAG, "before runBlocking")
        runBlocking {

            // it wont be blocked  because of dispatchers.IO
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "finish IO coroutine 1")
            }

            // it wont be blocked
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "finish IO coroutine 2")
            }

            // here after will be blocked
            Log.d(TAG, "start of runBlocking")
            delay(5000L)
            Log.d(TAG, "end of runBlocking")
        }

        Log.d(TAG, "after runBlocking")
    }


    private fun example5() {

        var count = 0
        val job = GlobalScope.launch(Dispatchers.Default) {
            repeat(5) {
                Log.d(TAG, "coroutine is still working")
                count += 1
                Log.d(TAG, count.toString())
                delay(1000L)
            }
        }

        // block main thread for 5 seconds to wait job finish its task
        runBlocking {
            job.join()
            Log.d(TAG, "main thread is continuing")
        }
    }


    private fun example6() {
        var count = 0
        val job = GlobalScope.launch(Dispatchers.Default) {

            repeat(5) {
                Log.d(TAG, "coroutine is still working")
                count += 1
                Log.d(TAG, count.toString())
                delay(1000L)
            }
        }


        runBlocking {
            delay(2000)
            job.cancel()  // after 2 seconds it cancel the job and job cant repeat for 5 times , just for 2 times
            Log.d(TAG, "main thread is continuing")
        }
    }


    private fun example7() {

        var count = 0

        val job = GlobalScope.launch(Dispatchers.Default) {

            Log.d(TAG, "starting long running caculation")
            for (i in 30..40) {
                Log.d(TAG, "result for i = $i: ${fib(i)}")
            }
            Log.d(TAG, "finish long running caculation")
        }

        // after 2 seconds the job won't be cancelled because job is involving the long run task (job hasn't been noticed that was cancelled)
        runBlocking {
            delay(2000)
            job.cancel()
            Log.d(TAG, "canceled job")
        }

    }


    private fun example8() {

        var count = 0

        val job = GlobalScope.launch(Dispatchers.Default) {

            Log.d(TAG, "starting long running caculation")
            for (i in 30..40) {
                // this clause notice job that has been cancelled and no need to continue
                if (isActive) {
                    Log.d(TAG, "result for i = $i: ${fib(i)}")
                }

            }
            Log.d(TAG, "finish long running caculation")
        }

        // after 2 seconds the job won't be cancelled because job is involving the forLoop (job hasn't been noticed that was cancelled)
        runBlocking {
            delay(2000)
            job.cancel()
            Log.d(TAG, "canceled job")
        }

    }


    private fun example9() {
        // it cancel the job after specific time automatically

        val job = GlobalScope.launch(Dispatchers.IO) {

            Log.d(TAG, "starting long running caculation")

            withTimeout(3000L) {
                for (i in 30..40) {
                    // this clause notice job that has been cancelled and no need to continue
                    if (isActive) {
                        Log.d(TAG, "result for i = $i: ${fib(i)}")
                    }
                }
            }

            Log.d(TAG, "finish long running caculation")

        }
    }


    private fun example10() {

        GlobalScope.launch(Dispatchers.IO) {

            val time = measureTimeMillis {

                val result1 = async { doNetworkCall() }
                val result2 = async { doNetworkCall2() }

                Log.d(TAG, "result1 is: ${result1.await()}")
                Log.d(TAG, "result2 is: ${result2.await()}")
            }

            Log.d(TAG, "request took $time ms.")
        }
    }


    private fun example11() {
        // global scope is alive till app close
        //here if we start new activity we can see that coroutine is still running
        // it's lifeCycle is depend on App
//        GlobalScope.launch {
//            while (true){
//                delay(1000L)
//                Log.d(TAG, "still running")
//            }
//        }




        // here after starting new activity the scope will finish
        // it's lifeCycle is depend on Activity and after destroy activity , the coroutine will cancel
        var count =0
        lifecycleScope.launch {
            while (true) {

                count ++
                delay(1000L)
                Log.d(TAG, "still running  $count")
            }
        }



        GlobalScope.launch {
            delay(5000)
            Intent(this@CoroutneActivity, SplashActivity::class.java).also {
                startActivity(it)
                finish()  // destroy activity
            }
        }
    }


    suspend fun doNetworkCall(): String {
        delay(3000L)
        return "answer 1"
    }

    suspend fun doNetworkCall2(): String {
        delay(3000)
        return "Answer 2"
    }


    // fibunachi function
    fun fib(n: Int): Long {

        return if (n == 0) 0
        else if (n == 1) 1
        else fib(n - 1) + fib(n - 2)

    }

}