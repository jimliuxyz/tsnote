package com.jimliuxyz.vocabook.di

import android.app.Application
import com.jimliuxyz.vocabook.BookApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityBindingModule::class,
    MutableModule::class])
interface AppComponent : AndroidInjector<BookApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}

