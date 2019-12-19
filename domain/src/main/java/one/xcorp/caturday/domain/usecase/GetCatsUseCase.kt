package one.xcorp.caturday.domain.usecase

import one.xcorp.caturday.domain.repository.CatsRepository
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(
    private val catsRepository: CatsRepository
) {

    fun execute(limit: Int, page: Int) = catsRepository.getCats(limit, page)
}
