package com.andres.nyuzuk.data.tools.jsonserializer

interface JsonSerializer {
    fun <T: Any> toJson(value: T?): String
    fun <T: Any> fromJson(json: String, clazz: Class<T>): T?
}