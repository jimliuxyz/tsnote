package com.jimliuxyz.tsnote.di

import android.app.Application
import android.content.Context
import com.jimliuxyz.tsnote.data.book.src.IBookDataSrc
import com.jimliuxyz.tsnote.data.book.src.local.BookDao
import com.jimliuxyz.tsnote.data.book.src.local.BookDatabase
import com.jimliuxyz.tsnote.data.book.src.local.BookLocalSrc
import com.jimliuxyz.tsnote.di.annotations.Local
import com.jimliuxyz.tsnote.services.translation.TranslateService
import com.jimliuxyz.tsnote.services.tts.SpeakService
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by jimliu on 2018/3/11.
 */
@Module
abstract class ApplicationModule {
    //expose Application as an injectable context
    @Binds
    internal abstract fun bindContext(application: Application): Context


    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun provideBookDb(application: Application): BookDatabase {
            return BookDatabase.getInstance(application)
        }

        @Provides
        @JvmStatic
        @Singleton
        fun provideBookDao(db: BookDatabase): BookDao {
            return db.getDao()
        }

        @Provides
        @JvmStatic
        @Singleton
        fun provideTranslateService(): TranslateService {
            return TranslateService.instance
        }

        @Provides
        @JvmStatic
        @Singleton
        fun provideSpeakService(context: Context): SpeakService {
            return SpeakService.getInstance(context)
        }
    }

    @Singleton
    @Local
    @Binds
    abstract fun provideBookLocalSrc(src: BookLocalSrc): IBookDataSrc
}
