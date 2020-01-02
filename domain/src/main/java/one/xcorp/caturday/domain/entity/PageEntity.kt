package one.xcorp.caturday.domain.entity

data class PageEntity<T>(
    val totalItems: Int,
    val items: List<T>
)
