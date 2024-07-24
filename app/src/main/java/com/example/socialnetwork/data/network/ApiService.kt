package com.example.socialnetwork.data.network

import com.example.socialnetwork.data.model.CreatePostResponseObject
import com.example.socialnetwork.data.model.EditProfilSentModel
import com.example.socialnetwork.data.model.EditProfileResponseModel
import com.example.socialnetwork.data.model.GetPostResponseObject
import com.example.socialnetwork.data.model.LikePostResponse
import com.example.socialnetwork.data.model.ProfileDetailObject
import com.example.socialnetwork.data.model.ProfileDetailResponse
import com.example.socialnetwork.data.model.RequestCommentModel
import com.example.socialnetwork.data.model.ResponseCommentModel
import com.example.socialnetwork.data.model.ResponseFollowers
import com.example.socialnetwork.data.model.ResponseLikeProgramPost
import com.example.socialnetwork.data.model.ResponseModelSearch
import com.example.socialnetwork.data.model.ResponseSendRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface ApiService {
    @GET("/post/myposts")
    suspend fun getPosts(): GetPostResponseObject

    @GET("/post/posts/{id}")
    suspend fun getPostsByUserId(@Path("id") id: String): GetPostResponseObject

    @POST("/like/{id}")
    suspend fun likeOrDislikePost(@Path("id") idPost: String): LikePostResponse


    @Multipart
    @Streaming
    @POST("/post/createPost")
    suspend fun createPost(
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("privacy") privacy: RequestBody,
        @Part image: List<MultipartBody.Part>
    ): CreatePostResponseObject


    @GET("/profile/followers")
    suspend fun getFollowers(): ResponseFollowers

    @PATCH("/profile/edit")
    suspend fun editProfile(@Body editProfile: EditProfilSentModel): EditProfileResponseModel

    @GET("/profile/search")
    suspend fun searchProfile(@Query("keyword") keyword: String): ResponseModelSearch

    @GET("/programPost/programPosts")
    suspend fun programPosts(): GetPostResponseObject

    @POST("/like/programpost/{pId}")
    suspend fun likeOrDislikeProgramPost(@Path("pId") idPost: String): ResponseLikeProgramPost

    @POST("/comment/{pId}")
    suspend fun sendCommentPost(@Path("pId") idPost: String,@Body comment : RequestCommentModel): ResponseCommentModel

    @POST("/comment/programpost/{pId}")
    suspend fun sendCommentProgramPost(@Path("pId") idPost: String,@Body comment : RequestCommentModel): ResponseCommentModel

    @POST("/profile/request/send/{id}")
    suspend fun sendRequest(@Path("id") idUser: String): ResponseSendRequest

    @GET("/profile/details/{id}")
    suspend fun getProfile(@Path("id") idUser: String): ProfileDetailResponse

}
