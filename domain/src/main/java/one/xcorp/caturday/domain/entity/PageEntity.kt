package one.xcorp.caturday.domain.entity

data class PageEntity<T>(
    val items: List<T>,
    val position: Int,
    val totalCount: Int
)
