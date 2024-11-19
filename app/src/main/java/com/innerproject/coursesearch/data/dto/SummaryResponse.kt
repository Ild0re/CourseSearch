package com.innerproject.coursesearch.data.dto

import com.google.gson.annotations.SerializedName

data class SummaryResponse(
    val meta: MetaDto,
    @SerializedName("course-review-summaries")
    val summaries: List<SummaryDto>
): Response()