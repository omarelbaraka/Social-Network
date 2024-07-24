package com.example.socialnetwork.data.utils

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


object ServiceTools {
    fun prepareStringPart(descriptionString: String?): RequestBody {
        return RequestBody.create(
            MultipartBody.FORM,
            descriptionString
        ) // MediaType.parse("application/json")
    }
    fun prepareFilePart(partName: String?, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            partName,
            file.name,
            RequestBody.create(MediaType.parse("application/octet-stream"), file)
        )
    }
}