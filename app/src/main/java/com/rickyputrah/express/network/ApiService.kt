package com.rickyputrah.express.network


interface ApiService {
    fun <T> create(type: Class<T>): T
}

inline fun <reified T> ApiService.create(): T {
    return create(T::class.java)
}