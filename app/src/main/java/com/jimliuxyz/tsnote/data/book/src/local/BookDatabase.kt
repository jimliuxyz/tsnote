package com.jimliuxyz.tsnote.data.book.src.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.jimliuxyz.tsnote.data.book.BookContent
import com.jimliuxyz.tsnote.data.book.BookInfo


/**
 * Created by jimliu on 2018/3/1.
 */
@Database(entities = [BookInfo::class, BookContent::class], version = 12)
abstract class BookDatabase : RoomDatabase() {

    abstract fun getDao(): BookDao

    companion object {
        private var INSTANCE: BookDatabase? = null
        private var lock = Any()

        fun getInstance(context: Context): BookDatabase {
            synchronized(lock) {
                return INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                        BookDatabase::class.java, "appbook.db")
                        .fallbackToDestructiveMigration()
                        .build().also { INSTANCE = it }
            }
        }
    }
}
