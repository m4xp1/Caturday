package one.xcorp.caturday.domain.entity

data class PageEntity<T>(
    val startPosition: Int,
    val totalItems: Int,
    val items: List<T>
)
