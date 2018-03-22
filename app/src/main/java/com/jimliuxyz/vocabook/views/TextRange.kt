package com.jimliuxyz.vocabook.views

import kotlin.math.max
import kotlin.math.min

/**
 * Created by jimliu on 2018/3/21.
 */
internal open class TextRange(var start: Int = -1, var end: Int = -1) {
    init {
        set(start, end)
    }

    fun set(start_: Int, end_: Int) {
        start = min(start_, end_)
        end = max(start_, end_)
    }

    fun set(rng: TextRange) {
        set(rng.start, rng.end)
    }

    fun empty() {
        set(-1, -1)
    }

    fun getText(text: String): String {
        if (start >= 0 && end <= text.length) {
            return "[" + text.substring(start, end) + "]"
        }
        return ""
    }

    fun isPoint() = start == end
    fun isEmpty() = start == -1 && isPoint()

    operator fun contains(value: Int) = start <= value && value <= end
    operator fun contains(rng: TextRange) = (rng.start >= start && rng.end <= end)

    operator fun plus(rng: TextRange): TextRange {
        return TextRange(min(start, rng.start), max(end, rng.end))
    }

    operator fun minus(rng: TextRange): TextRange {
        if (this > rng || this < rng)
            return this

        if (start == rng.start)
            return TextRange(rng.end, end)
        else if (end == rng.end)
            return TextRange(start, rng.start)

        if (Math.abs(start - rng.start) <= Math.abs(end - rng.end))
            return TextRange(rng.end, end)
        else
            return TextRange(start, rng.start)
    }

    operator fun compareTo(b: TextRange): Int {
        if (start > b.end) // a after b
            return 1
        else if (end < b.start) // a before b
            return -1

        return 0 //cross
    }

    infix fun sameAs(b: TextRange) = (start == b.start && end == b.end)

    override fun toString(): String {
        return "[$start, $end]"
    }
}