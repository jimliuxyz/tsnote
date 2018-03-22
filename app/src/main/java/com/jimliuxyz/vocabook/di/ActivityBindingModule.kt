package com.jimliuxyz.vocabook.di

import com.jimliuxyz.vocabook.activity.editor.EditorActivity
import com.jimliuxyz.vocabook.activity.editor.EditorDaggerModule
import com.jimliuxyz.vocabook.activity.main.MainActivity
import com.jimliuxyz.vocabook.activity.main.MainDaggerModule
import com.jimliuxyz.vocabook.activity.prefs.MainSettingActivity
import com.jimliuxyz.vocabook.di.annotations.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jimliu on 2018/3/11.
 */

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainDaggerModule::class])
    abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [EditorDaggerModule::class])
    abstract fun editorActivity(): EditorActivity

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun mainSettingActivity(): MainSettingActivity
}
