package com.jimliuxyz.vocabook.di

import com.jimliuxyz.vocabook.editor.EditorActivity
import com.jimliuxyz.vocabook.editor.EditorDaggerModule
import com.jimliuxyz.vocabook.main.MainActivity
import com.jimliuxyz.vocabook.main.MainDaggerModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jimliu on 2018/3/11.
 */

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainDaggerModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [EditorDaggerModule::class])
    internal abstract fun editorActivity(): EditorActivity
}
