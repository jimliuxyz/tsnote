package com.jimliuxyz.tsnote.di

import com.jimliuxyz.tsnote.data.book.src.IBookDataSrc
import com.jimliuxyz.tsnote.data.book.src.remote.BookRemoteSrc
import com.jimliuxyz.tsnote.di.annotations.Remote
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created by jimliu on 2018/3/11.
 */
@Module
abstract class MutableModule {

    @Singleton
    @Remote
    @Binds
    abstract fun provideBookRemoteSrc(src: BookRemoteSrc): IBookDataSrc
}

