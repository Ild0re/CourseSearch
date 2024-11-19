package com.innerproject.coursesearch.data.dto

data class TagsResponse(
    val meta: MetaDto,
    val tags: List<TagsDto>
): Response()