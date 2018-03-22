package com.jimliuxyz.vocabook.di

import com.jimliuxyz.vocabook.data.book.src.IBookDataSrc
import com.jimliuxyz.vocabook.data.book.src.remote.BookRemoteSrc
import com.jimliuxyz.vocabook.di.annotations.Remote
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