package com.jimliuxyz.vocabook.data.book

import com.google.gson.annotations.SerializedName

/**
 * Created by jimliu on 2018/3/10.
 */
data class Word(
        @SerializedName("naText")
        var naText: String,
        @SerializedName("taText")
        var taText: String
)