package com.jimliuxyz.vocabook.di

import android.content.Context
import com.jimliuxyz.vocabook.data.book.src.IBookDataSrc
import com.jimliuxyz.vocabook.data.src.FakeBookSrc
import com.jimliuxyz.vocabook.di.annotations.Remote
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