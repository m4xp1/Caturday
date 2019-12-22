package one.xcorp.caturday.adapter.cats

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import one.xcorp.caturday.model.CatModel

class CatsListAdapter : PagedListAdapter<CatModel, CatViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val catModel = getItem(position)
        holder.bindTo(catModel)
    }

    companion object {

        private val DiffCallback = object : DiffUtil.ItemCallback<CatModel>() {
            override fun areItemsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
