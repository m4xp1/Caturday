package one.xcorp.caturday.screen.cats.list.mapper

import one.xcorp.caturday.domain.entity.CatEntity
import one.xcorp.caturday.screen.cats.list.model.CatModel

fun CatEntity.toCatModel() = CatModel(id, image)

fun List<CatEntity>.toCatModel() = map { it.toCatModel() }
