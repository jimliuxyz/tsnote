package com.jimliuxyz.vocabook.activity.main

import com.jimliuxyz.vocabook.di.annotations.ActivityScoped
import com.jimliuxyz.vocabook.di.annotations.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jimliu on 2018/3/11.
 */

@Module
abstract class MainDaggerModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun provideFragment(): MainFragment

    @ActivityScoped
    @Binds
    abstract fun providePresenter(p: MainPresenter): MainContract.Presenter
}
