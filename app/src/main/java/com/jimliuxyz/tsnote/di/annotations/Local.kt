package com.jimliuxyz.tsnote.di.annotations

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

/**
 * Created by jimliu on 2018/3/11.
 */
@Documented
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
annotation class Local
