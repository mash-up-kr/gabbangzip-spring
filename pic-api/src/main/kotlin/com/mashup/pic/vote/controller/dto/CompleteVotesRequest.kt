package com.mashup.pic.vote.controller.dto

data class CompleteVotesRequest(
    val resultInfoList: List<ResultInfo>
) {
    data class ResultInfo(
        val picImageId: String,
        val likeYn: Boolean
    )
}
