package one.xcorp.caturday.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean = true,
    private val headerNum: Int = 0
) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        val position = parent.getChildAdapterPosition(view) - headerNum

        if (position < 0) {
            outRect.left = 0
            outRect.right = 0
            outRect.top = 0
            outRect.bottom = 0
        } else {
            val column = position % spanCount

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount

                if (position < spanCount) {
                    outRect.top = spacing
                }
                outRect.bottom = spacing
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount

                if (position >= spanCount) {
                    outRect.top = spacing
                }
            }
        }
    }
}
