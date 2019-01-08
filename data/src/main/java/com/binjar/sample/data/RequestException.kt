package com.binjar.sample.data

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

class RequestException(var httpCode: Int = 500, override var message: String = "") : Exception(message)

class ApiException(val code: Int, val errorBody: ResponseBody?, override val message: String) : Exception(message)

/**
 * Response model of error response from server
 */
data class ApiError(@SerializedName("message") val message: String = "")