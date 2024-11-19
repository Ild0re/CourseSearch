package com.innerproject.coursesearch.data.dto

import com.google.gson.annotations.SerializedName

data class MetaDto(
    val page: Int,
    @SerializedName("has_next")
    val hasNext: Boolean,
    @SerializedName("has_previous")
    val hasPrevious: Boolean
): Response()