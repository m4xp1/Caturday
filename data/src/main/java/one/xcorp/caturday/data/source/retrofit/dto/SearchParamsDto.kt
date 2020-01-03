package one.xcorp.caturday.data.source.retrofit.dto

import kotlin.math.sqrt

data class SearchParamsDto(
    val limit: Int,
    val page: Int,
    val offset: Int
) : Comparable<SearchParamsDto> {

    override fun compareTo(other: SearchParamsDto) = limit - other.limit

    companion object {

        operator fun invoke(size: Int, position: Int): SearchParamsDto {
            require(size > 0) { "size <= 0" }
            require(position >= 0) { "position < 0" }

            val length = position + size

            val currentPositionParams = createSearchParams(size, length)
            val nextPositionParams = createSearchParams(size + 1, length + 1)

            return minOf(currentPositionParams, nextPositionParams)
        }

        private fun createSearchParams(size: Int, length: Int): SearchParamsDto {
            val limit = minimumPageSize(size, length)
            return SearchParamsDto(limit, length / limit - 1, limit - size)
        }

        private fun minimumPageSize(size: Int, length: Int): Int {
            if (size == 1 || size == length) {
                return size
            }

            val count = sqrt(length.toDouble()).toInt()
            var previousBiggerDivisor = length

            for (divisor in 2..count) {
                if (length % divisor == 0) {
                    val biggerDivisor = length / divisor
                    when {
                        divisor >= size -> return divisor
                        biggerDivisor == size -> return biggerDivisor
                        biggerDivisor < size -> return previousBiggerDivisor
                    }
                    previousBiggerDivisor = biggerDivisor
                }
            }
            return previousBiggerDivisor
        }
    }
}
