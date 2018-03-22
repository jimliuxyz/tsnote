package com.jimliuxyz.tsnote.di

import android.content.Context
import com.jimliuxyz.tsnote.data.book.src.IBookDataSrc
import com.jimliuxyz.tsnote.data.src.FakeBookSrc
import com.jimliuxyz.tsnote.di.annotations.Remote
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by jimliu on 2018/3/11.
 */
@Module
class MutableModule {

    @Singleton
    @Remote
    @Provides
    fun provideBookRemoteSrc(context: Context): IBookDataSrc {
        return FakeBookSrc(context)
    }

}