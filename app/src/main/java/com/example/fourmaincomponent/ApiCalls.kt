package com.example.fourmaincomponent

import com.example.fourmaincomponent.model.SocialPost

suspend fun loadPosts(): List<SocialPost> {
    return ApiClient.apiService.getPosts()
}