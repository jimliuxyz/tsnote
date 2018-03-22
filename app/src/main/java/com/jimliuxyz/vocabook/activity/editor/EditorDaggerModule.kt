package com.jimliuxyz.vocabook.activity.editor

import com.jimliuxyz.vocabook.di.annotations.ActivityScoped
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by jimliu on 2018/3/11.
 */

@Module
abstract class EditorDaggerModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        @ActivityScoped
        @Named(EditorActivity.EXTRA_BOOK_ID)
        fun EXTRA_BOOK_ID(activity: EditorActivity): String? {
            return activity.intent.getStringExtra(EditorActivity.EXTRA_BOOK_ID)
        }
    }

    @Binds
    abstract fun provideView(v: EditorActivity): EditorContract.View

    @Binds
    abstract fun providePresenter(p: EditorPresenter): EditorContract.Presenter

}
