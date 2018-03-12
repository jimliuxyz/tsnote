package com.jimliuxyz.vocabook.di

import android.app.Application
import com.jimliuxyz.vocabook.data.book.src.local.BookDao
import com.jimliuxyz.vocabook.data.book.src.local.BookDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by jimliu on 2018/3/11.
 */
@Module
class BookModule {

    @Provides
    @Singleton
    fun provideBookDb(application: Application): BookDatabase {
        return BookDatabase.getInstance(application)
    }

    @Provides
    @Singleton
    fun provideBookDao(db: BookDatabase): BookDao {
        return db.getDao()
    }

}