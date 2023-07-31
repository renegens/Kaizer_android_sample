package eu.giant.kaizen.data

import retrofit2.HttpException

sealed class Resource<out T : Any> {
    object Success : Resource<Nothing>()
    class Data<out T : Any>(val data: T) : Resource<T>()
    class HttpError(val error: HttpException) : Resource<Nothing>()
    class Error(val error: Exception) : Resource<Nothing>()
    object Canceled : Resource<Nothing>()
    object NotAuthenticated : Resource<Nothing>()

    fun isSuccessful(): T? {
        return if (this is Data) return this.data else null
    }
}
