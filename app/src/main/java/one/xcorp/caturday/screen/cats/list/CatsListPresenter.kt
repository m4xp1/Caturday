package one.xcorp.caturday.screen.cats.list

import one.xcorp.caturday.domain.usecase.GetCatsImagesUseCase
import one.xcorp.caturday.presenter.BasePresenterImpl
import javax.inject.Inject

class CatsListPresenter @Inject constructor(
    private val getCatsImages: GetCatsImagesUseCase
) : BasePresenterImpl<CatsListContract.View>(), CatsListContract.Presenter {

    override fun nextPage() {

    }
}
