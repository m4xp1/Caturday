package one.xcorp.caturday.domain.repository

import one.xcorp.caturday.domain.entity.PageEntity
import rx.Single

interface CatsRepository {

    fun getCats(limit: Int, page: Int): Single<PageEntity>
}
