package com.jimliuxyz.vocabook.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * Created by jimliu on 2018/3/11.
 */
@Module
abstract class ApplicationModule {
    //expose Application as an injectable context
    @Binds
    internal abstract fun bindContext(application: Application): Context
}
