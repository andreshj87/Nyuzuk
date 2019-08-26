package com.andres.nyuzuk.data.tools.jsonserializer

import com.squareup.moshi.Moshi

class MoshiJsonSerializer(
    private val moshi: Moshi
) : JsonSerializer {
    override fun <T : Any> toJson(value: T?) = makeAdapter(value!!::class.java).toJson(value as Nothing?)

    override fun <T : Any> fromJson(json: String, clazz: Class<T>) = moshi.adapter(clazz).fromJson(json)

    private fun <T : Any> makeAdapter(clazz: Class<T>) = moshi.adapter(clazz)
}