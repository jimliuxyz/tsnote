package com.jimliuxyz.vocabook.data.book

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.jimliuxyz.vocabook.utils.UUID62
import java.util.*

/**
 * Created by jimliu on 2018/3/1.
 */

@Entity
@TypeConverters(BookInfo.Converter::class)
data class BookInfo(
        @PrimaryKey var id: String = UUID62.randomUUID(),
        var title: String = "",
        var attentionDate: Date = Date(System.currentTimeMillis())
) {
    companion object {
        fun clone(info: BookInfo): BookInfo {
            return BookInfo(info.id, info.title)
        }
    }

    class Converter {
        @TypeConverter
        fun date2Long(date: Date): Long {
            return date.time
        }

        @TypeConverter
        fun long2Date(timems: Long): Date {
            return Date(timems)
        }
    }
}