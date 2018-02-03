package br.com.conseng.basicloadmore

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * IMPORTANTE: precisa incluir nas dependências do projeto o [RecyclerView].
 * Base class for an Adapter.
 * Adapters provide a binding from an app-specific data set to views that are displayed
 * within a RecyclerView.
 * @property [data] list of data to be displayed.
 * @param [context] application context.
 * @param <RecyclerView.ViewHolder> A class that extends ViewHolder that will be used by the adapter.
 * @see [https://developer.android.com/guide/topics/ui/layout/recyclerview.html]
 */
class Adapter(val data: ArrayList<String>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.  This new [ViewHolder] should be constructed with a new View that can represent
     * the items of the given type.
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder(ViewHolder, int, List). Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary findViewById(int) calls.
     * @param [parent] The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param [viewType] The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see [https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#getItemViewType(int)]
     * @see [https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#onBindViewHolder(VH, int)]
     */
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item, parent, false)
        val vh = VHolder(view)
        return vh
    }

    /**
     * Returns the total number of items that can be laid out.
     * RecyclerView listens for Adapter's notify events and calculates the effects of adapter
     * data changes on existing Views. These calculations are used to decide which animations
     * should be run.
     * @return The number of items currently available
     */
    override fun getItemCount(): Int = data.size


    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the ViewHolder.itemView to reflect the item at the given
     * position.
     * Note that unlike 'ListView', RecyclerView will not call this method again if the position of
     * the item changes in the data set unless the item itself is invalidated or the new position
     * cannot be determined. For this reason, you should only use the [position] parameter while
     * acquiring the related data item inside this method and should not keep a copy of it. If you
     * need the position of an item later on (e.g. in a click listener), use
     * 'ViewHolder.getAdapterPosition()' which will have the updated adapter position.
     * @param [holder] The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param [position] The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder as VHolder
        view.textView.text = data[position]
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     * While 'LayoutParams' belong to the 'LayoutManager', [ViewHolder] belong to the adapter.
     * The individual item views will hold strong references to [ViewHolder] objects and that
     * [RecyclerView] instances may hold strong references to extra off-screen item views for caching purposes.
     * @property [itemView] fields for caching potentially expensive 'findViewById(int)' results.
     * @see [https://developer.android.com/reference/android/support/v7/widget/RecyclerView.ViewHolder.html]
     */
    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView: TextView

        init {
            textView = itemView.findViewById(R.id.lo_txtItem)
        }
    }
}