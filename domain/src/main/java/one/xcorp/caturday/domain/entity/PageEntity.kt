package one.xcorp.caturday.domain.entity

data class PageEntity<T>(
    val current: Int,
    val total: Int,
    val items: List<T>
)
