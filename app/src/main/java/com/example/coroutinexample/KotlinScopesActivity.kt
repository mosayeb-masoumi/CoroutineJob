package com.example.coroutinexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutinexample.coroutin_example1.Model

class KotlinScopesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_scopes)


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


        val result = Model().let {
            it.name1 = "naghi"
            it.name2 = "taghi"

            "return this result"
        }

        val ddd = result
        var oo = ddd


        obj.run {
            val name1 = obj.name1
            val name2 = obj.name2

            Log.i("TAG", "onCreate: ")
        }



        val list3 = arrayListOf<Model>().apply {
            add(Model("reza","rezai"))
            add(Model("ali","mardani"))
        }

        list3.run {

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


}