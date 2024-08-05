package com.example.appy.model

data class CampingData (
    val addr1: String,
    val facltNm: String,
    val induty: String,
    val tel: String,
    val mapX: String,
    val mapY: String
)

data class ApiResponse (
    val response: ResponseBody
)

data class ResponseBody (
    val body: ResponseData
)

data class ResponseData (
    val items: List<CampingData>
)
