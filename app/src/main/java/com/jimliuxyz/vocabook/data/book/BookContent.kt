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
@TypeConverters(BookContent.Converter::class)
data class BookContent(
        var bookId: String,
        var story: String,
        var keyNotes: ArrayList<KeyNote> = ArrayList<KeyNote>(),
        @PrimaryKey(autoGenerate = true) var id: Long = 0
) {

    companion object {
        fun clone(content: BookContent): BookContent {
            return BookContent(content.bookId, content.story,
                    ArrayList(content.keyNotes.map { KeyNote(it.key, it.note) }))
        }
    }

    class Converter {
        @TypeConverter
        fun word2Json(words: ArrayList<KeyNote>): String {
            val json = Gson().toJson(words)
            return json
        }

        @TypeConverter
        fun json2Word(json: String): ArrayList<KeyNote> {
            val turnsType = object : TypeToken<ArrayList<KeyNote>>() {}.type
            val list = Gson().fromJson<ArrayList<KeyNote>>(json, turnsType)
            return list
        }
    }

}


