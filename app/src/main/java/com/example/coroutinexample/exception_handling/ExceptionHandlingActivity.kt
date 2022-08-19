package com.example.coroutinexample.exception_handling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.coroutinexample.R
import kotlinx.coroutines.*


class ExceptionHandlingActivity : AppCompatActivity() {

    val TAG = "ExceptionActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exceptio_handling)


//        example1()

        /**** exception cancel all children ****/
//        example2()

        /**** supervisor scope ,exception in one child do not cancel all other children ****/
        example3()

    }


    private fun example1() {
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d(TAG, "$throwable")
        }

        CoroutineScope(handler).launch {
            throw Exception("Error")
        }

        lifecycleScope.launch(handler) {
            throw Exception("Error")
        }
    }


    private fun example2() {

        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d(TAG, "$throwable")
        }


        CoroutineScope(Dispatchers.IO + handler).launch {


            /** in this example coroutin3 start after 100ms then this corotine
             * finish successfully , coroutine2 has exception then wont finish,
             * and coroutine1 because has delay 400ms and start after coroutine2
             * then it will be cancel too
             * then in general , the coroutines that start after exception will be canceled **/


            launch {
                delay(400L)
                Log.d(TAG, "Coroutine 1 finished")
            }

            launch {
                delay(300L)
                throw Exception("Coroutine 2 failed")
            }

            launch {
                delay(100L)
                Log.d(TAG, "Coroutine 3 finished")
            }

            launch {
                delay(150L)
                Log.d(TAG, "Coroutine 4 finished")
            }

        }
    }


    private fun example3() {

        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.d(TAG, "$throwable")
        }
        CoroutineScope(Dispatchers.IO + handler).launch {

            /** using  supervisorScope , exception in one child wont effect on the others **/

            supervisorScope {
                launch {
                    delay(400L)
                    Log.d(TAG, "Coroutine 1 finished")
                }

                launch {
                    delay(300L)
                    throw Exception("Coroutine 2 failed")
                }

                launch {
                    delay(100L)
                    Log.d(TAG, "Coroutine 3 finished")
                }
            }


        }
    }
}