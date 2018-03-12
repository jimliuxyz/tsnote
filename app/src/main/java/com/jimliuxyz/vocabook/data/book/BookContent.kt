package com.jimliuxyz.vocabook.data.book

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by jimliu on 2018/3/1.
 */

@Entity(foreignKeys = [ForeignKey(entity = BookInfo::class,
        parentColumns = ["id"],
        childColumns = ["bookId"],
        onDelete = CASCADE)])
@TypeConverters(BookContent.WordConverter::class)
data class BookContent(
        var bookId: Long,
        var words: ArrayList<Word> = ArrayList<Word>(),
        @PrimaryKey(autoGenerate = true) var id: Long = 0
) {

    class WordConverter {
        @TypeConverter
        fun word2Json(words: ArrayList<Word>): String {
            val json = Gson().toJson(words)
            return json
        }

        @TypeConverter
        fun json2Word(json: String): ArrayList<Word> {
            val turnsType = object : TypeToken<ArrayList<Word>>() {}.type
            val list = Gson().fromJson<ArrayList<Word>>(json, turnsType)
            return list
        }
    }

}


