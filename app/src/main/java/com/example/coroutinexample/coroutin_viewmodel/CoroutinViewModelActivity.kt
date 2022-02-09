package com.example.coroutinexample.coroutin_viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.coroutinexample.R
import kotlinx.android.synthetic.main.activity_coroutin_view_model.*


class CoroutinViewModelActivity : AppCompatActivity() {

    var context: Context? = null
    var viewModel: MyViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutin_view_model)


        context = this
        viewModel = MyViewModel(context as CoroutinViewModelActivity)

        btn_fetch_data.setOnClickListener {

            fetchData()
        }


    }

    private fun fetchData() {


        loading.visibility = View.VISIBLE
        btn_fetch_data.visibility = View.GONE

        viewModel?.getList()?.observe(this, {
            when (it != null) {
                it.size > 0 -> {
                    val list = it
                    val dd = list

                    Toast.makeText(context,"first name is ${it[0]}",Toast.LENGTH_SHORT).show()
                }

                it.size == 0 -> {
                    val list = it
                    val dd = list
                    Log.i(TAG, "list is empety")
                }

            }

            loading.visibility = View.GONE
            btn_fetch_data.visibility = View.VISIBLE

        })


    }
}