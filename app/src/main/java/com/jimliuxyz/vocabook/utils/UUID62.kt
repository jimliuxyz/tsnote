package com.jimliuxyz.vocabook.utils

/**
 * Created by jimliu on 2018/3/16.
 */
object UUID62 {
    private val BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private fun num2UID(inNum: Long): String {
        check(inNum >= 0) { "number must >= 0" }

        var text = ""
        var num = inNum
        while (num > 0) {
            text = BASE62[(num % BASE62.length).toInt()] + text
            num /= BASE62.length
        }
        return text
    }

    private fun num2UID(num: Double): String {
        val split = num.toBigDecimal().toString().split("\\.".toRegex())
        return num2UID(split[0].toLong()) + if (split.size > 1) num2UID(split[1].toLong()) else ""
    }

    private inline fun String.fixLen(len: Int, padChar: Char): String {
        return substring(0, kotlin.math.min(len, length)).padEnd(len, '0')
    }

    fun randomUUID(len: Int = 6): String {
        return num2UID(Math.random()).fixLen(len, '0')
    }

    fun randomUUIDWithTime(len: Int = 6): String {
        var time = num2UID(System.currentTimeMillis()).fixLen(6, '0')
        var uuid = num2UID(Math.random()).fixLen(len, '0')
        return time + uuid
    }
}