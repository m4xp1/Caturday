package one.xcorp.caturday.domain.entity

data class PageEntity(
    val current: Int,
    val total: Int,
    val items: List<CatEntity>
)
