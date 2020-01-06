package one.xcorp.caturday.screen.cats.list

import kotlinx.android.parcel.Parcelize

@Parcelize
data class CatsListState(
    override val initialKey: Int
) : CatsListContract.State
