package com.example.madcampw2.retrofit

import com.example.madcampw2.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserAPI {
    @get:GET("/user/get-all")
    val allUsers: Call<List<User?>?>?

    @POST("/user/save")
    fun  // 저장하기
            save(@Body user: User?): Call<User?>?
}