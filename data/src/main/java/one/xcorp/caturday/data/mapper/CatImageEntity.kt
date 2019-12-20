package one.xcorp.caturday.data.mapper

import one.xcorp.caturday.data.source.network.dto.CatImageDto
import one.xcorp.caturday.domain.entity.CatImageEntity

internal fun CatImageDto.toEntity() = CatImageEntity(id, url)

internal fun List<CatImageDto>.toEntity() = map { it.toEntity() }
