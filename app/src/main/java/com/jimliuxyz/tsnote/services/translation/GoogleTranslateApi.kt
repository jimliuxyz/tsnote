package com.jimliuxyz.tsnote.services.translation

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by jimliu on 2018/3/19.
 */
interface GoogleTranslateApi {
    @GET("translate_a/single?client=gtx&dt=t&ie=UTF-8&oe=UTF-8")
    fun translate(
            @Query("sl") srcLang: String,
            @Query("tl") targetLang: String,
            @Query("q", encoded = true) query: String)
            : Call<JsonArray>

    //https://translate.google.com.tw/translate_a/single?client=gtx&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&otf=1&ssel=0&tsel=0&kc=4&tk=82638.467088&sl=en&tl=zh-TW&q="love"
    @GET("translate_a/single?client=gtx&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&otf=1&ssel=0&tsel=0&kc=4&tk=82638.467088")
    fun translate2(
            @Query("sl") srcLang: String,
            @Query("tl") targetLang: String,
            @Query("q", encoded = true) query: String)
            : Call<JsonArray>
}
