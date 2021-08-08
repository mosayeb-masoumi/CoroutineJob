package com.example.coroutinexample

import android.graphics.Insets.add
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {

    private var count = 1

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start.setOnClickListener {

            startCounting()
        }


        btn_cancel.setOnClickListener {

            if(::job.isInitialized)
                job.cancel()

            count = 0

            CoroutineScope(IO).launch{
                showCounter("0")
            }
        }



        val list1  = arrayListOf<String>().apply {
            add("reza")
            add("hassan")
        }

        val list2  = arrayListOf<String>().also {
            it.add("milad")
            it.add("gholi")
        }

        list1?.run {

            val name1 = list1[0]
            val name2 = list1[1]

            val names = name1+name2
        }

        val obj = Model().apply {
            name1 = "naghi"
            name2 = "taghi"
        }
        with(obj){
            name1 = "naghi1"
            name2 = "taghi2"
        }

        Log.i("TAG", "onCreate: ")


        var result = Model().let {
            it.name1 = "naghi"
            it.name2 = "taghi"

            "return this result"
        }

        var ddd = result
        var oo = ddd


        obj?.run {
            val name1 = obj.name1
            val name2 = obj.name2

            Log.i("TAG", "onCreate: ")
        }



        val list3 = arrayListOf<Model>().apply {
            add(Model("reza","rezai"))
            add(Model("ali","mardani"))
        }

        list3?.run {

            var name1 = list3[0].name1 + list3[0].name2
            var name2 = list3[1].name1 + list3[1].name2

            var ss = ""
        }


        val list4 = arrayListOf<String>().apply {
            add(list3[0].name1 + list3[0].name2)
            add(list3[1].name1 + list3[1].name2)
        }.also {
            var name1 = it[0]
            Log.i("TAG", "onCreate: ")
        }



        list4.run {
            var name1 = list4[0]
            var name2 = list4[1]

            var ss = ""
        }
        var ss = ""





    }

    private fun startCounting() {
        job = Job()
        CoroutineScope(IO + job).launch {
            var counter = withContext(IO){getCount()}

            showCounter(counter.toString())

        }


    }

    private suspend fun showCounter(count: String) {

        withContext(Main){
            txt.text = count
        }

    }

    private suspend fun getCount(): Int {
        delay(1000)
        startCounting()
        return count++
    }


    private fun getFun1(): String {
        return "result1"
    }
    private fun getFun2(): String {
        return "result2"
    }


    override fun onDestroy() {
        super.onDestroy()

        if(job.isActive)
        job.cancel()
    }
}