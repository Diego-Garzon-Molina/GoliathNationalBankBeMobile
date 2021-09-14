package diego.garzon.platform

import diego.garzon.platform.Status.SUCCESS
import diego.garzon.platform.Status.ERROR
import diego.garzon.platform.Status.LOADING

data class Resource<out T>(
  val status: Status,
  val data: T?,
  val exception: Exception?
) {
  companion object {
    fun <T> success(data: T?): Resource<T> {
      return Resource(SUCCESS, data, null)
    }

    fun <T> error(exception: Exception): Resource<T> {
      return Resource(ERROR, null, exception)
    }

    fun <T> loading(data: T?): Resource<T> {
      return Resource(LOADING, data, null)
    }
  }
}
