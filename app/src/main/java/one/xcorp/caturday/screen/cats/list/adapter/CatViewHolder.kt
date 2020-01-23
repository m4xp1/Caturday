package one.xcorp.caturday.screen.cats.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.list_item_cat.view.*
import one.xcorp.caturday.R
import one.xcorp.caturday.glide.GlideApp
import one.xcorp.caturday.screen.cats.list.model.CatModel

class CatViewHolder private constructor(
    view: View
) : ViewHolder(view) {

    private val glide = GlideApp.with(itemView.context)

    fun bindTo(catModel: CatModel?) {
        glide.clear(itemView.imageView)

        if (catModel != null) {
            glide.load(catModel.image).into(itemView.imageView)
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
