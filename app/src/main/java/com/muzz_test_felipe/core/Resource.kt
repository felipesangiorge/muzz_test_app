package com.muzz_test_felipe.core

sealed class Resource<out DataType>(
    open val data: DataType?
) {
    data class Loading<out DataType>(override val data: DataType?) : Resource<DataType>(data)
    data class Success<out DataType>(override val data: DataType) : Resource<DataType>(data)
    data class Failure<out DataType>(override val data: DataType?, val error: Error) : Resource<DataType>(data)

    open class Error(val message: String?) {
    }

    companion object {
        fun from(apiError: String?): Resource.Error = Resource.Error(apiError)
    }
}