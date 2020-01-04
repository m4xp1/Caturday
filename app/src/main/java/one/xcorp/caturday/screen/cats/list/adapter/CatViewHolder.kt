package one.xcorp.caturday.screen.cats.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.list_item_cat.view.*
import one.xcorp.caturday.R
import one.xcorp.caturday.glide.GlideApp
import one.xcorp.caturday.model.CatModel

class CatViewHolder private constructor(
    view: View
) : ViewHolder(view) {

    fun bindTo(catModel: CatModel?) {
        if (catModel == null) {
            itemView.imageView.setImageDrawable(null)
        } else {
            GlideApp.with(itemView.context)
                .load(catModel.image)
                .into(itemView.imageView)
        }
    }

    companion object {

        operator fun invoke(parent: ViewGroup): CatViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.list_item_cat, parent, false)

            return CatViewHolder(view)
        }
    }
}
