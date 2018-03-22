package com.jimliuxyz.tsnote.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by jimliu on 2018/3/17.
 */
object AppExecutors {
    val diskIO = Executors.newSingleThreadExecutor()
    val networkIO = Executors.newFixedThreadPool(3)

    val mainThread = object : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            mainThreadHandler.post(command)
        }
    }
}

fun doIO(block: () -> Unit) {
    AppExecutors.diskIO.execute {
        block()
    }
}

fun doNetwork(block: () -> Unit) {
    AppExecutors.networkIO.execute {
        block()
    }
}

fun doUI(block: () -> Unit) {
    AppExecutors.mainThread.execute {
        block()
    }
}

//class CallarMsg: Thread(){
//    val looper = Looper.myLooper()
//    var handler : Handler
//    init{
//        try {
//            handler = Handler(Looper.myLooper())
//        }catch (ex: Exception){
//            looper.queue.
//            handler = Handler()
//        }
//    }
//
//    override fun run() {
//        handler.looper.
//    }
//}

