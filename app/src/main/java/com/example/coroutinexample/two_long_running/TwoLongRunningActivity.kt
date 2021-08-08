package com.example.coroutinexample.two_long_running

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
import com.example.coroutinexample.R
//import com.example.coroutinexample.databinding.ActivityTwoLongRunningBinding
import java.util.ArrayList

class TwoLongRunningActivity : AppCompatActivity() {

    var context: Context? = null
//    var viewModel: TwoLongViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two_long_running)

//                                                     ActivityBundleBinding
//        val binding = DataBindingUtil.setContentView<ActivityTwoLongRunningBinding>(this, R.layout.activity_two_long_running)
//        context = this
//        viewModel = TwoLongViewModel(context as TwoLongRunningActivity , binding)



        setupLongRunningTask()


    }

    private fun setupLongRunningTask() {

//        viewModel?.getName()?.observe(this, Observer {
//
//            if (it == "success") {
//                Log.i("TAG", "success")
//            }
//            else if (it == "failed") {
//                Log.i("TAG", "failed")
//            }
//        })



//        viewModel!!.getName().observe(this , Observer<String?> { name ->
//
//            when {
//                name.equals("success") -> {
//                    Log.i("TAG", "success")
//                }
//                name.equals("failed") -> {
//                    Log.i("TAG", "failed")
//                }
//            }
//        })
    }


}