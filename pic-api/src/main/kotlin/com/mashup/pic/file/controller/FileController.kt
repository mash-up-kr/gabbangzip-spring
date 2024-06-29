package com.mashup.pic.file.controller

import com.mashup.pic.common.ApiResponse
import com.mashup.pic.file.applicationservice.FileApplicationService
import com.mashup.pic.file.controller.dto.UploadUrlResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "파일")
@RestController
@RequestMapping("/api/v1/files")
class FileController(
    private val fileApplicationService: FileApplicationService,
) {
    @GetMapping("/upload")
    fun getUploadUrl(
        @Valid @RequestParam @NotBlank extension: String,
    ): ApiResponse<UploadUrlResponse> {
        return ApiResponse.success(UploadUrlResponse.from(fileApplicationService.getUploadUrl(extension)))
    }
}
