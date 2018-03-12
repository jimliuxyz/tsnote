package com.jimliuxyz.vocabook.data.book.src.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.jimliuxyz.vocabook.data.book.BookContent
import com.jimliuxyz.vocabook.data.book.BookInfo
import com.jimliuxyz.vocabook.data.book.BookSet

/**
 * Created by jimliu on 2018/3/1.
 */
@Dao
abstract class BookDao {

    //select
    @Query("SELECT * FROM BookInfo")
    abstract fun getBookInfos(): List<BookInfo>

    @Query("SELECT * FROM BookInfo where id=:id")
    abstract fun getBookInfo(id: Long): BookInfo

    @Query("SELECT * FROM BookContent where bookId=:id")
    abstract fun getBookContent(id: Long): BookContent

    fun getBookSet(id: Long): BookSet {
        return BookSet(getBookInfo(id), getBookContent(id))
    }

    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBookInfo(info: BookInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBookContent(content: BookContent)


    //delete
    @Query("DELETE FROM BookInfo where id=:id")
    abstract fun delBookInfo(id: Long)

    @Query("DELETE FROM BookInfo")
    abstract fun clear()

//    @Query("SELECT COUNT(*)>0 FROM BookInfo WHERE value = :arg0")
//    fun hasText(text: String): Boolean


}
