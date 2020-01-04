package one.xcorp.caturday.mapper

import one.xcorp.caturday.domain.entity.CatEntity
import one.xcorp.caturday.model.CatModel

fun CatEntity.toCatModel() = CatModel(id, image)

fun List<CatEntity>.toCatModel() = map { it.toCatModel() }
