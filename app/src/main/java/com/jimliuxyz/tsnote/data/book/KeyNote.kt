package com.jimliuxyz.tsnote.data.book

import com.google.gson.annotations.SerializedName

/**
 * Created by jimliu on 2018/3/10.
 */
data class KeyNote(
        @SerializedName("key")
        var key: String,
        @SerializedName("note")
        var note: String
)