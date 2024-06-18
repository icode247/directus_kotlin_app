package com.example.directusapp.network
import com.example.directusapp.Constants
import com.example.directusapp.model.BlogsResponse
import com.example.directusapp.model.BlogResponse
import com.example.directusapp.model.GlobalResponse
import com.example.directusapp.model.PageResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DirectusApiService {
    @GET("items/global")
    suspend fun getGlobal(): GlobalResponse

    @GET("items/pages")
    suspend fun getPages(): PageResponse

    @GET("items/blog?fields=*,author.name")
    suspend fun getBlogs(): BlogsResponse

    @GET("items/blog/{id}?fields=*,author.name")
    suspend fun getBlogById(@Path("id") id: Int): BlogResponse

    companion object {
        fun create(): DirectusApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(DirectusApiService::class.java)
        }
    }
}
