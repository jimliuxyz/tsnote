package com.jimliuxyz.vocabook.utils

import android.util.Log
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by jimliu on 2018/3/9.
 */
class MemLeakDetector {
    companion object {
        val TAG = "MLD"
        var map = WeakHashMap<Any, String>()

        fun add(obj: Any, desc: String = "") {
            map.put(obj, desc + "-" + obj.javaClass.simpleName)
        }

        init {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    System.gc()
                    if (map.size > 0)
                        Log.d(TAG, "---")

                    var cntmap = HashMap<String, Int>()
                    for (ety in map.entries) {
                        var desc = ety.value

                        var c = cntmap.get(desc)
                        cntmap.put(desc, if (c == null) 1 else c + 1)
                    }

                    for (ety in cntmap.entries) {
                        Log.d(TAG, "${ety.key} : ${ety.value}")
                    }
                }
            }, 1000, 60 * 1000)
        }
    }
}