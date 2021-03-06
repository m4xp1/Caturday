package one.xcorp.caturday.data.source.retrofit.mapper

import one.xcorp.caturday.data.source.retrofit.dto.CatImageDto
import one.xcorp.caturday.domain.entity.CatImageEntity

internal fun CatImageDto.toEntity() = CatImageEntity(id, url)

internal fun List<CatImageDto>.toEntity() = map { it.toEntity() }
