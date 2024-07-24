package com.example.socialnetwork.data.repository

import android.util.Log
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.socialnetwork.data.model.CreatePostResponseObject
import com.example.socialnetwork.data.model.EditProfilSentModel
import com.example.socialnetwork.data.model.EditProfileResponseModel
import com.example.socialnetwork.data.model.GetPostResponseObject
import com.example.socialnetwork.data.model.LikePostResponse
import com.example.socialnetwork.data.model.ProfileDetailResponse
import com.example.socialnetwork.data.model.RequestCommentModel
import com.example.socialnetwork.data.model.ResponseCommentModel
import com.example.socialnetwork.data.model.ResponseFollowers
import com.example.socialnetwork.data.model.ResponseLikeProgramPost
import com.example.socialnetwork.data.model.ResponseModelSearch
import com.example.socialnetwork.data.model.ResponseSendRequest
import com.example.socialnetwork.data.network.ApiService
import com.example.socialnetwork.data.utils.ServiceTools
import com.example.socialnetwork.data.utils.ServiceTools.prepareFilePart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

val responsePostText = "{\n" +
        "    \"status\": \"Success\",\n" +
        "    \"data\": [\n" +
        "        {\n" +
        "            \"id\": 3,\n" +
        "            \"userId\": 2,\n" +
        "            \"title\": \"2eme post \",\n" +
        "            \"content\": \"omar le bg\",\n" +
        "            \"image\": [\n" +
        "                \"http://res.cloudinary.com/duy6c001a/image/upload/v1718819040/egkqz7qzhdkhzbxghtoe.png\"\n" +
        "            ],\n" +
        "            \"likesCount\": 0,\n" +
        "            \"commentsCount\": 0,\n" +
        "            \"postedAt\": \"2024-06-19T17:44:01.397Z\",\n" +
        "            \"updatedAt\": \"2024-06-19T17:44:01.397Z\",\n" +
        "            \"privacy\": \"PUBLIC\",\n" +
        "            \"author\": {\n" +
        "                \"name\": \"Omar\",\n" +
        "                \"id\": 2\n" +
        "            },\n" +
        "            \"comments\": []\n" +
        "        },\n" +
        "        {\n" +
        "            \"id\": 2,\n" +
        "            \"userId\": 2,\n" +
        "            \"title\": \"titr\",\n" +
        "            \"content\": \"voici mon post\",\n" +
        "            \"image\": [\n" +
        "                \"http://res.cloudinary.com/duy6c001a/image/upload/v1718812689/kzpzoyoiu49ltf9zt38a.png\",\n" +
        "                \"http://res.cloudinary.com/duy6c001a/image/upload/v1718812691/tkq1fhp5ol9murvste5l.png\"\n" +
        "            ],\n" +
        "            \"likesCount\": 0,\n" +
        "            \"commentsCount\": 0,\n" +
        "            \"postedAt\": \"2024-06-19T15:58:11.540Z\",\n" +
        "            \"updatedAt\": \"2024-06-19T15:58:11.540Z\",\n" +
        "            \"privacy\": \"PUBLIC\",\n" +
        "            \"author\": {\n" +
        "                \"name\": \"Omar\",\n" +
        "                \"id\": 2\n" +
        "            },\n" +
        "            \"comments\": []\n" +
        "        }\n" +
        "    ]\n" +
        "}"

class AppRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPosts(): GetPostResponseObject {
        return apiService.getPosts()
        //return Gson().fromJson(responsePostText, GetPostResponseObject::class.java)

    }

    suspend fun getProgramPosts(): GetPostResponseObject {
        return apiService.programPosts()
    }

    suspend fun getFollowers(): ResponseFollowers {
        return apiService.getFollowers()
    }

    suspend fun editProfile(edit: EditProfilSentModel): EditProfileResponseModel {
        return apiService.editProfile(edit)
    }

    suspend fun searchProfile(keyword: String): ResponseModelSearch {
        return apiService.searchProfile(keyword = keyword)
    }

    suspend fun getPostsByUserId(userId: String): GetPostResponseObject {
        return apiService.getPostsByUserId(id = userId)
    }

    suspend fun likeOrDislike(postId: String): LikePostResponse {
        return apiService.likeOrDislikePost(idPost = postId)
    }

    suspend fun likeOrDislikeProgramPost(postId: String): ResponseLikeProgramPost {
        return apiService.likeOrDislikeProgramPost(idPost = postId) //
    }

    suspend fun postComment(idPost: String, comment: RequestCommentModel): ResponseCommentModel {
        return apiService.sendCommentPost(idPost = idPost, comment = comment) //
    }

    suspend fun postProgramPostComment(
        idPost: String,
        comment: RequestCommentModel
    ): ResponseCommentModel {
        return apiService.sendCommentProgramPost(idPost = idPost, comment = comment) //
    }

    suspend fun sendRequestToUser(idUser: String): ResponseSendRequest {
        return apiService.sendRequest(idUser) //
    }

    suspend fun getProfileDetail(idUser: String): ProfileDetailResponse {
        return apiService.getProfile(idUser) //
    }

    suspend fun createPost(
        title: String,
        desc: String,
        privacy: String = "PUBLIC",
        imagesPath: List<String>
    ): CreatePostResponseObject {
        val parts: MutableList<MultipartBody.Part> = ArrayList()
        uploadToCloudinary(imagesPath.get(0)).let {

            Log.e("TAG_Mohamed", "createPost: $it")

        }
        imagesPath.forEach {
            parts.add(
                prepareFilePart(
                    "image",
                    File(it)
                )
            )
        }
        return apiService.createPost(
            title = ServiceTools.prepareStringPart(title),
            content = ServiceTools.prepareStringPart(desc),
            privacy = ServiceTools.prepareStringPart(privacy),
            image = parts
        )
    }

    suspend fun uploadToCloudinary(filePath: String): String? {
        val cloudinary = Cloudinary(
            ObjectUtils.asMap(
                "cloud_name", "duy6c001a",
                "api_key", "514851867785229",
                "api_secret", "SkhJjxs9tuwy4HYd9i9nps7Qiaw"
            )
        )
        return withContext(Dispatchers.IO) {
            try {
                val uploadResult =
                    cloudinary.uploader().upload(File(filePath), ObjectUtils.emptyMap())
                uploadResult["secure_url"] as String?
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
