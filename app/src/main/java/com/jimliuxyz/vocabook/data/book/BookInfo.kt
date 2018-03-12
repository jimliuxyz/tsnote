package com.jimliuxyz.vocabook.data.book

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Created by jimliu on 2018/3/1.
 */

@Entity
data class BookInfo(
        @PrimaryKey var id: Long = (UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE),
        var title: String = ""
)