package com.example.fourmaincomponent

import com.example.fourmaincomponent.model.SocialPost
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<SocialPost>
}