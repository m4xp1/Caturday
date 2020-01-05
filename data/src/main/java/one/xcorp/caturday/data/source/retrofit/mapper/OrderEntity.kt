package one.xcorp.caturday.data.source.retrofit.mapper

import one.xcorp.caturday.domain.entity.OrderEntity
import one.xcorp.caturday.domain.entity.OrderEntity.ASCENDING
import one.xcorp.caturday.domain.entity.OrderEntity.DESCENDING

fun OrderEntity.toDto() = when (this) {
    ASCENDING -> "ASC"
    DESCENDING -> "DESC"
}
