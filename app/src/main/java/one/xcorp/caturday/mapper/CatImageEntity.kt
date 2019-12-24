package one.xcorp.caturday.mapper

import one.xcorp.caturday.domain.entity.CatImageEntity
import one.xcorp.caturday.model.CatModel

fun CatImageEntity.toCatModel() = CatModel(id, image)

fun List<CatImageEntity>.toCatModel() = map { it.toCatModel() }
