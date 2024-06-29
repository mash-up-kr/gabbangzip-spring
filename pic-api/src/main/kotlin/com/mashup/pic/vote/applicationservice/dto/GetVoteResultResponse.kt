package com.mashup.pic.vote.applicationservice.dto

data class GetVoteResultResponse(
    val imageUrls: List<String>,
    val templateImageUrl: String,
    val needLottieYn: Boolean
) {
    companion object {
        fun sample(): GetVoteResultResponse {
            return GetVoteResultResponse(
                imageUrls = listOf(),
                templateImageUrl = "testImageUrl",
                needLottieYn = false
            )
        }
    }
}
