package com.jimliuxyz.tsnote.di

import com.jimliuxyz.tsnote.activity.editor.EditorActivity
import com.jimliuxyz.tsnote.activity.editor.EditorDaggerModule
import com.jimliuxyz.tsnote.activity.main.MainActivity
import com.jimliuxyz.tsnote.activity.main.MainDaggerModule
import com.jimliuxyz.tsnote.activity.prefs.MainSettingActivity
import com.jimliuxyz.tsnote.di.annotations.ActivityScoped
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
