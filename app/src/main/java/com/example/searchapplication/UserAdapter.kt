package com.example.searchapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(clickListener: ClickListener): RecyclerView.Adapter<UserAdapter.ViewHolder>(), Filterable {

    private lateinit var context: Context
    private var userModelList: List<UserModel> = arrayListOf()
    private var userModelListFitered: List<UserModel> = arrayListOf()
    private var clickListener: ClickListener = clickListener

    public fun setData(userModel: List<UserModel>) {
        userModelList = userModel
        userModelListFitered = userModel
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val viewCell = layoutInflater.inflate(R.layout.row_user, parent, false)
        return ViewHolder(viewCell)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var userModel = userModelList.get(position)
        holder.bind(userModel)
        holder.itemView.setOnClickListener {
            clickListener.clickItem(userModel)
        }
    }

    override fun getItemCount(): Int {
        return userModelList.size
    }

    // MARK: - click listener

    interface ClickListener {
        fun clickItem(userModel: UserModel)
    }

    // MARK: - view holder

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var prefixTextView = itemView.findViewById<TextView>(R.id.tvPrefix)
        var usernameTextView = itemView.findViewById<TextView>(R.id.tvUsername)

        fun bind(userModel: UserModel) {
            val username = userModel.username
            prefixTextView.text = username.substring(0, 1)
            usernameTextView.text = username
        }
    }

    override fun getFilter(): Filter {
        var filter = object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                var filterResults = FilterResults()

                if (p0 == null || p0.isEmpty()) {
                    filterResults.values = userModelListFitered
                    filterResults.count = userModelListFitered.size
                } else {
                    var searchChar = p0.toString().lowercase()
                    var filteredResults = ArrayList<UserModel>()

                    for (userModel in userModelListFitered) {
                        if (userModel.username.lowercase().contains(searchChar)) {
                            filteredResults.add(userModel)
                        }
                    }

                    filterResults.values = filteredResults
                    filterResults.count = filteredResults.size
                }

                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                userModelList = p1!!.values as List<UserModel>
                notifyDataSetChanged()
            }
        }

        return filter
    }
}